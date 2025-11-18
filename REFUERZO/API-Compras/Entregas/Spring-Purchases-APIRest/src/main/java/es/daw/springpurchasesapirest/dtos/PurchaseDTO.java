package es.daw.springpurchasesapirest.dtos;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class PurchaseDTO {

    @NotBlank(message = "customer name is obligatory")
    @Size(min = 3, max = 20, message = "customer name between three and " +
            "twenty characters")
    private String customer;
    @PastOrPresent(message = "you can't buy from the future!")
    private LocalDateTime date;
    @DecimalMin(value = "400", message ="minimum price is 800" )
    private double total;
    @NotEmpty( message = "you have to buy at least one product")
    //TODO tell mariluz -  saw that you can add validation to the elements of
    // a collection
    private List<@Positive(message="product ID must be positive") Integer> products;


}
