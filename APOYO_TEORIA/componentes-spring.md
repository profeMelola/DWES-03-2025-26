# COMPONENTES: Principales Anotaciones de Spring

Estas anotaciones indican a Spring qué tipo de componente es cada clase dentro de la aplicación y permiten que el contenedor de Spring las gestione automáticamente como *beans*.

<img width="823" height="437" alt="imagen" src="https://github.com/user-attachments/assets/d8c439d9-ff2f-4152-b539-0fc2855a1b8c" />

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

--- 
# Scope


La anotación **@Scope** en Spring Boot se utiliza para definir el alcance de un componente gestionado por el contenedor de Spring. Permite especificar cómo se crean y se mantienen las instancias de un componente en el contexto de la aplicación.

Existen diferentes valores que se pueden asignar a la anotación @Scope:

- **Singleton (valor por defecto):** Indica que solo se creará una única instancia del componente en el contexto de la aplicación. Esta instancia será compartida por todos los hilos y solicitudes que accedan al componente.
- **Prototype:** Indica que se creará una nueva instancia del componente cada vez que sea solicitado. Cada solicitud obtendrá una instancia independiente del componente.
- **Request:** Indica que se creará una nueva instancia del componente para cada solicitud web que lo requiera. Cada solicitud obtendrá una instancia independiente del componente.
- **Session:** Indica que se creará una nueva instancia del componente para cada sesión web. Cada sesión obtendrá una instancia independiente del componente.
- **GlobalSession:** Similar al alcance de sesión, pero se utiliza en aplicaciones que utilizan el ámbito de sesión global.

Para usar la anotación @Scope, simplemente se debe colocar encima de la declaración de la clase del componente y especificar el valor del alcance deseado. Por ejemplo:

``` 
@Component
@Scope("prototype") // @Scope("singleton")
public class MiComponente {
   // ...
}
```

