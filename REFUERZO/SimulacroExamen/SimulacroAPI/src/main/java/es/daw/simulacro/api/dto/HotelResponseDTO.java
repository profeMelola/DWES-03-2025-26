package es.daw.simulacro.api.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HotelResponseDTO {

    private String codigo;
    private String nombre;
    private String descripcion;
    private boolean piscina;
    private String localidad;

    private CategoriaDTO categoria; // DTO anidado
}

