package es.daw.simulacro.api.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name="CATEGORIA")
public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String codigo;

    private String nombre;
    private String descripcion;

    // mappedBy: El atributo propietario está en Hotel.categoria.
    // Si borras o guardas una categoría, se afecta automáticamente a todos sus hoteles.
    // Si un hotel se elimina de la lista hoteles, se borra físicamente de la BD.
    @OneToMany(mappedBy = "categoria", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Hotel> hoteles;
}
