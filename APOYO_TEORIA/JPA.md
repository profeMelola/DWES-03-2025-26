# JPA

## Repositorios

<img width="510" height="453" alt="imagen" src="https://github.com/user-attachments/assets/871e55da-9437-4393-bb74-4e0053a7ba7e" />

```
Repository<T, ID>
 └── CrudRepository<T, ID>
      └── PagingAndSortingRepository<T, ID>
           └── JpaRepository<T, ID>

```

<img width="749" height="229" alt="imagen" src="https://github.com/user-attachments/assets/f3cc8cf7-3a4f-4a73-aeb4-ea4ab40dc2b0" />

## JpaRepository

JpaRepository es otra interfaz proporcionada por Spring Data que extiende CrudRepository y PagingAndSortingRepository (que proporciona métodos para paginar y ordenar registros). Por lo tanto, JpaRepository hereda todos los métodos de estas dos interfaces.

JpaRepository también proporciona algunas funcionalidades adicionales específicas de JPA, como:

- **flush():** Aplica todas las operaciones pendientes a la base de datos.
- **deleteAllInBatch(Iterable<T> entities):** Borra las entidades en un lote, lo que es más eficiente que borrar una por una.
- **&lt;S extends T&gt; List&lt;S&gt; findAll(Example&lt;S&gt; example)**: Soporte para consultas de ejemplo con la API Query By Example.
[API Query by Example QBE](findAll_Example_QBE)

Aquí hay un ejemplo de cómo se puede crear un repositorio UserRepository que extiende JpaRepository:

```
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}
```

De igual manera que con CrudRepository, una vez que se ha creado este repositorio, se puede inyectar en cualquier clase y usarlo para realizar operaciones CRUD, además de las operaciones adicionales proporcionadas por JpaRepository, en la entidad User.

Es importante mencionar que, si bien JpaRepository proporciona más funcionalidad que CrudRepository, no todas las aplicaciones necesitarán esta funcionalidad adicional. Por lo tanto, el desarrollador debe considerar cuidadosamente qué interfaz es la más adecuada para sus necesidades específicas.

## Operaciones derivadas del nombre del método

Spring Data JPA tiene una característica poderosa que permite crear consultas simplemente definiendo una interfaz de método en el repositorio. Este enfoque es mejor conocido como **"operaciones derivadas del nombre del método"**.

En este sistema, el nombre de los métodos de la interfaz del repositorio sigue una convención que Spring Data interpreta y traduce en la consulta SQL correspondiente. 

Por ejemplo, si tu entidad tiene un campo llamado email y quieres buscar una entidad basándote en ese campo, puedes añadir el método findByEmail(String email) a tu repositorio. No necesitas proporcionar ninguna implementación para este método. En tiempo de ejecución, Spring Data generará una implementación adecuada.

Aquí hay un ejemplo con una entidad User y un repositorio UserRepository:

```
import org.springframework.data.repository.CrudRepository;
import java.util.List;

public interface UserRepository extends CrudRepository<User, Long> {
    List<User> findByEmail(String email);
}
```

Cuando este código se ejecuta, Spring Data JPA creará una implementación del método findByEmail que ejecuta una consulta SQL similar a SELECT * FROM user WHERE email = ?.

La convención de nomenclatura soporta varias palabras clave que pueden ayudar a construir consultas más complejas, como And, Or, Between, LessThan, GreaterThan, Like, OrderBy, y muchas más. Por ejemplo:

```
import org.springframework.data.repository.CrudRepository;
import java.util.List;

public interface UserRepository extends CrudRepository<User, Long> {
    List<User> findByEmailAndFirstName(String email, String firstName);
    List<User> findByAgeBetween(int startAge, int endAge);
    List<User> findByLastNameLike(String lastName);
    List<User> findByAgeGreaterThan(int age);
    List<User> findByFirstNameOrderByLastNameAsc(String firstName);
}
```

Estos métodos se traducirán en las siguientes consultas SQL:

- SELECT * FROM user WHERE email = ? AND firstName = ?
- SELECT * FROM user WHERE age BETWEEN ? AND ?
- SELECT * FROM user WHERE lastName LIKE ?
- SELECT * FROM user WHERE age > ?
- SELECT * FROM user WHERE firstName = ? ORDER BY lastName ASC

Es importante recordar que, aunque las operaciones derivadas del nombre del método pueden simplificar la implementación de las operaciones de consulta comunes, también pueden llevar a nombres de métodos largos y difíciles de leer para consultas más complejas. En tales casos, puede ser preferible utilizar la **anotación @Query** para definir la consulta directamente.

## Consultas personalizadas

