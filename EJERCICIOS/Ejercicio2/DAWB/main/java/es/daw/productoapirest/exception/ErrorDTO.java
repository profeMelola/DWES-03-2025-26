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
    // PATRÓN BUILDER ....
//    private ErrorDTO(Builder builder) {
//        this.message = builder.message;
//        this.timestamp = builder.timestamp;
//        this.details = builder.details;
//    }
//
//    public static class Builder{
//        private String message;
//        private LocalDateTime timestamp;
//        private Map<String, String> details;
//
//        public Builder message(String message){
//            this.message = message;
//            return this;
//        }
//
//        public Builder timestamp(LocalDateTime timestamp){
//            this.timestamp = timestamp;
//            return this;
//        }
//
//        public Builder details(Map<String, String> details){
//            this.details = details;
//            return this;
//        }
//
//        public ErrorDTO build(){
//            return new ErrorDTO(this);
//        }
//    }





}
