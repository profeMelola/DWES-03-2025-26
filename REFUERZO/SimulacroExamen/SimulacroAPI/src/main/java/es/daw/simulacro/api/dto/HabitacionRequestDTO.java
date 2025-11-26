package es.daw.simulacro.api.dto;

import jakarta.validation.constraints.*;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HabitacionRequestDTO {

    @NotBlank
    private String codigo;

    @Min(11) // mayor que 10 m²
    private int tamano;

    private boolean doble;

    @Positive
    private double precioNoche;

    private boolean incluyeDesayuno;
}

