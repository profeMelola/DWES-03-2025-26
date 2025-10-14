## Anotacons JPA más usadas en CRUD simples

| Anotación                                             | Descripción                                                                             | Ejemplo                                                       |
| ----------------------------------------------------- | --------------------------------------------------------------------------------------- | ------------------------------------------------------------- |
| `@Entity`                                             | Indica que la clase es una entidad persistente y se mapea a una tabla de base de datos. | `@Entity<br>public class Usuario { ... }`             |
| `@Table(name = "usuarios")`                           | Define el nombre de la tabla (opcional si coincide con el nombre de la clase).          | `@Table(name="usuarios")`                             |
| `@Id`                                                 | Marca el campo como clave primaria.                                                     | `@Id private Long id;`                             |
| `@GeneratedValue(strategy = GenerationType.IDENTITY)` | Indica que el ID se genera automáticamente por la base de datos.                        | `@GeneratedValue(strategy = GenerationType.IDENTITY)` |
| `@Column(name = "nombre", nullable = false)`          | Configura la columna (nombre, si acepta nulos, longitud...).                            | `@Column(nullable=false)`                             |
| `@ManyToOne` / `@OneToMany`                           | Define relaciones entre entidades (foráneas o listas).                                  | `@ManyToOne<br>@JoinColumn(name="rol_id")`            |
| `@JoinColumn(name = "rol_id")`                        | Especifica la columna que actúa como clave foránea.                                     | `@JoinColumn(name="rol_id")`                          |

### Estrategias posibles de GenerationType

| Estrategia | Descripción                                                                                                                                | Cuándo usarla                                                                                              |
| ---------- | ------------------------------------------------------------------------------------------------------------------------------------------ | ---------------------------------------------------------------------------------------------------------- |
| `IDENTITY` | Delega la generación del ID a la **base de datos** (usa columnas auto–incrementales). No permite obtener el ID hasta después del `INSERT`. | ✅ Muy usada en MySQL, PostgreSQL, SQL Server, H2.                                                          |
| `SEQUENCE` | Usa una **secuencia** de base de datos para generar IDs. JPA obtiene el valor antes del `INSERT`.                                          | ✅ Ideal para Oracle, PostgreSQL (con secuencias). Requiere definir una secuencia con `@SequenceGenerator`. |
| `TABLE`    | Usa una **tabla auxiliar** que guarda los últimos valores de ID generados. Es portable pero más lenta.                                     | ⚙️ Opción genérica cuando no hay soporte nativo de secuencias ni autoincremento.                           |
| `AUTO`     | Deja que JPA elija automáticamente la estrategia más adecuada según la base de datos.                                                      | 🔄 Opción por defecto, práctica para desarrollo inicial. Puede variar al cambiar de BD.                    |


## Anotaciones principales de JPA

