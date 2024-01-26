package giovannilongo.PROGETTOU5S3L5260124.controllers;

import giovannilongo.PROGETTOU5S3L5260124.entities.Evento;
import giovannilongo.PROGETTOU5S3L5260124.entities.Utente;
import giovannilongo.PROGETTOU5S3L5260124.payloads.EventoDTO;
import giovannilongo.PROGETTOU5S3L5260124.payloads.EventoResponseDTO;
import giovannilongo.PROGETTOU5S3L5260124.services.EventoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/eventi")
public class EventoController {

    @Autowired
    private EventoService eventoService;

    @PostMapping("/crea")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('ORGANIZZATORE_EVENTI')")
    public EventoResponseDTO creaNuovoEvento(@RequestBody EventoDTO body,
                                             @AuthenticationPrincipal Utente currentUser) {
        Evento nuovoEvento = eventoService.creaNuovoEvento(body, currentUser.getUsername());
        return new EventoResponseDTO(nuovoEvento.getId());
    }

    @GetMapping("")
    public Page<Evento> getEventi(@RequestParam(defaultValue = "0") int page,
                                  @RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "id") String sortBy) {
        return eventoService.getEventi(page, size, sortBy);
    }

    @GetMapping("/{eventId}")
    public Evento findById(@PathVariable long eventId) {
        return eventoService.findById(eventId);
    }


    @PutMapping("/{eventId}")
    @PreAuthorize("hasAuthority('ORGANIZZATORE_EVENTI')")
    public Evento findEventByIdAndUpdate(@PathVariable long eventId, @RequestBody Evento eventoDTO) {
        return eventoService.findByIdAndUpdate(eventId, eventoDTO);
    }

    @DeleteMapping("/{eventId}")
    @PreAuthorize("hasAuthority('ORGANIZZATORE_EVENTI')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminaEvento(@PathVariable long eventId) {
        eventoService.findByIdAndDelete(eventId);
    }

    @PostMapping("/{eventoId}/prenota")
    @PreAuthorize("isAuthenticated()")
    public void prenotaEvento(@PathVariable long eventoId,
                              @AuthenticationPrincipal Utente currentUser) {
        eventoService.prenotaEvento(eventoId, currentUser);
    }

    @PatchMapping("/{eventoId}/avatar")
    @PreAuthorize("hasAuthority('ORGANIZZATORE_EVENTI')")
    public Evento uploadAvatar(@RequestParam("avatar") MultipartFile file, @PathVariable long eventoId) {
        try {
            return eventoService.uploadAvatar(eventoId, file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
