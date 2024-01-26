package giovannilongo.PROGETTOU5S3L5260124.controllers;

import giovannilongo.PROGETTOU5S3L5260124.entities.Utente;
import giovannilongo.PROGETTOU5S3L5260124.exceptions.BadRequestException;
import giovannilongo.PROGETTOU5S3L5260124.payloads.NewUtenteDTO;
import giovannilongo.PROGETTOU5S3L5260124.payloads.NewUtenteResponseDTO;
import giovannilongo.PROGETTOU5S3L5260124.payloads.UtenteLoginDTO;
import giovannilongo.PROGETTOU5S3L5260124.payloads.UtenteLoginResponseDTO;
import giovannilongo.PROGETTOU5S3L5260124.services.AuthService;
import giovannilongo.PROGETTOU5S3L5260124.services.UtenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    AuthService authService;
    @Autowired
    UtenteService utenteService;

    @PostMapping("/login")
    public UtenteLoginResponseDTO login(@RequestBody UtenteLoginDTO body) {
        String accessToken = authService.authenticateUser(body);
        return new UtenteLoginResponseDTO(accessToken);
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public NewUtenteResponseDTO createUser(@RequestBody @Validated NewUtenteDTO newUserPayload, BindingResult validation) throws IOException {
        System.out.println(validation);
        if (validation.hasErrors()) {
            System.out.println(validation.getAllErrors());
            throw new BadRequestException("Ci sono errori nel payload!");
        } else {
            Utente newUser = authService.save(newUserPayload);

            return new NewUtenteResponseDTO(newUser.getId());
        }
    }
}
