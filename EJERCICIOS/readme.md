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
- Usar DTO.
- Implementar un servicio de mapeo entre DTO y Entity.
- Usar ResponseEntity<T> como respuesta de los endpoints.
- Usar JpaRepository.
- Cargar datos de prueba desde un script sql (data.sl).
- Propiedades de configuración H2.
  


---
# Curiosidad: uso nativo de HttpServletRequest

```
    @PostMapping("/add")
    public String add(@RequestParam String nombre, @RequestParam int precio, @RequestParam String sku) {
        Producto producto = new Producto();
        producto.setNombre(nombre);
        producto.setPrecio(precio);
        producto.setSku(sku);
        productoRepository.save(producto);
        return "Added new product to repo!";
    }

    @PostMapping("/add-nativo")
    public String add(HttpServletRequest request) {
        ProductoDTO productoDTO = new ProductoDTO();
        productoDTO.setNombre(request.getParameter("nombre"));
        productoDTO.setSku(request.getParameter("sku"));
        productoDTO.setPrecio(Integer.parseInt(request.getParameter("precio")));

        productoService.convert2Producto(productoDTO).ifPresent(producto -> productoRepository.save(producto));

        return "Added new product to repo!";

    }
```

---
# EJERCICIO 2: RestController, CRUD de productos

