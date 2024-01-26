package giovannilongo.PROGETTOU5S3L5260124.repositories;

import giovannilongo.PROGETTOU5S3L5260124.entities.Utente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UtenteRepository extends JpaRepository<Utente, Long> {
    Optional<Utente> findByUsername(String username);
}
