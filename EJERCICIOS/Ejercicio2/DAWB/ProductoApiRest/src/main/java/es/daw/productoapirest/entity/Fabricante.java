package es.daw.productoapirest.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
//@ToString
//@Table(name = "fabricante")
public class Fabricante {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer codigo;

    @Column(nullable = false, length = 255)
    private String nombre;

    // Relación 1:N con Producto
    @OneToMany(mappedBy = "fabricante", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Producto> productos;

//    @Override
//    public String toString() {
//        return "Fabricante{" +
//                "codigo=" + codigo +
//                ", nombre='" + nombre + '\'' +
//                //", productos=" + productos +
//                '}';
//    }
}
