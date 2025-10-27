package es.daw.productoapirest.repository;

import es.daw.productoapirest.entity.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductoRepository extends JpaRepository<Producto,Integer> {
    Optional<Producto> findByCodigo(String codigo);
}
