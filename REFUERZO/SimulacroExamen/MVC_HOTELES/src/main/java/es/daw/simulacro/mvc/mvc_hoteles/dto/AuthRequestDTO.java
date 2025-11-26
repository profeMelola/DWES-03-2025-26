package es.daw.simulacro.mvc.mvc_hoteles.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AuthRequestDTO {
    private String username;
    private String password;
}
