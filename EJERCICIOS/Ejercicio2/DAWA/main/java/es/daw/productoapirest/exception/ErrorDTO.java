package es.daw.productoapirest.exception;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorDTO {
    private String message; // mensaje nuestro personalizado

    private LocalDateTime timestamp;

    //private String details; // e.getMessage
    private Map<String, String> details;

}