| Caso                                       | Recomendación                                                        |
| ------------------------------------------ | -------------------------------------------------------------------- |
| Consultas simples basadas en propiedades   | Usa **nombres de método** (`findBy...`, `existsBy...`, `countBy...`) |
| Consultas complejas o SQL nativo           | Usa **`@Query`**                                                     |
| Consultas dinámicas con filtros opcionales | Considera **`Query by Example` (QBE)** o **`Specification`**         |


Si bien las operaciones derivadas del nombre del método son poderosas y cubren una gran cantidad de casos de uso comunes, hay momentos en que se necesita un mayor control sobre la consulta de la base de datos. Para estos casos, Spring Data JPA proporciona una forma de especificar consultas personalizadas a través de la anotación @Query.

La anotación @Query se puede utilizar para especificar una consulta JPQL (Java Persistence Query Language) que será ejecutada. La consulta puede hacer referencia a los parámetros del método utilizando un índice basado en 1 precedido por un signo de interrogación.

Por ejemplo, si se quiere buscar usuarios por su apellido, pero solo se quiere retornar aquellos usuarios que tengan un correo electrónico que termine con un cierto dominio, se podría utilizar la anotación @Query para definir esta consulta personalizada en la interfaz del repositorio:

```
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    @Query("SELECT u FROM User u WHERE u.lastName = ?1 AND u.email LIKE %?2")
    List<User> findByLastNameAndEmailDomain(String lastName, String emailDomain);
}

```

En este caso, ?1 se refiere al primer parámetro del método (lastName) y %?2 se refiere al segundo parámetro (emailDomain), con un símbolo de porcentaje (%) añadido para la operación LIKE.

La anotación @Query también puede ser utilizada para ejecutar consultas nativas SQL, simplemente pasando el atributo nativeQuery = true a la anotación. Por ejemplo:

```
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    @Query(value = "SELECT * FROM Users u WHERE u.last_name = ?1 AND u.email LIKE %?2", nativeQuery = true)
    List<User> findByLastNameAndEmailDomain(String lastName, String emailDomain);
}
```

Aquí se está ejecutando una consulta SQL nativa en lugar de una consulta JPQL. Por favor, ten en cuenta que las consultas nativas son específicas de la base de datos y no son portables entre diferentes tipos de bases de datos.

Las consultas personalizadas proporcionan un gran nivel de control sobre las operaciones de la base de datos y permiten al desarrollador escribir consultas complejas que no pueden ser fácilmente expresadas a través de las operaciones derivadas del nombre del método.


*Fuente: https://certidevs.com/tutorial-spring-boot-repositorios-crud*

---

## Configuración de H2

### spring.jpa.hibernate.ddl-auto — Opciones y comportamiento

| Valor         | Qué hace Hibernate al iniciar                                   | Qué hace al cerrar la app                   | Ideal para                      | Efecto en H2 **en memoria**                      | Efecto en H2 **persistente (en archivo)** |
| :------------ | :-------------------------------------------------------------- | :------------------------------------------ | :------------------------------ | :----------------------------------------------- | :---------------------------------------- |
| `none`        | No crea ni modifica tablas                                      | No borra nada                               | Producción con BD ya gestionada | ❌ Fallará (no existen tablas)                    | ✅ Usa las tablas existentes               |
| `validate`    | Verifica que las tablas coincidan con las entidades             | No borra nada                               | Producción o integración        | ❌ Fallará (no existen tablas)                    | ✅ Verifica coherencia, no cambia nada     |
| `update`      | Crea tablas si faltan o actualiza estructura (sin borrar datos) | No borra nada                               | Desarrollo con BD persistente   | ✅ Crea tablas nuevas en cada inicio (BD vacía)   | ✅ Mantiene datos entre reinicios          |
| `create`      | Borra el esquema anterior y crea tablas nuevas                  | No borra nada                               | Desarrollo y pruebas manuales   | ✅ Crea todo en cada inicio (se pierde al cerrar) | ✅ Deja las tablas al reiniciar            |
| `create-drop` | Igual que `create` (borra y crea al inicio)                     | **Borra todas las tablas al cerrar la app** | Tests o demos temporales        | ✅ Igual que `create` (H2 se borra al cerrar)     | 🧨 Borra todo al apagar la app            |


### Ejemplo completo de application.properties

