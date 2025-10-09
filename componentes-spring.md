# COMPONENTES: Principales Anotaciones de Spring

Estas anotaciones indican a Spring qué tipo de componente es cada clase dentro de la aplicación y permiten que el contenedor de Spring las gestione automáticamente como *beans*.

---

## @SpringBootApplication

Marca la clase principal de una aplicación Spring Boot.

Combina tres anotaciones:
- @Configuration: define una clase de configuración.
- @EnableAutoConfiguration: configura automáticamente los beans según las dependencias.
- @ComponentScan: busca componentes en el paquete actual y subpaquetes.

```java
@SpringBootApplication
public class DemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }
}
```

---

## @RestController

Indica que la clase es un controlador REST.
Combina @Controller y @ResponseBody.

Los métodos devuelven directamente JSON o XML, no vistas.

```java
@RestController
@RequestMapping("/api")
public class HelloController {

    @GetMapping("/hello")
    public String sayHello() {
        return "Hola desde API REST";
    }
}
```

---

## @Controller

Indica que la clase es un controlador MVC (para vistas HTML como Thymeleaf).

Los métodos devuelven el nombre de una vista, no datos.

```java
@Controller
public class HomeController {

    @GetMapping("/home")
    public String home(Model model) {
        model.addAttribute("mensaje", "Bienvenido");
        return "home"; // Thymeleaf buscará home.html
    }
}
```

---

## @Service

Marca una clase de lógica de negocio.
Permite separar la lógica de negocio del acceso a datos y de los controladores.

```java
@Service
public class UsuarioService {
    public List<String> listarUsuarios() {
        return List.of("Ana", "Luis", "Marta");
    }
}
```

---

## @Repository

Marca una clase o interfaz que accede a la base de datos.
Spring traduce las excepciones de base de datos a sus propias excepciones coherentes.

```java
@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    // Spring Data JPA implementa automáticamente los métodos
}
```

---

## @Entity

Indica que una clase es una entidad JPA, es decir, que se mapea a una tabla en la base de datos.

```java
@Entity
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    // Getters y setters
}
```

---

## @Configuration

Declara una clase de configuración de beans.
Permite definir manualmente beans con @Bean.

```java
@Configuration
public class AppConfig {

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
```

---

## Resumen

| Anotación | Rol en la aplicación | Tipo |
|----------|-----------------------|------|
| @SpringBootApplication | Clase principal de inicio | Configuración |
| @RestController | Controlador REST (devuelve JSON) | Web |
| @Controller | Controlador MVC (devuelve vistas) | Web |
| @Service | Lógica de negocio | Capa de servicio |
| @Repository | Acceso a datos | Capa de persistencia |
| @Entity | Entidad de base de datos | Modelo |
| @Configuration | Define beans/configuración | Configuración |
