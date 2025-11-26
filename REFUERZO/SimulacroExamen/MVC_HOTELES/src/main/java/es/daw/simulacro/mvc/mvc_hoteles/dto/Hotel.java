package es.daw.simulacro.mvc.mvc_hoteles.dto;

import lombok.Data;

@Data
public class Hotel {
    private String codigo;
    private String nombre;
    private String descripcion;
    private boolean piscina;
    private String localidad;
    private Categoria categoria;
}
