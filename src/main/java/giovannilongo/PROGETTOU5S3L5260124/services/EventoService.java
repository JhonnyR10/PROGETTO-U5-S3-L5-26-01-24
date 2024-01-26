package giovannilongo.PROGETTOU5S3L5260124.services;

import giovannilongo.PROGETTOU5S3L5260124.entities.Evento;
import giovannilongo.PROGETTOU5S3L5260124.entities.Prenotazione;
import giovannilongo.PROGETTOU5S3L5260124.entities.Utente;
import giovannilongo.PROGETTOU5S3L5260124.exceptions.NotFoundException;
import giovannilongo.PROGETTOU5S3L5260124.payloads.EventoDTO;
import giovannilongo.PROGETTOU5S3L5260124.repositories.EventoRepository;
import giovannilongo.PROGETTOU5S3L5260124.repositories.UtenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class EventoService {

    @Autowired
    private EventoRepository eventoRepository;

    @Autowired
    private UtenteRepository utenteRepository;

    public Evento creaNuovoEvento(EventoDTO eventoDTO, String usernameOrganizzatore) {
        Utente organizzatore = utenteRepository.findByUsername(usernameOrganizzatore)
                .orElseThrow(() -> new NotFoundException("Utente organizzatore non trovato"));

        Evento nuovoEvento = new Evento();
        nuovoEvento.setTitolo(eventoDTO.titolo());
        nuovoEvento.setDescrizione(eventoDTO.descrizione());
        nuovoEvento.setData(eventoDTO.data());
        nuovoEvento.setLuogo(eventoDTO.luogo());
        nuovoEvento.setPostiDisponibili(eventoDTO.postiDisponibili());
        nuovoEvento.setOrganizzatore(organizzatore);

        return eventoRepository.save(nuovoEvento);
    }

    public Page<Evento> getEventi(int page, int size, String sort) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(sort));
        return eventoRepository.findAll(pageable);
    }

    public Evento findById(long id) {
        return eventoRepository.findById(id).orElseThrow(() -> new NotFoundException("Evento non trovato con ID: " + id));
    }

    public Evento findByIdAndUpdate(long id, Evento body) {
        Evento evento = eventoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Evento non trovato con ID: " + id));

        evento.setTitolo(body.getTitolo());
        evento.setDescrizione(body.getDescrizione());
        evento.setData(body.getData());
        evento.setLuogo(body.getLuogo());
        evento.setPostiDisponibili(body.getPostiDisponibili());

        return eventoRepository.save(evento);
    }

    public void findByIdAndDelete(long id) {
        Evento found = this.findById(id);
        eventoRepository.delete(found);
    }

    public void prenotaEvento(Long eventoId, Utente utente) {
        Evento evento = eventoRepository.findById(eventoId)
                .orElseThrow(() -> new NotFoundException("Evento non trovato con ID: " + eventoId));

        if (evento.getPostiDisponibili() > 0) {
            Prenotazione prenotazione = new Prenotazione();
            prenotazione.setEvento(evento);
            prenotazione.setUtente(utente);

            evento.getPrenotazioni().add(prenotazione);
            evento.setPostiDisponibili(evento.getPostiDisponibili() - 1);

            eventoRepository.save(evento);
        } else {
            throw new IllegalStateException("Posti esauriti per l'evento con ID: " + eventoId);
        }
    }

}
