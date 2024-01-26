package giovannilongo.PROGETTOU5S3L5260124.payloads;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record NewUtenteDTO(@NotEmpty(message = "L'username è obbligatorio")
                           @Size(min = 3, max = 18, message = "Username deve avere minimo 3 caratteri, massimo 18")
                           String username,
                           @NotEmpty(message = "La password è obbligatoria")
                           @Size(min = 3, max = 18, message = "Password deve avere minimo 3 caratteri, massimo 18")
                           String password
) {
}
