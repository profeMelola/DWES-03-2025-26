package es.daw.simulacro.api.repository;


import es.daw.simulacro.api.entity.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface HotelRepository extends JpaRepository<Hotel, Long> {

    Optional<Hotel> findByCodigo(String codigo);

    List<Hotel> findByLocalidadIgnoreCase(String localidad);

    // Busca todos los hoteles cuya categoría tenga un determinado código.
    // Guión bajo: navegación por relaciones
    /*
        SELECT h.*
        FROM hotel h
        JOIN categoria c ON h.categoria_id = c.id
        WHERE c.codigo = ?;
     */
    List<Hotel> findByCategoria_Codigo(String codigoCategoria);
}

