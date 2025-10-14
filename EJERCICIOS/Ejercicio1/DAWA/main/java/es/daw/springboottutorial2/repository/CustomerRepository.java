package es.daw.springboottutorial2.repository;


import es.daw.springboottutorial2.entity.Customer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends CrudRepository<Customer, Integer> {

    Customer findCustomerById(Integer id);

    //Optional<Customer> findByFirstNameAndLastName(String firstName, String lastName);
}


