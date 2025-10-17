package es.daw.springboottutorial2.controller;

import es.daw.springboottutorial2.dto.CustomerDTO;
import es.daw.springboottutorial2.dto.ErrorDTO;
import es.daw.springboottutorial2.entity.Customer;
import es.daw.springboottutorial2.repository.CustomerRepository;
import es.daw.springboottutorial2.service.CustomerMapperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/customer")
public class CustomerController {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CustomerMapperService  customerMapperService;

    @PostMapping("/add")
    public ResponseEntity<CustomerDTO> addCustomer(@RequestBody CustomerDTO customerDTO){
        customerRepository.save(customerMapperService.toEntity(customerDTO));
        return ResponseEntity.status(HttpStatus.CREATED).body(customerDTO);
    }

    @GetMapping("/list")
    public ResponseEntity<List<CustomerDTO>> getCustomers() {
        return ResponseEntity.ok(customerMapperService.toDTO(customerRepository.findAll()));
    }

    @GetMapping("/find/{id}")
    //public ResponseEntity<CustomerDTO> findCustomerById(@PathVariable Integer id) {
    public ResponseEntity<?> findCustomerById(@PathVariable Integer id) {
        Optional<Customer> customer = customerRepository.findById(id);
        if  (customer.isPresent()) {
            return ResponseEntity.ok(customerMapperService.toDTO(customer.get()));
        }
        // no existe
        //return ResponseEntity.notFound().build(); // vacío el body

        // Si no encuentra el customer devolver un ErrorDTO con un código X y una descripción XXX
        ErrorDTO errorDTO = new ErrorDTO("Customer not found with id " + id,"C-404");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorDTO);
    }
}

