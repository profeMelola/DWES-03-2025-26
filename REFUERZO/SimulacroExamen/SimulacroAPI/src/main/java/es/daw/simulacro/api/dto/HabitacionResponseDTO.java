package es.daw.simulacro.api.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HabitacionResponseDTO {

    private String codigo;
    private int tamano;
    private boolean doble;
    private double precioNoche;
    private boolean incluyeDesayuno;
    private boolean ocupada;
}

