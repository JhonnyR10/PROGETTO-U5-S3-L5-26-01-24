package giovannilongo.PROGETTOU5S3L5260124.services;

import giovannilongo.PROGETTOU5S3L5260124.entities.Utente;
import giovannilongo.PROGETTOU5S3L5260124.exceptions.NotFoundException;
import giovannilongo.PROGETTOU5S3L5260124.repositories.UtenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class UtenteService {
    @Autowired
    private UtenteRepository utenteRepository;

    public Page<Utente> getUser(int page, int size, String sort) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(sort));
        return utenteRepository.findAll(pageable);
    }

    public Utente findById(long id) {
        return utenteRepository.findById(id).orElseThrow(() -> new NotFoundException(id));
    }

    public void findByIdAndDelete(long id) {
        Utente found = this.findById(id);
        utenteRepository.delete(found);
    }

    public Utente findByIdAndUpdate(long id, Utente body) {

        Utente found = this.findById(id);
        found.setUsername(body.getUsername());
        return utenteRepository.save(found);
    }


    public Utente findByUsername(String username) throws NotFoundException {
        return utenteRepository.findByUsername(username).orElseThrow(() -> new NotFoundException("Utente con username " + username + " non trovato!"));
    }
}
