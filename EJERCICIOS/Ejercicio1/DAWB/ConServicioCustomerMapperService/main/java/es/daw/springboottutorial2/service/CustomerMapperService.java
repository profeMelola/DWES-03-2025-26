package es.daw.springboottutorial2.service;

import es.daw.springboottutorial2.dto.CustomerDTO;
import es.daw.springboottutorial2.entity.Customer;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerMapperService {

    public CustomerDTO toDTO(Customer entity) {
        if (entity == null)  return null;
        CustomerDTO dto = new CustomerDTO();
        dto.setLastName(entity.getLastName());
        dto.setFirstName(entity.getFirstName());

        return dto;
    }

    public Customer toEntity(CustomerDTO dto) {
        if (dto == null)  return null;
        Customer entity = new Customer();
        entity.setLastName(dto.getLastName());
        entity.setFirstName(dto.getFirstName());
        return entity;
    }

    public List<CustomerDTO> toDTO(List<Customer> entities) {
        return entities.stream()
                .map(this::toDTO)
                //.collect(Collectors.toList());
                .toList();
    }

    public List<Customer> toEntity(List<CustomerDTO> dtos) {
        return dtos.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }

}
