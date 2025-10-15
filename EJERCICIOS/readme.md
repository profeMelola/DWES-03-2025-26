# EJERCICIO 1: SpringBootTutorial

<img width="166" height="60" alt="image" src="https://github.com/user-attachments/assets/033e436e-e819-4966-9e1d-da886f7f5db1" />

https://www.jetbrains.com/help/idea/spring-support-tutorial.html

This tutorial guides you through steps that cover the following:

- Add dependencies for JPA and H2 that enable your Spring application to store and retrieve relational data
- Write and examine your code
- Run your application and execute HTTP requests
- Add Spring Boot Actuator for advanced health monitoring and endpoint analysis
- Add Spring Boot Developer Tools for faster application updates

## Mejoras incorporadas en clase 

- Estructurar el proyecto en paquetes.
- Usar Lombok.
- Usar DTO y servicio de mapeo entre DTO y Entity.
- Usar ResponseEntity<T> como respuesta de los endpoints.
- Usar MapStruct: herramienta que genera automáticamente el código de mapeo entre objetos, muy útil para convertir entre entidades JPA (Entity) y DTOs sin escribir código repetitivo.
https://github.com/profeMelola/DWES-03-2025-26/blob/main/APOYO_TEORIA/Integracion_MapStruct_SpringBoot.md

# EJERCICIO 2: RestController, CRUD de productos

Vamos a partir de la base de datos H2 usada en el ejercicio de [Productos JDBC](https://github.com/profeMelola/DWES-02-2025-26/tree/main/EJERCICIOS/JDBC/EJERCICIOS/Productos)


Llama a tu proyecto **primerRestController**.

Aprenderemos a:
- Crear la entidad de persistencia Producto.
- Crear el repositorio asociado a Producto.
- Crear el controlador Rest con diferentes endpoints. 
- Realizar operaciones CRUD sobre la tabla Productos.

Sigue las instrucciones del profesor...
