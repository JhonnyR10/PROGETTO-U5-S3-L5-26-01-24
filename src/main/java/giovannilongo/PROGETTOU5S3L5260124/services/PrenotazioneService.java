package giovannilongo.PROGETTOU5S3L5260124.services;

import giovannilongo.PROGETTOU5S3L5260124.entities.Evento;
import giovannilongo.PROGETTOU5S3L5260124.entities.Prenotazione;
import giovannilongo.PROGETTOU5S3L5260124.entities.Utente;
import giovannilongo.PROGETTOU5S3L5260124.exceptions.NotFoundException;
import giovannilongo.PROGETTOU5S3L5260124.repositories.EventoRepository;
import giovannilongo.PROGETTOU5S3L5260124.repositories.PrenotazioneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class PrenotazioneService {
    @Autowired
    private PrenotazioneRepository prenotazioneRepository;

    @Autowired
    private EventoRepository eventoRepository;

    public Prenotazione save(Utente utente, long eventoId) {
        Evento evento = eventoRepository.findById(eventoId)
                .orElseThrow(() -> new NotFoundException("Evento non trovato con ID: " + eventoId));

        Prenotazione prenotazione = new Prenotazione();
        prenotazione.setEvento(evento);
        prenotazione.setUtente(utente);

        return prenotazioneRepository.save(prenotazione);
    }

    public Page<Prenotazione> getPrenotazioni(int page, int size, String sort) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(sort));
        return prenotazioneRepository.findAll(pageable);
    }

    public Prenotazione findById(long id) {
        return prenotazioneRepository.findById(id).orElseThrow(() -> new NotFoundException("Prenotazione non trovato con ID: " + id));
    }


    public void findByIdAndDelete(long id) {
        Prenotazione found = this.findById(id);
        prenotazioneRepository.delete(found);
    }
}
