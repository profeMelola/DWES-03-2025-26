package es.daw.productoapirest.dto;

import lombok.Data;

@Data
public class AuthRequest {
    private String username;
    private String password;
}
