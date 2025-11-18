
package es.daw.springpurchasesapirest.repositories;
import es.daw.springpurchasesapirest.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ProductRepository extends JpaRepository<Product,Integer> {


}



