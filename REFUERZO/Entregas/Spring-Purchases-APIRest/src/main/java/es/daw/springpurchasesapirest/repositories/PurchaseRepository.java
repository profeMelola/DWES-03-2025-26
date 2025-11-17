package es.daw.springpurchasesapirest.repositories;

import es.daw.springpurchasesapirest.entities.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


public interface PurchaseRepository extends JpaRepository<Purchase, Long> {



    //TODO search for another way to filter by date
    Optional<List<Purchase>> findByDateBetween(LocalDateTime start,
                                        LocalDateTime end);

    // ERROR -> because contains mapts to SQL LIKE, Entity is LocalDateTime...
    //Optional<List<PurchaseDTO>> findByDateContains(String date);
}



    