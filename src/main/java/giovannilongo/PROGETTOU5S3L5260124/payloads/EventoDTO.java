package giovannilongo.PROGETTOU5S3L5260124.payloads;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record EventoDTO(@NotEmpty(message = "Il titolo è obbligatorio")
                        @Size(min = 3, max = 18, message = "Username deve avere minimo 3 caratteri, massimo 18")
                        String titolo,
                        @NotEmpty(message = "La descrizione è obbligatoria")
                        @Size(min = 3, max = 18, message = "Descrizione deve avere minimo 3 caratteri, massimo 18")
                        String descrizione,
                        @NotEmpty(message = "La data è obbligatoria")
                        LocalDate data,
                        @NotEmpty(message = "Il luogo è obbligatoria")
                        @Size(min = 3, max = 18, message = "Luogo deve avere minimo 3 caratteri, massimo 18")
                        String luogo,
                        @NotEmpty(message = "Il numero di posti disponibili è obbligatorio")
                        int postiDisponibili
) {
}
