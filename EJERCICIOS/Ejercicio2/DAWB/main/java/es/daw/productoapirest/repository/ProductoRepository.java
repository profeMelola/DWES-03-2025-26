package es.daw.productoapirest.repository;


import es.daw.productoapirest.entity.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {
    // JpaRepository ya incluye métodos básicos como save(), findById(), findAll(), deleteById(), etc.
}