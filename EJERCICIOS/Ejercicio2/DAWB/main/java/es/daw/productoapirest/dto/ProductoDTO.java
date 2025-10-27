package es.daw.productoapirest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
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

    // pendiente anotación con expresión regular 3digitos seguidos de una letra [0-9]{3}[a-z]{1}
    @Size(min = 4, max = 4, message = "El código debe tener exactamente 4 caracteres")
    private String codigo;


    // Solo se usa esta propiedad al crear un producto, no se devuelve al listar
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Integer codigoFabricante;

}