| Anotación                                                 | Descripción                                                                                  | Ejemplo                                                       |
| --------------------------------------------------------- | -------------------------------------------------------------------------------------------- | ------------------------------------------------------------- |
| `@Entity`                                                 | Marca una clase Java como una entidad persistente (se mapea a una tabla de base de datos).   | `@Entity<br>public class Usuario { ... }`             |
| `@Table(name = "usuarios")`                               | Define el nombre de la tabla asociada a la entidad. Si se omite, toma el nombre de la clase. | `@Table(name="usuarios")`                             |
| `@Id`                                                     | Indica el campo que es clave primaria.                                                       | `@Id<br>private Long id;`                             |
| `@GeneratedValue(strategy = GenerationType.IDENTITY)`     | Define cómo se genera el valor de la clave primaria (IDENTITY, SEQUENCE, AUTO...).           | `@GeneratedValue(strategy = GenerationType.IDENTITY)` |
| `@Column(name = "nombre", nullable = false, length = 50)` | Configura detalles de la columna (nombre, si acepta nulos, longitud, etc.).                  | `@Column(nullable=false, length=50)`                  |
| `@Transient`                                              | Excluye un campo del mapeo a base de datos (no se guarda).                                   | `@Transient<br>private int edadTemporal;`             |
| `@Lob`                                                    | Indica que el campo se almacena como un tipo grande (BLOB o CLOB).                           | `@Lob<br>private String descripcion;`                 |
| `@Enumerated(EnumType.STRING)`                            | Indica cómo se persiste un `enum` (por nombre o por ordinal).                                | `@Enumerated(EnumType.STRING)`                        |
| `@Temporal(TemporalType.DATE)`                            | Indica el tipo de fecha (DATE, TIME, TIMESTAMP).                                             | `@Temporal(TemporalType.TIMESTAMP)`                   |
| `@ManyToOne`                                              | Relación muchos-a-uno (varios registros apuntan a uno).                                      | `@ManyToOne<br>@JoinColumn(name="rol_id")`            |
| `@OneToMany(mappedBy = "rol")`                            | Relación uno-a-muchos (una entidad tiene una lista de otras).                                | `@OneToMany(mappedBy="rol")`                          |
| `@OneToOne`                                               | Relación uno-a-uno.                                                                          | `@OneToOne<br>@JoinColumn(name="perfil_id")`          |
| `@ManyToMany`                                             | Relación muchos-a-muchos.                                                                    | `@ManyToMany<br>@JoinTable(name="usuarios_roles")`    |
| `@JoinColumn(name = "otra_tabla_id")`                     | Define la columna que actúa como clave foránea.                                              | `@JoinColumn(name="rol_id")`                          |
| `@JoinTable`                                              | Define la tabla intermedia en una relación muchos-a-muchos.                                  | `@JoinTable(name="usuarios_roles")`                   |
| `@Embeddable`                                             | Indica una clase que puede ser embebida en otra entidad.                                     | `@Embeddable<br>public class Direccion { ... }`       |
| `@Embedded`                                               | Inserta los campos de una clase embebida dentro de una entidad.                              | `@Embedded<br>private Direccion direccion;`           |
| `@Version`                                                | Campo usado para control de versiones (optimistic locking).                                  | `@Version<br>private int version;`                    |

## Anotaciones principales de Spring Data JPA

| Anotación                                                  | Descripción                                                                                                                                       | Ejemplo                                                                                                                             |
| ---------------------------------------------------------- | ------------------------------------------------------------------------------------------------------------------------------------------------- | ----------------------------------------------------------------------------------------------------------------------------------- |
| `@Repository`                                              | Marca una clase o interfaz como componente de acceso a datos. Permite el manejo automático de excepciones de persistencia.                        | `@Repository<br>public interface UsuarioRepository extends JpaRepository<Usuario, Long> { }`                                |
| `@Query("SELECT u FROM Usuario u WHERE u.email = :email")` | Permite definir consultas JPQL personalizadas directamente sobre el método.                                                                       | `@Query("SELECT u FROM Usuario u WHERE u.email = :email")<br>Optional<Usuario> findByEmail(@Param("email") String email);`  |
| `@Param("nombre")`                                         | Asocia parámetros nombrados de una consulta con los argumentos del método.                                                                        | `@Query("SELECT u FROM Usuario u WHERE u.nombre = :nombre")<br>List<Usuario> findByNombre(@Param("nombre") String nombre);` |
| `@Modifying`                                               | Indica que el método realiza una operación de actualización o eliminación (no una consulta SELECT).                                               | `@Modifying<br>@Query("DELETE FROM Usuario u WHERE u.activo = false")<br>void eliminarInactivos();`                         |
| `@Transactional`                                           | Controla las transacciones en métodos del repositorio o servicio. Puede indicar si son de solo lectura o si deben hacer rollback.                 | `@Transactional(readOnly = true)`                                                                                           |
| `@EnableJpaRepositories`                                   | Habilita el escaneo y la creación automática de repositorios JPA en el paquete indicado (suele ponerse en la clase principal o de configuración). | `@EnableJpaRepositories(basePackages = "com.ejemplo.repositorios")`                                                         |


