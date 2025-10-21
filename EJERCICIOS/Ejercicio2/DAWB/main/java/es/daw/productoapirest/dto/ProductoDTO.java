package es.daw.productoapirest.dto;

import lombok.Data;

@Data
public class ProductoDTO {
    private String nombre;
    private Double precio;
    private Integer cantidad;
}
