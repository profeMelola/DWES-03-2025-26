package es.daw.productoapirest.repository;

import es.daw.productoapirest.entity.Fabricante;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FabricanteRepository extends JpaRepository<Fabricante, Integer> {
}