```
spring.application.name=spring-boot-tutorial2

# Esto crea el archivo data/demo.mv.db en tu proyecto.
#spring.datasource.url=jdbc:h2:file:./data/demo

# ------------------------------------------------------------------------
# URL de base de datos en memoria
#spring.datasource.url=jdbc:h2:mem:testdb
#spring.datasource.driverClassName=org.h2.Driver
#spring.datasource.username=sa
#spring.datasource.password=

# ------------------------------------------------------------------------
## Mostrar las sentencias SQL en consola
#spring.jpa.show-sql=true

## Activar consola web de H2
#spring.h2.console.enabled=true
#spring.h2.console.path=/h2-console

# ------------------------------------------------------------------------
## Dialecto de Hibernate
# El lenguaje SQL es estándar, pero cada base de datos tiene sus propias variantes.
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect

# ------------------------------------------------------------------------
# Esta propiedad le indica a Hibernate (el ORM que usa Spring Data JPA)
# qué debe hacer con el esquema de base de datos (tablas, relaciones, etc.) cada vez que arranca la aplicación.
# Crea las tablas según las entidades
spring.jpa.hibernate.ddl-auto=create-drop

# ------------------------------------------------------------------------
# Ejecuta scripts schema.sql y data.sql (por defecto: solo en BD embebidas)
# garantiza que siempre se ejecuten los scripts, aunque la base de datos no sea embebida
spring.sql.init.mode=ALWAYS
spring.sql.init.encoding=UTF-8

# ------------------------------------------------------------------------
# Si quisieras personalizar el nombre o ubicación

# schema.sql: antes de crear el esquema (crear tablas, índices...)
# spring.sql.init.schema-locations=classpath:/db/schema.sql

# data.sql: después de crear el esquema. Insertar datos iniciales o de prueba
spring.sql.init.data-locations=classpath:/db/data.sql

# Espera a que Hibernate cree las tablas, y solo después ejecuta los scripts SQL
spring.jpa.defer-datasource-initialization=true
```

___

## Anotaciones JPA más usadas en CRUD simples

| Anotación                                             | Descripción                                                                             | Ejemplo                                                       |
| ----------------------------------------------------- | --------------------------------------------------------------------------------------- | ------------------------------------------------------------- |
| `@Entity`                                             | Indica que la clase es una entidad persistente y se mapea a una tabla de base de datos. | `@Entity public class Usuario { ... }`             |
| `@Table(name = "usuarios")`                           | Define el nombre de la tabla (opcional si coincide con el nombre de la clase).          | `@Table(name="usuarios")`                             |
| `@Id`                                                 | Marca el campo como clave primaria.                                                     | `@Id private Long id;`                             |
| `@GeneratedValue(strategy = GenerationType.IDENTITY)` | Indica que el ID se genera automáticamente por la base de datos.                        | `@GeneratedValue(strategy = GenerationType.IDENTITY)` |
| `@Column(name = "nombre", nullable = false)`          | Configura la columna (nombre, si acepta nulos, longitud...).                            | `@Column(nullable=false)`                             |
| `@ManyToOne` / `@OneToMany`                           | Define relaciones entre entidades (foráneas o listas).                                  | `@ManyToOne @JoinColumn(name="rol_id")`            |
| `@JoinColumn(name = "rol_id")`                        | Especifica la columna que actúa como clave foránea.                                     | `@JoinColumn(name="rol_id")`                          |

| Escenario                                             | Recomendado         |
| ----------------------------------------------------- | ------------------- |
| Desarrollo con **H2 en memoria**                      | `create-drop`       |
| Desarrollo con **H2 persistente** (archivo) o BD real | `update`            |
| Producción                                            | `validate` o `none` |
| Tests automatizados                                   | `create-drop`       |

___

### Estrategias posibles de GenerationType

| Estrategia | Descripción                                                                                                                                | Cuándo usarla                                                                                              |
| ---------- | ------------------------------------------------------------------------------------------------------------------------------------------ | ---------------------------------------------------------------------------------------------------------- |
| `IDENTITY` | Delega la generación del ID a la **base de datos** (usa columnas auto–incrementales). No permite obtener el ID hasta después del `INSERT`. | ✅ Muy usada en MySQL, PostgreSQL, SQL Server, H2.                                                          |
| `SEQUENCE` | Usa una **secuencia** de base de datos para generar IDs. JPA obtiene el valor antes del `INSERT`.                                          | ✅ Ideal para Oracle, PostgreSQL (con secuencias). Requiere definir una secuencia con `@SequenceGenerator`. |
| `TABLE`    | Usa una **tabla auxiliar** que guarda los últimos valores de ID generados. Es portable pero más lenta.                                     | ⚙️ Opción genérica cuando no hay soporte nativo de secuencias ni autoincremento.                           |
| `AUTO`     | Deja que JPA elija automáticamente la estrategia más adecuada según la base de datos.                                                      | 🔄 Opción por defecto, práctica para desarrollo inicial. Puede variar al cambiar de BD.                    |

___

## Anotaciones principales de JPA

