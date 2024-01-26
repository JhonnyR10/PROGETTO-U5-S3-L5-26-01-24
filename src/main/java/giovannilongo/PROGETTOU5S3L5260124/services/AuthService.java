package giovannilongo.PROGETTOU5S3L5260124.services;

import giovannilongo.PROGETTOU5S3L5260124.entities.Ruolo;
import giovannilongo.PROGETTOU5S3L5260124.entities.Utente;
import giovannilongo.PROGETTOU5S3L5260124.exceptions.BadRequestException;
import giovannilongo.PROGETTOU5S3L5260124.exceptions.UnauthorizedException;
import giovannilongo.PROGETTOU5S3L5260124.payloads.NewUtenteDTO;
import giovannilongo.PROGETTOU5S3L5260124.payloads.UtenteLoginDTO;
import giovannilongo.PROGETTOU5S3L5260124.repositories.UtenteRepository;
import giovannilongo.PROGETTOU5S3L5260124.security.JWTTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    @Autowired
    private UtenteService utenteService;
    @Autowired
    private UtenteRepository utenteRepository;
    @Autowired
    private JWTTools jwtTools;
    @Autowired
    private PasswordEncoder bcrypt;

    public String authenticateUser(UtenteLoginDTO body) {
        Utente user = utenteService.findByUsername(body.username());
        if (bcrypt.matches(body.password(), user.getPassword())) {
            return jwtTools.createToken(user);
        } else {
            throw new UnauthorizedException("Credenziali non valide!");
        }
    }

    public Utente save(NewUtenteDTO body) {
        utenteRepository.findByUsername(body.username()).ifPresent(user -> {
            throw new BadRequestException("L'username" + user.getUsername() + " è già in uso!");
        });

        Utente newUser = new Utente();
        newUser.setUsername(body.username());
        newUser.setPassword(bcrypt.encode(body.password()));
        newUser.setRuolo(Ruolo.UTENTE_NORMALE);
        return utenteRepository.save(newUser);
    }
}
