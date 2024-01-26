package giovannilongo.PROGETTOU5S3L5260124.controllers;

import giovannilongo.PROGETTOU5S3L5260124.entities.Prenotazione;
import giovannilongo.PROGETTOU5S3L5260124.services.PrenotazioneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/prenotazioni")
public class PrenotazioniController {

    @Autowired
    private PrenotazioneService prenotazioneService;

    @GetMapping("")
    public Page<Prenotazione> getPrenotazioni(@RequestParam(defaultValue = "0") int page,
                                              @RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "id") String sortBy) {
        return prenotazioneService.getPrenotazioni(page, size, sortBy);
    }
}
