package es.daw.springboottutorial2.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
//@AllArgsConstructor
//@NoArgsConstructor
//@ToString
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    //@Column(name="firstName")
    private String firstName;
    private String lastName;

}


