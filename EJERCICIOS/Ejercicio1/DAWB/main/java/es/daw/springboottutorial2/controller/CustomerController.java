package es.daw.springboottutorial2.controller;

import es.daw.springboottutorial2.dto.CustomerDTO;
import es.daw.springboottutorial2.dto.ErrorDTO;
import es.daw.springboottutorial2.entity.Customer;
import es.daw.springboottutorial2.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    @Autowired
    private CustomerRepository customerRepository;

    @PostMapping("/add")
    public ResponseEntity<CustomerDTO> addCustomer(@RequestBody CustomerDTO customerDTO) {
    //public String addCustomer(@RequestParam String first, @RequestParam String last) {
        Customer customer = new Customer();
        customer.setFirstName(customerDTO.getFirstName());
        customer.setLastName(customerDTO.getLastName());
        customerRepository.save(customer);

        //return ResponseEntity.ok(customerDTO); // 200 + en el body un json con el customerDTO
        return ResponseEntity.status(HttpStatus.CREATED).body(customerDTO);

    }

    @GetMapping("/list")
    //public Iterable<Customer> getCustomers() {
    public ResponseEntity<List<CustomerDTO>> getCustomers() {

        // Transformar una lista de entities a una lista de DTOs
        // Forma 1:imperativo
//        List<Customer> customers = customerRepository.findAll();
//        List<CustomerDTO> customerDTOS = new ArrayList<>();
//
//        for (Customer customer : customers) {
////            CustomerDTO customerDTO = new CustomerDTO();
////            customerDTO.setFirstName(customer.getFirstName());
////            customerDTO.setLastName(customer.getLastName());
//            CustomerDTO customerDTO = new CustomerDTO(customer.getFirstName(), customer.getLastName());
//            customerDTOS.add(customerDTO);
//        }

        // Forma 2: api stream
        List<CustomerDTO> customerDTOList = customerRepository.findAll()
                .stream()
                .map(
                        c ->{
                            CustomerDTO customerDTO = new CustomerDTO();
                            customerDTO.setFirstName(c.getFirstName());
                            customerDTO.setLastName(c.getLastName());
                            return customerDTO;
                        }
                )
                .toList();
                //.collect(Collectors.toList());
        //return customerRepository.findAll();

        return ResponseEntity.ok(customerDTOList); //200 y en el body un json que es un array de objetos dto
    }

    @GetMapping("/find/{id}")
    //public Customer findCustomerById(@PathVariable Integer id) {
    //public ResponseEntity<CustomerDTO> findCustomerById(@PathVariable Integer id) {
    public ResponseEntity<?> findCustomerById(@PathVariable Integer id) {

        //return customerRepository.findCustomerById(id);
        //return customerRepository.findById(id).get();
        //return customerRepository.findCustomersById(id);


        Optional<Customer> customer = customerRepository.findById(id);
        if (customer.isPresent()) {
            CustomerDTO customerDTO = new CustomerDTO();
            customerDTO.setFirstName(customer.get().getFirstName());
            customerDTO.setLastName(customer.get().getLastName());
            return ResponseEntity.ok(customerDTO);
        }
        // Si pasa por aquí es que no lo ha encontrado!!!
        //return ResponseEntity.notFound().build();
        // 404
        //return ResponseEntity.ok(null);
        ErrorDTO errorDTO = new ErrorDTO("Que no existe torpedo!","666");

        //return ResponseEntity.notFound().body(errorDTO);

        //return ResponseEntity.notFound().build();

        return ResponseEntity.badRequest().body(errorDTO);

        //return ResponseEntity.notFound(). //?????




    }
}


