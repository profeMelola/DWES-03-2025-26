# Comparativa: JPA "puro" vs Spring Data JPA

## Objetivo

Comprender cómo **Spring Data JPA** simplifica el uso de **JPA
estándar**, automatizando gran parte del código repetitivo.

------------------------------------------------------------------------

## JPA "puro" (sin Spring)

### Estructura básica

``` plaintext
src/
 └─ META-INF/
     └─ persistence.xml
```

### Archivo `persistence.xml`

``` xml
<persistence xmlns="https://jakarta.ee/xml/ns/persistence" version="3.0">
  <persistence-unit name="miUnidad">
    <class>com.ejemplo.modelo.Cliente</class>
    <properties>
      <property name="jakarta.persistence.jdbc.driver" value="org.h2.Driver"/>
      <property name="jakarta.persistence.jdbc.url" value="jdbc:h2:mem:testdb"/>
      <property name="jakarta.persistence.jdbc.user" value="sa"/>
      <property name="jakarta.persistence.jdbc.password" value=""/>
      <property name="jakarta.persistence.schema-generation.database.action" value="validate"/>
    </properties>
  </persistence-unit>
</persistence>
```

### Código típico

``` java
EntityManagerFactory emf = Persistence.createEntityManagerFactory("miUnidad");
EntityManager em = emf.createEntityManager();

em.getTransaction().begin();

Cliente c = new Cliente();
c.setNombre("Ana");
c.setEmail("ana@ejemplo.com");

em.persist(c);

em.getTransaction().commit();
em.close();
emf.close();
```

### Características

-   Requiere `persistence.xml`.
-   Se gestiona manualmente `EntityManager` y transacciones.
-   Las consultas JPQL se escriben a mano.
-   Alta flexibilidad, pero mucho código "boilerplate".

------------------------------------------------------------------------

## Spring Data JPA (con Spring Boot)

### Configuración `application.properties`

``` properties
spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.driver-class-name=org.h2.Driver
spring.jpa.hibernate.ddl-auto=create
spring.jpa.show-sql=true
```

### Entidad JPA

``` java
@Entity
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String email;
}
```

### Repositorio automático

``` java
@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    List<Cliente> findByNombre(String nombre);
}
```

### Uso en servicio o controlador

``` java
@Service
public class ClienteService {
    @Autowired
    private ClienteRepository repository;

    public Cliente crearCliente(Cliente c) {
        return repository.save(c);
    }

    public List<Cliente> listar() {
        return repository.findAll();
    }
}
```

### Características

-   No necesita `persistence.xml`.
-   Spring crea y gestiona automáticamente el `EntityManager`.
-   Transacciones automáticas con `@Transactional`.
-   Repositorios generados sin implementar consultas.
-   Integración directa con controladores REST (`@RestController`).

------------------------------------------------------------------------

# Ejemplo: comparativa entre persist y save

| Característica                      | `persist()`                    | `save()`                               |
| ----------------------------------- | ------------------------------ | -------------------------------------- |
| ¿De qué API proviene?               | JPA (`EntityManager`)          | Spring Data JPA (`Repository`)         |
| Inserta entidad nueva               | ✅ Sí                           | ✅ Sí (usa `persist()`)                 |
| Actualiza entidad existente         | ❌ No                           | ✅ Sí (usa `merge()`)                   |
| Devuelve la entidad guardada        | ❌ No                           | ✅ Sí                                   |
| Lanza error si la entidad ya existe | ✅ Sí (`EntityExistsException`) | ❌ No, actualiza                        |
| Necesita transacción explícita      | ✅ Sí (o `@Transactional`)      | No necesariamente (Spring la gestiona) |

---

# Las 30 preguntas de entrevista sobre Spring Data JPA más comunes para las que deberías prepararte

https://www.vervecopilot.com/es/preguntas-de-entrevista/las-30-preguntas-de-entrevista-sobre-spring-data-jpa-m%C3%A1s-comunes-para-las-que-deber%C3%ADas-prepararte

¿Qué es Spring Data JPA?

¿Qué es JPA y por qué lo usan los programadores?

¿Cuáles son las interfaces clave en Spring Data JPA?

¿Cómo funciona la generación de consultas por nombre de método en Spring Data JPA?

¿Cuáles son las ventajas de usar JPA?

¿Qué es una Entidad en JPA?

¿Cuál es la diferencia entre EntityManager y Repository en Spring Data JPA?

¿Qué es el contexto de persistencia?

¿Cuál es la diferencia entre save() y saveAndFlush()?

¿Cuál es la diferencia entre JPQL y SQL?

¿Cuáles son los diferentes tipos de relaciones en JPA?

¿Qué es la carga diferida (lazy loading) y la carga inmediata (eager loading)?

¿Cuál es la diferencia entre @Query y los métodos de consulta derivados?

¿Cuál es el uso de la anotación @Modifying?

¿Cómo se gestionan las transacciones en Spring Data JPA?

¿Cuál es el tipo de carga (fetch type) predeterminado para @ManyToOne y @OneToMany?

¿Qué es una clave primaria compuesta y cómo se define?

¿Cuál es la diferencia entre @Entity y @Table?

¿Cuál es el papel de EntityManager en JPA?

¿Cómo se implementan la paginación y la ordenación en Spring Data JPA?

¿Puede Spring Data JPA manejar consultas SQL nativas?

¿Cómo admite Spring Data JPA la auditoría?

¿Qué es la cascada (cascading) en JPA?

¿Cuál es la diferencia entre los métodos persist() y merge() de EntityManager?

¿Qué es el problema de select N+1 en JPA?

¿Cómo se escriben implementaciones personalizadas de repositorios en Spring Data JPA?

¿Qué es el bloqueo optimista (optimistic locking) en JPA?

¿Qué anotaciones se utilizan para mapear columnas de base de datos en JPA?

¿Cuál es la diferencia entre flush() y commit() en JPA?

¿Cómo se manejan las excepciones en Spring Data JPA?