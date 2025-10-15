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


