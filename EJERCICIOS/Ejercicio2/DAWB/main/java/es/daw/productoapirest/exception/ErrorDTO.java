package es.daw.productoapirest.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorDTO {
    private String message;

    private LocalDateTime timestamp;

    // Fase 1: string con todos los errores de validación
    //private String details;

    // Fase 2: ?????
    private Map<String, String> details;


}
