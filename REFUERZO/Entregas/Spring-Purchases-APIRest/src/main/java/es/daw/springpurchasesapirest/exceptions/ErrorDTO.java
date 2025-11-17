package es.daw.springpurchasesapirest.exceptions;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ErrorDTO {
    private String message;

    private LocalDateTime timestamp;

    private Map<String, String> details;

}