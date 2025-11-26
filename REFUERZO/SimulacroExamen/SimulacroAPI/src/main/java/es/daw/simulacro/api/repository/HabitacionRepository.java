package es.daw.simulacro.api.repository;

import es.daw.simulacro.api.entity.Habitacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface HabitacionRepository extends JpaRepository<Habitacion, Long> {

    Optional<Habitacion> findByCodigo(String codigo);

    // Navegación por relación: Hotel_Codigo
    /*
        SELECT hb.*
        FROM habitacion hb
        JOIN hotel h ON hb.hotel_id = h.id
        WHERE h.codigo = ?
          AND hb.ocupada = false;
     */
    List<Habitacion> findByHotel_CodigoAndOcupadaFalse(String codigoHotel);


    /*
        - Muy fáciles de escribir.
        - Muy rápidos de entender.
        - Muy seguros (tipados y validados por Spring).
        - Perfectos para CRUD y filtros simples.
        - No necesitas aprender JPQL.
     */
    /*
        SELECT hb.*
        FROM habitacion hb
        JOIN hotel h ON hb.hotel_id = h.id
        WHERE h.codigo = ?
          AND hb.ocupada = false
          AND hb.tamano >= ?
          AND hb.precio_noche BETWEEN ? AND ?;
     */
    List<Habitacion> findByHotel_CodigoAndOcupadaFalseAndTamanoGreaterThanEqualAndPrecioNocheBetween(
            String codigoHotel, int tamanoMinimo, double precioMinimo, double precioMaximo
    );

    // --------------------------------- JPQL ----------------------------
    // FORMA 2: NO ENTRA EN EXAMEN DEL 1 DE DICIEMBRE... JPQL AVANZADO
    // Si necesitas funciones específicas de tu BD y muy buen rendimiento
    @Query("""
       SELECT h
       FROM Habitacion h
       JOIN h.hotel ht
       WHERE ht.codigo = :codigoHotel
         AND h.ocupada = false
         AND h.tamano >= :tamanoMin
         AND h.precioNoche BETWEEN :precioMin AND :precioMax
       """)
    List<Habitacion> buscarHabitacionesDisponibles(
            String codigoHotel,
            int tamanoMin,
            double precioMin,
            double precioMax
    );



    // FORMA 3: QUERY NATIVO... DEPENDES DE LA BASE DE DATOS. NO HAY PORTABILIDAD.
}

