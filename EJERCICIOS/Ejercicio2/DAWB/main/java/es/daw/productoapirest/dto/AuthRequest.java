package es.daw.productoapirest.dto;

import lombok.Data;

@Data
public class AuthRequest {
    // Pendiente validaciones...
    private String password;
    private String username;

}
