package es.daw.springboottutorial2.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDTO {
    private String firstName;
    private String lastName;

    //    @Override
//    public boolean equals(Object o) {
//        if (o == null || getClass() != o.getClass()) return false;
//        CustomerDTO that = (CustomerDTO) o;
//        return Objects.equals(firstName, that.firstName);
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hashCode(firstName);
//    }
}
