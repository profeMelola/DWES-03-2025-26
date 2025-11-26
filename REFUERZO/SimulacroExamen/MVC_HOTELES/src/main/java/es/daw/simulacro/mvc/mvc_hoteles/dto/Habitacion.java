package es.daw.simulacro.mvc.mvc_hoteles.dto;

import lombok.Data;

@Data
public class Habitacion {
    private String codigo;
    private int tamano;
    private boolean doble;
    private double precioNoche;
    private boolean incluyeDesayuno;
    private boolean ocupada;
}
