package es.daw.springboottutorial2.service;

import es.daw.springboottutorial2.dto.CustomerDTO;
import es.daw.springboottutorial2.entity.Customer;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerMapperService {
    public CustomerDTO toDTO(Customer customerEntity) {
        if (customerEntity == null) return null;
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setFirstName(customerEntity.getFirstName());
        customerDTO.setLastName(customerEntity.getLastName());
        return customerDTO;
    }

    public Customer toEntity(CustomerDTO customerDTO) {
        if (customerDTO == null) return null;
        Customer customer = new Customer();
        customer.setFirstName(customerDTO.getFirstName());
        customer.setLastName(customerDTO.getLastName());
        return customer;
    }

    public List<CustomerDTO> toDTO(List<Customer> entities) {
        return entities.stream()
                //.map(e -> toDTO(e))
                .map(this::toDTO)
                .toList();

    }

    public List<Customer> toEntity(List<CustomerDTO> dtos) {
        return dtos.stream()
                .map(this::toEntity)
                .toList();
    }
}
