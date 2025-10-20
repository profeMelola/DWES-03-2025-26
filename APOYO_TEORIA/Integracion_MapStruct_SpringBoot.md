# 🧩 Integración de MapStruct en Spring Boot

MapStruct es una herramienta que genera automáticamente el código de **mapeo entre objetos**, muy útil para convertir entre **entidades JPA (`Entity`)** y **DTOs** sin escribir código repetitivo.

---

## 🚀 1. Dependencias Maven

Agrega estas dependencias en tu archivo `pom.xml`:

```xml
<dependencies>
    <!-- MapStruct -->
    <!-- https://mvnrepository.com/artifact/org.mapstruct/mapstruct -->
    <dependency>
        <groupId>org.mapstruct</groupId>
        <artifactId>mapstruct</artifactId>
        <version>1.5.5.Final</version>
    </dependency>

    <!-- Procesador de anotaciones (necesario para generar código) -->
    <!-- https://mvnrepository.com/artifact/org.mapstruct/mapstruct-processor -->
    <dependency>
        <groupId>org.mapstruct</groupId>
        <artifactId>mapstruct-processor</artifactId>
        <version>1.5.5.Final</version>
    </dependency>
</dependencies>

<build>
    <plugins>
        <!-- Generación automática del código de MapStruct en compilación -->
        <plugin>
            <groupId>org.apache.maven.plugins</groupId>
            <artifactId>maven-compiler-plugin</artifactId>
            <version>3.11.0</version>
            <configuration>
                <source>17</source>
                <target>17</target>
                <annotationProcessorPaths>
                    <path>
                        <groupId>org.mapstruct</groupId>
                        <artifactId>mapstruct-processor</artifactId>
                        <version>1.6.2</version>
                    </path>
                </annotationProcessorPaths>
            </configuration>
        </plugin>
    </plugins>
</build>
```

> 🧠 *Nota:* MapStruct genera las clases en `target/generated-sources/annotations`.

---

## 🏗️ 2. Estructura del proyecto

```
src/
 └── main/java/com/example/myapp/
      ├── entity/         → Entidades JPA
      ├── model/          → Clases DTO
      ├── mapper/         → Interfaces de MapStruct
      ├── repository/     → Repositorios JPA
      ├── service/        → Lógica de negocio
      └── controller/     → Controladores REST
```

---

## 🧱 3. Crear el Mapper

```java
package com.example.myapp.mapper;

import com.example.myapp.entity.Customer;
import com.example.myapp.model.CustomerDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CustomerMapper {

    CustomerDTO toDto(Customer entity);

    Customer toEntity(CustomerDTO dto);
}
```

**componentModel = "spring"** → Genera una implementación (CustomerMapperImpl) que sea un bean de Spring, para que pueda inyectarse automáticamente con @Autowired o mediante constructor

---

## ⚙️ 4. Uso en el controlador

```java
package com.example.myapp.controller;

import com.example.myapp.entity.Customer;
import com.example.myapp.model.CustomerDTO;
import com.example.myapp.mapper.CustomerMapper;
import com.example.myapp.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    @Autowired
    public CustomerController(CustomerRepository customerRepository, CustomerMapper customerMapper) {
        this.customerRepository = customerRepository;
        this.customerMapper = customerMapper;
    }

    @PostMapping
    public ResponseEntity<CustomerDTO> addCustomer(@RequestBody CustomerDTO dto) {
        Customer entity = customerMapper.toEntity(dto);
        Customer saved = customerRepository.save(entity);
        return ResponseEntity.status(201).body(customerMapper.toDto(saved));
    }

    @GetMapping
    public ResponseEntity<List<CustomerDTO>> getCustomers() {
        List<CustomerDTO> list = customerRepository.findAll())
                .stream()
                .map(customerMapper::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerDTO> findCustomerById(@PathVariable Integer id) {
        Optional<Customer> customerOpt = Optional.ofNullable(customerRepository.findCustomerById(id));
        return customerOpt
                .map(customer -> ResponseEntity.ok(customerMapper.toDto(customer)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
```

---

## 🧰 5. Ejemplo avanzado (nombres distintos y listas)

```java
package com.example.myapp.mapper;

import com.example.myapp.entity.Customer;
import com.example.myapp.model.CustomerDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import java.util.List;

@Mapper(componentModel = "spring")
public interface CustomerMapper {

    @Mapping(source = "firstName", target = "nombre")
    @Mapping(source = "lastName", target = "apellido")
    CustomerDTO toDto(Customer entity);

    @Mapping(source = "nombre", target = "firstName")
    @Mapping(source = "apellido", target = "lastName")
    Customer toEntity(CustomerDTO dto);


    List<CustomerDTO> toDtoList(List<Customer> entities);
}
```

Toma el campo firstName de Customer → lo asigna al campo nombre en CustomerDTO.

Toma lastName → lo asigna a apellido.

Si los nombres fueran iguales, no haría falta escribir nada: MapStruct lo hace automáticamente.

---

## 🎯 6. Beneficios de MapStruct

✅ Código limpio y sin duplicaciones.  
✅ Rápido (sin reflexión, se ejecuta como código Java normal).  
✅ Errores detectables en tiempo de compilación.  
✅ Perfectamente integrable con Spring Boot.


---

## 7. Ahora aplica MapStruct en el ejercicio 1

@Mapper(componentModel = "spring")
public interface CustomerMapper {

    // ✅ Entity → DTO
    CustomerDTO toDto(Customer entity);

    // ✅ DTO → Entity
    Customer toEntity(CustomerDTO dto);

    // ✅ Lista de entidades → lista de DTOs
    List<CustomerDTO> toDtoList(List<Customer> entities);

    // ✅ Lista de DTOs → lista de entidades
    List<Customer> toEntityList(List<CustomerDTO> dtos);
}

---

📚 **Referencia oficial:**  
👉 [https://mapstruct.org/](https://mapstruct.org/)
