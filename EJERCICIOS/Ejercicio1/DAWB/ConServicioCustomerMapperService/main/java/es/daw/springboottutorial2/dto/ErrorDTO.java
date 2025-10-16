package es.daw.springboottutorial2.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data // getter, setter, constructor de prop final, equals, hashcode y tostring
@NoArgsConstructor // constructor vacío
@AllArgsConstructor // constructor con todas las propiedades
public class ErrorDTO {
    private String message;
    private String code;
}