Vamos a partir de la base de datos H2 usada en el ejercicio de [Productos JDBC](https://github.com/profeMelola/DWES-02-2025-26/tree/main/EJERCICIOS/JDBC/EJERCICIOS/Productos)


Llama a tu proyecto **ProductosRestController**.

Aprenderemos a:
- Crear la entidad de persistencia Producto.
- Crear el repositorio asociado a Producto.
- Crear el controlador Rest con diferentes endpoints. 
- Realizar operaciones CRUD sobre la tabla Productos.

Sigue las instrucciones del profesor...

[¿Qué es un API Rest?](.././APOYO_TEORIA/API-REST.md)

## Entidades JPA

### Fabricante

```
@Entity
@Table(name = "fabricante")
public class Fabricante {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer codigo;

    @Column(nullable = false, length = 255)
    private String nombre;

    // Relación 1:N con Producto
    @OneToMany(mappedBy = "fabricante", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Producto> productos;

}
```

| Anotación                                                                              | Descripción                                                                                           |
| -------------------------------------------------------------------------------------- | ----------------------------------------------------------------------------------------------------- |
| `@Entity`                                                                              | Indica que la clase es una **entidad JPA** y se mapeará a una tabla en la base de datos.              |
| `@Table(name = "fabricante")`                                                          | Especifica el **nombre de la tabla**. Si no se indica, se usa el nombre de la clase.                  |
| `@Id`                                                                                  | Marca el campo como **clave primaria**.                                                               |
| `@GeneratedValue(strategy = GenerationType.IDENTITY)`                                  | Indica que el valor del ID se **autogenera por la base de datos** (autoincremental en H2).            |
| `@Column(nullable = false, length = 255)`                                              | Define las restricciones de la columna (no nulo y longitud máxima).                                   |
| `@OneToMany(mappedBy = "fabricante", cascade = CascadeType.ALL, orphanRemoval = true)` | Relación **uno a muchos**: un fabricante tiene muchos productos.                                      |
| `mappedBy`                                                                             | Indica que el **lado propietario de la relación** está en la entidad `Producto` (campo `fabricante`). |
| `cascade = CascadeType.ALL`                                                            | Propaga todas las operaciones (persist, remove, etc.) al conjunto de productos.                       |
| `orphanRemoval = true`                                                                 | Si se elimina un producto de la lista, también se elimina en la base de datos.                        |


### Producto

```
@Entity
@Table(name = "producto")
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer codigo;

    @Column(nullable = false, length = 255)
    private String nombre;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal precio;

    // Relación muchos a uno con Fabricante
    @ManyToOne(optional = false)
    @JoinColumn(name = "codigo_fabricante", nullable = false)
    private Fabricante fabricante;
```

| Anotación                                                   | Descripción                                                                                 |
| ----------------------------------------------------------- | ------------------------------------------------------------------------------------------- |
| `@Entity`                                                   | Indica que la clase representa una tabla en la BD.                                          |
| `@Table(name = "producto")`                                 | Asocia la clase con la tabla `producto`.                                                    |
| `@Id`                                                       | Define el campo `codigo` como **clave primaria**.                                           |
| `@GeneratedValue(strategy = GenerationType.IDENTITY)`       | Hace que el ID sea **autogenerado** en la BD.                                               |
| `@Column(nullable = false, length = 255)`                   | Define las restricciones del campo `nombre`.                                                |
| `@Column(nullable = false, precision = 10, scale = 2)`      | Define el tipo decimal con 10 dígitos totales y 2 decimales, equivalente a `DECIMAL(10,2)`. |
| `@ManyToOne(optional = false)`                              | Relación **muchos a uno** con `Fabricante`. Cada producto pertenece a un fabricante.        |
| `@JoinColumn(name = "codigo_fabricante", nullable = false)` | Crea la **columna de clave foránea** (`codigo_fabricante`) y define que es obligatoria.     |

--- 
# AMPLIACIONES

## Usar MapStruct
Herramienta que genera automáticamente el código de mapeo entre objetos, muy útil para convertir entre entidades JPA (Entity) y DTOs sin escribir código repetitivo.
[Cómo integrar MapStruct en SpringBoot](https://github.com/profeMelola/DWES-03-2025-26/blob/main/APOYO_TEORIA/Integracion_MapStruct_SpringBoot.md)

---
## Especificaciones de los endpoints

- Todos los endpoints devuelven ResponseEntity.
- Usa DTOs para comunicarte con el exterior (nunca expongas las entidades).

| Método     | Endpoint              | Descripción                             | Request Body                             | Response                  | Código HTTP                        |
| ---------- | --------------------- | --------------------------------------- | ---------------------------------------- | ------------------------- | ---------------------------------- |
| **POST**   | `/api/productos`      | Crea un nuevo producto                  | `ProductoDTO` (codigo,nombre, precio) | `ProductoDTO` creado      | `201 Created`                      |
| **GET**    | `/api/productos`      | Obtiene la lista de todos los productos | —                                        | `List<ProductoDTO>`       | `200 OK`                           |
| **GET**    | `/api/productos/{codigo}` | Obtiene un producto por su código           | —                                        | `ProductoDTO`             | `200 OK` o `404 Not Found`         |
| **PUT**    | `/api/productos/{codigo}` | Actualiza un producto existente         | `ProductoDTO`                            | `ProductoDTO` actualizado | `200 OK` o `404 Not Found`         |
| **DELETE** | `/api/productos/{codigo}` | Elimina un producto existente           | —                                        | —                         | `204 No Content` o `404 Not Found` |


En tu entidad Producto, el campo fabricante es obligatorio (optional = false), por lo que el DTO debe incluirlo de alguna forma en el POST /api/productos:

```
@Data
public class ProductoDTO {
    private String codigo;
    private String nombre;
    private BigDecimal precio;
    private Integer codigoFabricante; // ← nuevo campo
}

```

Ejemplo de JSON de entrada:

```
{
  "codigo": "A001",
  "nombre": "Teclado mecánico",
  "precio": 59.99,
  "codigoFabricante": 3
}

```
---

## Manejar excepciones

**1. Crear un DTO para guardar información de errores:**

Se llamará **ErrorDTO**  y tendrá estos dos atributos. Usa Lombok para completar el resto de código.

```
    private String message;
    private String details;
```

**2. Crear @ControllerAdvice para manejar excepciones:**

ControllerAdvice centraliza el manejo de excepciones en toda la aplicación.

```
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NumberFormatException.class)
    public ResponseEntity<ErrorDTO> handleNumberFormatException(NumberFormatException ex) {
        ErrorDTO error = new ErrorDTO(
                "Invalid number format",
                ex.getMessage()
        );
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    // Puedes añadir más métodos @ExceptionHandler para manejar otras excepciones.
}

```

**3. Añadir al controlador REST la gestión del error:**

Fíjate en este código de ejemplo y vamos a ver cómo aplicarlo a nuestro controlador rest:

```
    @GetMapping("/parse-int")
    public String parseInteger(@RequestParam String number) {
        int parsedNumber = Integer.parseInt(number); // Puede lanzar NumberFormatException
        return "Parsed number: " + parsedNumber;
    }
```

**4. Añadir gestión genérica de excepciones no previstas:**

Agregamos un método en la clase GlobalExceptionHandler para Exception:

```
@ExceptionHandler(Exception.class)
public ResponseEntity<ErrorDTO> handleGenericException(Exception ex) {
    ErrorDTO error = new ErrorDTO(
            "Internal Server Error",
            ex.getMessage()
    );
    return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
}

```

___

## Validaciones

Es necesario añadir la dependencia **Spring Boot Starter Validation (Bean Validation)**.

Añadimos desde el pom.xml -> add starter...

Buscamos Validation y automáticamente aparecerá la dependencia:


```
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-validation</artifactId>
        </dependency>
```

Estas son las anotaciones más comunes:

![image](https://github.com/user-attachments/assets/fbe1bbbe-2f6c-4fa9-8ebf-b2208e6f48f5)


Vamos a añadir validaciones automáticas a ProductoDTO:

```
    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;
    @NotBlank(message = "El SKU es obligatorio")
    @Size(min = 4, max = 4, message = "El SKU debe tener exactamente 4 caracteres")
    private String sku;
    @Min(value = 100, message = "El precio debe superior a 99")
    private int precio;
```

En el controlador rest vamos a añadir la validación de productoDTO. Observa:

```
@PostMapping("/add")
    public String add(@Valid @RequestBody ProductoDTO productoDTO) {
              ...
    }
```
Cuando Spring encuentra @Valid, ejecuta las validaciones de ProductoDTO antes de llamar al método. 

Si alguna validación falla, Spring lanza una excepción (MethodArgumentNotValidException) y devolverá automáticamente un código de estado HTTP 400 Bad Request y un mensaje de error que describe las validaciones fallidas.

Por ejemplo, si intentas enviar un JSON con datos inválidos:

```
{
    "id": null,
    "nombre": "",
    "precio": -5,
    "descripcion": "Esto es una descripción demasiado larga que excede el límite de 200 caracteres. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.",
    "fechaExpiracion": "2023-01-01"
}
```

```
{
    "timestamp": "2025-01-09T10:30:00",
    "status": 400,
    "errors": [
        "El ID del producto no puede ser nulo",
        "El nombre del producto es obligatorio",
        "El precio debe ser mayor a 0",
        "La descripción no puede tener más de 200 caracteres",
        "La fecha de expiración debe ser en el futuro"
    ]
}
``` 

Ejemplo de otras validaciones:

![image](https://github.com/user-attachments/assets/cb5168ba-e896-49fc-b17d-d9731ee5feea)


___

## Configuración personalizada

Podemos añadir parámetros de configuración directamente en el archivo **application.properties**. Observa:

```
spring.application.name=primerCrud

# Configuración de H2
spring.datasource.url=jdbc:h2:file:~/tienda_practica;AUTO_SERVER=TRUE
#spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.driver-class-name=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
spring.h2.console.enabled=true
spring.h2.console.path=/h2-console

#configuraciones personalizadas
config.daw.code=666
config.daw.message=primer controlador REST
```

Para acceder a dichos valores desde una clase java de nuestro proyecto, por ejemplo en **ProductoController.java:**

```
    // ----------------------------------
    // CONFIGURACIÓN PERSONALIZADA
    @Value("${config.daw.code}")
    private String code_conf;
    @Value("${config.daw.message}")
    private String message_conf;
    //-------------------------------------
```

Vamos a crear dos endpoints para realizar las pruebas y ver que obtenemos los valores correctamente:

```
        @GetMapping("/values-conf")
    public Map<String,String> values(){
        Map<String,String> json = new HashMap<>();
        json.put("code",code_conf);
        json.put("message",message_conf);
        return json;
    }


    @GetMapping("/values-conf2")
    public Map<String,String> values(@Value("${config.daw.code}") String code, @Value("${config.daw.message}") String message){
        Map<String,String> json = new HashMap<>();
        json.put("code",code);
        json.put("message",message);
        return json;
    }
```
Como mejora, vamos a cambiar el Map por un objeto DTO con los parámetros de configuración y usaremos un archivo de properties externo.

### Agregando otros archivos properties personalizados para las configuraciones

Además de usar un fichero de propiedades externo, usaremos DTO y @ConfigurationProperties.

Sigue los pasos:

**Creamos el archivo de propiedades externo:**

En: src/main/resources/mi-config.properties

```
# Archivo externo: mi-config.properties
config.daw.code=pruebaCode
config.daw.message=Mensaje personalizado
```

**Configuramos el uso de dicho fichero de propiedades externo:**

En application.properties añadimos:

```
#Esto carga tu archivo mi-config.properties junto con otros archivos de configuración.
spring.config.import=classpath:mi-config.properties
```

También podría añadirse a la clase marcada como @Configuration:

```
@Configuration
@PropertySource(value = "classpath:mi-config.properties", encoding = "UTF-8")
@EnableConfigurationProperties(DawConfig.class)
public class AppConfig {
    // No necesita contenido: su función es registrar la configuración
}
```

**DTO DawResponse con Lombok**

Es el objeto que devolverá el endpoint con los valores de los parámetros de configuración:

```

@Data // Genera getters, setters, equals, constructor con solo campos requeridos, hashCode y toString automáticamente.
@AllArgsConstructor // Genera un constructor con todos los campos.
@NoArgsConstructor // Genera un constructor vacío.
public class DawResponseDTO {
    private String code;
    private String message;
}
```

Ten en cuenta que Jackson neesita deserializar de DTO a JSON y requiere un constructor sin argumento o constructores con todos los campos, por tanto poner los atributos final puede complicar dicha serialización.

**Clase DawConfig (en el paquete config) con @ConfigurationProperties**

La clase que representa las propiedades:

```
@Configuration
@ConfigurationProperties(prefix = "config.daw")
@Data
@NoArgsConstructor
public class DawConfig {
    private String code;
    private String message;
}
```

**Creamos un controlador nuevo, específico para leer la configuración:**

```
@RestController
public class DawController {

    @Autowired
    private DawConfig dawConfig;

    @GetMapping("/values-conf")
    public DawResponseDTO values() {
        return new DawResponseDTO(dawConfig.getCode(), dawConfig.getMessage());
    }

}
```

---

## Cambios en los DTO

### Incluir un objeto anidado simple

```
@Data
public class ProductoDTO {
    private String codigo;
    private String nombre;
    private BigDecimal precio;
    private FabricanteDTO fabricante;
}

```

### Dos DTO separados para entrada y salida

```
@Data
public class ProductoCreateDTO {
    private String codigo;
    private String nombre;
    private BigDecimal precio;
    private Integer fabricanteId;
}

@Data
public class ProductoDTO {
    private String codigo;
    private String nombre;
    private BigDecimal precio;
    private String nombreFabricante;
}

```

---

# Añadir Autenticación y Autorización

JWT (JSON Web Token) es un estándar para la creación de tokens de acceso. 

Se utiliza principalmente para autenticación y autorización.

**Funcionamiento:**

- Autenticación: El usuario se autentica ingresando sus credenciales.
- Generación: El servidor genera un JWT y lo devuelve al cliente.
- Autorización: El cliente envía el JWT en las solicitudes subsecuentes para acceder a recursos protegidos.
- Verificación: El servidor verifica el JWT antes de conceder acceso.