package giovannilongo.PROGETTOU5S3L5260124.payloads;

import java.util.Date;
import java.util.List;

public record ErrorsPayloadWithList(
        String message,
        Date timestamp,
        List<String> errorsList
) {
}
