package es.daw.simulacro.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HotelRequestDTO {

    @NotBlank
    private String codigo;

    @NotBlank
    private String nombre;

    @NotBlank
    private String descripcion;

    private boolean piscina;

    @NotBlank
    private String localidad;

    @NotBlank
    private String codigoCategoria; // Se recibe el código, no el objeto
}

