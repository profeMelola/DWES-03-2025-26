package es.daw.springboottutorial2.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data

@NoArgsConstructor
@AllArgsConstructor
public class ErrorDTO {
    private String message;
    private String code;
}
