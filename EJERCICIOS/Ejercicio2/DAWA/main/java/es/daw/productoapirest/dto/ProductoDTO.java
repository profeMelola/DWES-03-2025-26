package es.daw.productoapirest.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductoDTO {
    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;
    @DecimalMin(value = "100.00", message = "El precio debe superior a 99")
    private BigDecimal precio;
    @Size(min = 4, max = 4, message = "El codigo debe tener exactamente 4 caracteres")
    private String codigo;
    private Integer codigoFabricante;
}
