package es.daw.productoapirest.exception;

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

    // Fase 1: string con todos los errores de validación
    //private String details;

    // Fase 2: ?????
    private Map<String, String> details;

    // ---------------------------
//    // PATRÓN BUILDER ....
//    // Constructor privado: solo el Builder puede crear instancias
//    // no se puede invocar directamente con new desde fuera. Solo el Builder puede hacerlo.
//    private ErrorDTO(Builder builder) {
//        this.message = builder.message;
//        this.timestamp = builder.timestamp;
//        this.details = builder.details;
//    }
//
//    // Método estático para obtener el builder
//    public static Builder builder() {
//        return new Builder();
//    }
//
//    // Clase interna estática Builder
//    // Es estática: no depende de una instancia de ErrorDTO
//    // Contiene los mismos campos que la clase principal.
//    // Ofrece métodos encadenables (fluent API) para asignar valores.
//    public static class Builder {
//        private String message;
//        private LocalDateTime timestamp;
//        private Map<String, String> details;
//
//        // Métodos del Builder
//        // Asigna un valor a un campo del builder.
//        // Esto permite encadenar llamadas
//        public Builder message(String message) {
//            this.message = message;
//            return this;
//        }
//
//        public Builder timestamp(LocalDateTime timestamp) {
//            this.timestamp = timestamp;
//            return this;
//        }
//
//        public Builder details(Map<String, String> details) {
//            this.details = details;
//            return this;
//        }
//
//        // Crea el objeto definitivo ErrorDTO, pasándole el builder con los valores ya configurados.
//        public ErrorDTO build() {
//            return new ErrorDTO(this);
//        }
//    }

}
