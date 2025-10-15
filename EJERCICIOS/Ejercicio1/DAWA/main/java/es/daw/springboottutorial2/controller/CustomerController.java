package es.daw.springboottutorial2.controller;

import es.daw.springboottutorial2.dto.CustomerDTO;
import es.daw.springboottutorial2.entity.Customer;
import es.daw.springboottutorial2.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
//@RequestMapping("/api/customer")
public class CustomerController {

    @Autowired
    private CustomerRepository customerRepository;

    @PostMapping("/add")
    // Fase 1
//    public String addCustomer(@RequestParam String first, @RequestParam String last) {
//        Customer customer = new Customer();
//        customer.setFirstName(first);
//        customer.setLastName(last);
//        customerRepository.save(customer);
//        return "Added new customer to repo!";
//    }
    public ResponseEntity<CustomerDTO> addCustomer(@RequestBody CustomerDTO customerDTO){
        // Mapear el DTO a su correspondiente Entity
        Customer customerEntity = new Customer();
        customerEntity.setFirstName(customerDTO.getFirstName());
        customerEntity.setLastName(customerDTO.getLastName());
        //customerRepository.save(customerDTO); //noooooooooooo porque customer es un DTP
        customerRepository.save(customerEntity);
        return ResponseEntity.status(HttpStatus.CREATED).body(customerDTO);
        //return ResponseEntity.ok(customerDTO);

    }

    @GetMapping("/list")
    //public Iterable<Customer> getCustomers() {
    public ResponseEntity<List<CustomerDTO>> getCustomers() {
        // PENDIENTE HACER LO MISMO DE FORMA IMPERATIVA
        // Recorro el List de entities que me devuelve el repositorio
        // Uno a uno cojo con get las propiedades y creo nuevos objetos DTO que
        // voy añadiendo a la lista de objetos CustomerDTO


        List<String> nombres = new ArrayList<>();
        List<Customer> customers = customerRepository.findAll();
        List<CustomerDTO> customerDTOS = new ArrayList<>();

        for (Customer customer : customers) {
            nombres.add(customer.getFirstName());

            customerDTOS.add(
                    new CustomerDTO(customer.getFirstName(), customer.getLastName())
            );
        }

        List<String> nombres2 = customerRepository.findAll()
                .stream()
                .map(customer -> customer.getFirstName())
                .collect(Collectors.toList());



        List<CustomerDTO> customerDTOs = customerRepository.findAll()
                .stream()
                .map(
                        c ->{
//                            CustomerDTO customerDTO = new CustomerDTO();
//                            customerDTO.setFirstName(c.getFirstName());
//                            customerDTO.setLastName(c.getLastName());
                            CustomerDTO customerDTO = new CustomerDTO(c.getFirstName(), c.getLastName());
                            return customerDTO;
                        }

                )
                .collect(Collectors.toList());

        return ResponseEntity.ok(customerDTOs);
    }

    @GetMapping("/find/{id}")
    //public Customer findCustomerById(@PathVariable Integer id) {
    public ResponseEntity<CustomerDTO> findCustomerById(@PathVariable Integer id) {
        //return customerRepository.findCustomerById(id);
        //return customerRepository.findById(id).get();
        //return  customerRepository.getCustomersById(id);
        //return customerRepository.findCustomerById(id);

        Optional<Customer> customer = customerRepository.findById(id);
        if  (customer.isPresent()) {
            CustomerDTO customerDTO = new CustomerDTO();
            customerDTO.setFirstName(customer.get().getFirstName());
            customerDTO.setLastName(customer.get().getLastName());
            return ResponseEntity.ok(customerDTO);
        }
        // no existe
        return ResponseEntity.notFound().build(); // vacío el body
    }
}

