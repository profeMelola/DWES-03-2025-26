package es.daw.springpurchasesapirest.repositories;

import es.daw.springpurchasesapirest.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository  extends JpaRepository<Role, Long> {
    Optional<Role> findByName(String name);
}