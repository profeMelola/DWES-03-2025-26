package es.daw.productoapirest.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class RegisterRequest {
    @NotBlank(message="El nombre de usuario es obligatorio")
    private String username;
    @NotBlank(message="La contraseña es obligatoria")
    private String password;

    private String role; // pendiente!!!!.... poder dar de alta un usuario con más de un role

//    private List<String> roles = new ArrayList<>(); // lista vacía

    /*
    {
        "username": "pepe",
        "password": "1324",
        "roles": ["admin","editor"]
    }
     */
}