| Anotación                                                 | Descripción                                                                                  | Ejemplo                                                       |
| --------------------------------------------------------- | -------------------------------------------------------------------------------------------- | ------------------------------------------------------------- |
| `@Entity`                                                 | Marca una clase Java como una entidad persistente (se mapea a una tabla de base de datos).   | `@Entity public class Usuario { ... }`             |
| `@Table(name = "usuarios")`                               | Define el nombre de la tabla asociada a la entidad. Si se omite, toma el nombre de la clase. | `@Table(name="usuarios")`                             |
| `@Id`                                                     | Indica el campo que es clave primaria.                                                       | `@Id private Long id;`                             |
| `@GeneratedValue(strategy = GenerationType.IDENTITY)`     | Define cómo se genera el valor de la clave primaria (IDENTITY, SEQUENCE, AUTO...).           | `@GeneratedValue(strategy = GenerationType.IDENTITY)` |
| `@Column(name = "nombre", nullable = false, length = 50)` | Configura detalles de la columna (nombre, si acepta nulos, longitud, etc.).                  | `@Column(nullable=false, length=50)`                  |
| `@Transient`                                              | Excluye un campo del mapeo a base de datos (no se guarda).                                   | `@Transient private int edadTemporal;`             |
| `@Lob`                                                    | Indica que el campo se almacena como un tipo grande (BLOB o CLOB).                           | `@Lob private String descripcion;`                 |
| `@Enumerated(EnumType.STRING)`                            | Indica cómo se persiste un `enum` (por nombre o por ordinal).                                | `@Enumerated(EnumType.STRING)`                        |
| `@Temporal(TemporalType.DATE)`                            | Indica el tipo de fecha (DATE, TIME, TIMESTAMP).                                             | `@Temporal(TemporalType.TIMESTAMP)`                   |
| `@ManyToOne`                                              | Relación muchos-a-uno (varios registros apuntan a uno).                                      | `@ManyToOne @JoinColumn(name="rol_id")`            |
| `@OneToMany(mappedBy = "rol")`                            | Relación uno-a-muchos (una entidad tiene una lista de otras).                                | `@OneToMany(mappedBy="rol")`                          |
| `@OneToOne`                                               | Relación uno-a-uno.                                                                          | `@OneToOne @JoinColumn(name="perfil_id")`          |
| `@ManyToMany`                                             | Relación muchos-a-muchos.                                                                    | `@ManyToMany @JoinTable(name="usuarios_roles")`    |
| `@JoinColumn(name = "otra_tabla_id")`                     | Define la columna que actúa como clave foránea.                                              | `@JoinColumn(name="rol_id")`                          |
| `@JoinTable`                                              | Define la tabla intermedia en una relación muchos-a-muchos.                                  | `@JoinTable(name="usuarios_roles")`                   |
| `@Embeddable`                                             | Indica una clase que puede ser embebida en otra entidad.                                     | `@Embeddable public class Direccion { ... }`       |
| `@Embedded`                                               | Inserta los campos de una clase embebida dentro de una entidad.                              | `@Embedded private Direccion direccion;`           |
| `@Version`                                                | Campo usado para control de versiones (optimistic locking).                                  | `@Version private int version;`                    |

___

## Anotaciones principales de Spring Data JPA

| Anotación                                                  | Descripción                                                                                                                                       | Ejemplo                                                                                                                             |
| ---------------------------------------------------------- | ------------------------------------------------------------------------------------------------------------------------------------------------- | ----------------------------------------------------------------------------------------------------------------------------------- |
| `@Repository`                                              | Marca una clase o interfaz como componente de acceso a datos. Permite el manejo automático de excepciones de persistencia.                        | `@Repository public interface UsuarioRepository extends JpaRepository<Usuario, Long> { }`                                |
| `@Query("SELECT u FROM Usuario u WHERE u.email = :email")` | Permite definir consultas JPQL personalizadas directamente sobre el método.                                                                       | `@Query("SELECT u FROM Usuario u WHERE u.email = :email") Optional<Usuario> findByEmail(@Param("email") String email);`  |
| `@Param("nombre")`                                         | Asocia parámetros nombrados de una consulta con los argumentos del método.                                                                        | `@Query("SELECT u FROM Usuario u WHERE u.nombre = :nombre") List<Usuario> findByNombre(@Param("nombre") String nombre);` |
| `@Modifying`                                               | Indica que el método realiza una operación de actualización o eliminación (no una consulta SELECT).                                               | `@Modifying @Query("DELETE FROM Usuario u WHERE u.activo = false") void eliminarInactivos();`                         |
| `@Transactional`                                           | Controla las transacciones en métodos del repositorio o servicio. Puede indicar si son de solo lectura o si deben hacer rollback.                 | `@Transactional(readOnly = true)`                                                                                           |
| `@EnableJpaRepositories`                                   | Habilita el escaneo y la creación automática de repositorios JPA en el paquete indicado (suele ponerse en la clase principal o de configuración). | `@EnableJpaRepositories(basePackages = "com.ejemplo.repositorios")`                                                         |


