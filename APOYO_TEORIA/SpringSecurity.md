
# Autenticación y autorización con JWT (Json Web Token) en un proyecto Spring Boot

1. **Dependencias necesarias**.
2. **Creación de un controlador de autenticación** para gestionar la autenticación de usuarios.
3. **Middleware o filtros para autorizar el acceso a los endpoints** según si un usuario está autenticado y autorizado.
4. **Configuración de seguridad** para que los endpoints sean accesibles según las reglas publicadas.

---

## Añadir dependencias al proyecto

Añadimos Spring Security como starter.

Es necesario añadir manualmente la dependencia a **Java JWT**.

```
        <dependency>
            <groupId>io.jsonwebtoken</groupId>
            <artifactId>jjwt-api</artifactId>
            <version>0.12.6</version>
        </dependency>
        <dependency>
            <groupId>io.jsonwebtoken</groupId>
            <artifactId>jjwt-impl</artifactId>
            <version>0.12.6</version>
            <scope>compile</scope>
        </dependency>
        <dependency>
            <groupId>io.jsonwebtoken</groupId>
            <artifactId>jjwt-jackson</artifactId>
            <version>0.12.6</version>
            <scope>compile</scope>
        </dependency>
```
---

## Crear Entidades de Usuario y Rol

**Entidad Role:**

```
@Entity
@Table(name = "roles")
@Getter
@AllArgsConstructor
public class Rol {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;

    @ManyToMany(mappedBy = "roles")
    private Set<Usuario> users;

    public Rol() {
        users = new HashSet<Usuario>();
    }

    public Rol(Long id, String name) {
        this();
        this.id = id;
        this.name = name;

    }

    @Override
    public String toString() {
        return "Rol{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

}


```

**Entidad User:**

En Spring Security, cuando haces login con usuario y contraseña, el framework necesita saber:
- Quién eres → el “username”.
- Qué permisos tienes → los “roles” o “authorities”.

Para ello, Spring Security define una interfaz llamada UserDetails, que describe cómo debe lucir un “usuario autenticable”.

La implementamos directamente en la entidad Usuario, de modo que Spring pueda usar los datos del usuario de la BD.

1. Spring llama a tu implementación de UserDetailsService.loadUserByUsername().
2. Este servicio busca al Usuario en la BD.
3. Spring crea un UserDetails (tu Usuario).
4. Compara la contraseña cifrada con la introducida.
5. Si todo es correcto, crea un Authentication en el contexto con los roles de getAuthorities().

```
@Entity
@Table(name = "users")
@Getter
@Setter
public class Usuario implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    // fetch = FetchType.EAGER indica que los roles se cargan siempre junto con el usuario, lo cual es necesario porque Spring Security los necesita inmediatamente para construir las autoridades.
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Rol> roles;

    public Usuario(){
        roles = new HashSet<>();
    }

    public void addRole(Rol rol) {
        roles.add(rol);
        //rol.getUsers().add(this);
    }

    public void removeRole(Rol rol) {
        roles.remove(rol);
        //rol.getUsers().remove(this);
    }


    // --------------------- 5 MÉTODOS DE LA INTERFACE UserDetails -----------------

    // Devuelve los roles convertidos en objetos GrantedAuthority
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream()
                // si en bd el rol no tiene el prefijo ROLE_
                //.map (rol -> (GrantedAuthority) () -> "ROLE_"+rol.getName())
                .map(rol -> (GrantedAuthority) rol::getName)
                .collect(Collectors.toSet());
    }

    /**
     * Indica si la cuenta del usuario ha expirado.
     * Devuelve true si la cuenta no ha expirado.
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
        // // Devuelve true si la fecha actual es anterior o igual a la de expiración
        //    return !LocalDate.now().isAfter(accountExpirationDate);
    }

    /**
     * Indica si la cuenta del usuario está bloqueada.
     * Devuelve true si la cuenta no está bloqueada.
     */
    @Override
    public boolean isAccountNonLocked() { return true; }

    /**
     * Indica si las credenciales del usuario (contraseña) han expirado.
     * Devuelve true si las credenciales no han expirado.
     */
    @Override
    public boolean isCredentialsNonExpired() { return true; }

    /**
     * Indica si la cuenta del usuario está habilitada.
     * Devuelve true si la cuenta está activa.
     */
    @Override
    public boolean isEnabled() { return true; }

}
```

Spring Security trabaja con un sistema de autenticación basado en **UserDetailsService**, que carga los usuarios desde la base de datos. 

Al **implementar UserDetails**, tu entidad User es compatible con Spring Security y puedes personalizar la lógica de autenticación y autorización.

---

## Cargar datos por defecto

```
-- Crear roles por defecto
INSERT INTO roles (name) VALUES ('ROLE_USER');
INSERT INTO roles (name) VALUES ('ROLE_ADMIN');

-- No vamos a crear usuarios. Se crearán a través de un endpoint

```

---
## Crear Repositorios

Repositorio de usuarios:

```
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(String name);
}

```

---

## Paquete security en nuestro proyecto

![alt text](image-8.png)

### Clase para manejar JWT

Crea un servicio para generar y validar tokens JWT.

**JwtService:**

- Generar tokens JWT con clave secreta.
- Extraer información del token.
- Valida si el token pertenece al usuario y si está expirado.

Añade estas propiedades a application.properties:

```
# SECRET KEY (para producción)
#jwt.secret = tu_clave_super_secreta_123456789012345678901234567890
# especificado en horas
jwt.expiration = 2
```

### Configurar filtros para interceptar solicitudes

Spring Security usa filtros para interceptar solicitudes antes de que lleguen a los controladores. 

Crea un filtro para comprobar si hay un JWT incluido en los encabezados de las solicitudes.

**JwtFilter**

### Configurar seguridad en la aplicación

Añade una clase de configuración para personalizar los permisos de acceso a los endpoints y registrar el filtro.

**SecurityConfig**

--- 

## Crear controlador para autenticación

**AuthController**

### POST /auth/register

Registrar nuevo usuario:

    - Recibe un usuario y contraseña.
    - Registra un nuevo usuario en la base de datos.
    - Devuelve un mensaje de éxito.

### POST /auth/login

Hacer login:

    - Recibe un usuario y contraseña.
    - Autentica al usuario.
    - Genera un JWT y lo devuelve en la respuesta.