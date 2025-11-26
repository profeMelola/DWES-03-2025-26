package es.daw.simulacro.api.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name="HABITACION")
public class Habitacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String codigo;

    private int tamano;
    private boolean doble;
    private double precioNoche;
    private boolean incluyeDesayuno;
    private boolean ocupada;

    @ManyToOne
    @JoinColumn(name = "hotel_id", nullable = false)
    // La tabla HABITACION tiene una columna FK hotel_id.
    // Una habitación siempre debe estar asociada a un hotel.
    private Hotel hotel;
}
