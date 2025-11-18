package es.daw.springpurchasesapirest.dtos;


import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.List;

@Data
public class RegisterRequest {

    @NotBlank(message = "El nombre de usuario es obligatorio")
    private String username;

    @NotBlank(message = "La constraseña es obligatoria")
    private String password;

    private List<String> roles;
}