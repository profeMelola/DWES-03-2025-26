# API REST

![alt text](image.png)

Una API REST (Representational State Transfer) es una interfaz que permite la comunicación entre sistemas mediante el protocolo HTTP.

Importancia:

- Escalabilidad: Las APIs REST son fácilmente escalables debido a su naturaleza estateless (sin estado).
- Flexibilidad: Permiten cualquier formato de datos (JSON, XML, etc.).
- Simplicidad: Son fáciles de usar y entender, lo que acelera el desarrollo y mantenimiento.

## Reglas básicas para diseñar una API RESTful

| Regla | Qué asegura            |
|-------|------------------------|
| 1. Métodos HTTP correctos     | Intención clara        |
| 2. Códigos de estado adecuados| Comunicación estándar  |
| 3. URLs por recursos          | Diseño semántico       |
| 4. JSON como representación   | Uniformidad            |
| 5. Stateless                  | Escalabilidad          |
| 6. HATEOAS                    | Descubribilidad        |
| 7. ErrorDTO                   | Consistencia           |
| 8. Versionado                 | Evolución segura       |
| 9. Paginación y filtrado      | Eficiencia             |
| 10. Documentación             | Mantenibilidad         |
---

### 1. Usa correctamente el protocolo HTTP

REST se apoya completamente en HTTP, usando sus métodos y códigos de estado para expresar intención y resultado.

| Operación          | Método HTTP | Descripción                       | Código típico        |
|--------------------|-------------|-----------------------------------|----------------------|
| Obtener recursos   | GET         | Devuelve información              | 200 OK               |
| Crear recurso      | POST        | Envía datos al servidor           | 201 Created          |
| Actualizar total   | PUT         | Reemplaza un recurso existente    | 200 OK o 204 No Content |
| Actualizar parcial | PATCH       | Modifica algunos campos           | 200 OK o 204 No Content |
| Eliminar recurso   | DELETE      | Elimina el recurso                | 204 No Content       |

> Evita usar POST para todo. Cada método HTTP tiene una semántica específica que ayuda a clientes, proxys y herramientas a entender la operación.

---

### 2. Usa códigos de estado HTTP significativos

Tu API debe comunicar claramente el resultado de cada operación:

| Código | Significado                 | Ejemplo                    |
|--------|-----------------------------|----------------------------|
| 200 OK | Todo correcto               | GET o PUT exitoso          |
| 201 Created | Recurso creado         | POST exitoso               |
| 204 No Content | Acción correcta sin cuerpo | DELETE exitoso     |
| 400 Bad Request | Petición inválida  | Error de validación        |
| 401 Unauthorized | No autenticado    | Token inválido o ausente   |
| 403 Forbidden | Sin permisos         | Usuario sin autorización   |
| 404 Not Found | Recurso no existe    | ID inexistente             |
| 409 Conflict | Conflicto de estado   | Duplicado o inconsistencia |
| 500 Internal Server Error | Error del servidor | Excepción inesperada |

---

### 3. Diseña URLs basadas en recursos (no en acciones)

Las rutas deben representar sustantivos (recursos), no verbos (acciones).

| Incorrecto            | Correcto                  |
|-----------------------|---------------------------|
| /getCustomerById      | /customers/{id}           |
| /createCustomer       | /customers (POST)         |
| /updateCustomer       | /customers/{id} (PUT)     |
| /deleteCustomer       | /customers/{id} (DELETE)  |

REST se basa en recursos (clientes, productos, pedidos…), no en funciones.

---

### 4. Usa representaciones adecuadas (JSON, XML…)

El cliente y el servidor intercambian representaciones del recurso, normalmente en JSON.

Ejemplo:

```json
{
  "id": 5,
  "firstName": "Laura",
  "lastName": "Sánchez"
}
```

---

### 5. Mantén tu servidor sin estado (Stateless)

Cada petición HTTP debe incluir toda la información necesaria para procesarla.
El servidor no debe recordar el estado del cliente entre peticiones.

Correcto:
- Enviar siempre el token JWT o de sesión en cada solicitud (Authorization: Bearer <token>)

Incorrecto:
- El servidor recuerda "quién eres" sin recibir ningún identificador.

---

### 6. Usa relaciones mediante hipervínculos (HATEOAS)

Un API REST completo incluye el principio HATEOAS (Hypermedia As The Engine Of Application State):
el cliente puede descubrir nuevas operaciones a través de los enlaces del propio recurso.

Ejemplo:

```json
{
  "id": 5,
  "firstName": "Laura",
  "lastName": "Sánchez",
  "links": [
    { "rel": "self", "href": "/api/customers/5" },
    { "rel": "orders", "href": "/api/customers/5/orders" }
  ]
}
```

En Spring Boot se puede implementar con el módulo spring-hateoas.

---

### 7. Define una estructura coherente de errores

No devuelvas mensajes arbitrarios. Usa un objeto estándar, por ejemplo un ErrorDTO:

```json
{
  "code": 404,
  "message": "Cliente no encontrado",
  "timestamp": "2025-10-16T12:34:56Z"
}
```

Esto hace que la API sea más predecible y fácil de consumir desde el frontend.

---

### 8. Versiona tu API

Evita romper a los clientes existentes cuando tu API evolucione.
Versiona desde el principio:

Opciones comunes:

- En la URL:
  /api/v1/customers
- O en la cabecera HTTP:
  Accept: application/vnd.myapi.v1+json

---

### 9. Soporta paginación, filtrado y ordenación

Para colecciones grandes de recursos, ofrece parámetros estándar:

GET /customers?page=2&size=20&sort=lastName,asc

Ejemplo de respuesta:

```json
{
  "content": [ ],
  "page": 2,
  "size": 20,
  "totalElements": 134
}
```

---

### 10. Documenta tu API

Usa Swagger / OpenAPI para generar documentación interactiva.

Dependencia para Spring Boot:

```xml
<dependency>
  <groupId>org.springdoc</groupId>
  <artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>
  <version>2.0.4</version>
</dependency>
```

Accede a la documentación en:
http://localhost:8080/swagger-ui.html

---

# MVC: web monolítica vs API Rest

| Aspecto           | Web MVC monolítica           | API REST                                                    |
| ----------------- | ---------------------------- | ----------------------------------------------------------- |
| Tipo de respuesta | HTML renderizado             | JSON / XML                                                  |
| Vista             | Plantillas (Thymeleaf, JSP)  | No hay (la vista la maneja el cliente, ej. React, Angular…) |
| Controlador       | `@Controller`                | `@RestController`                                           |
| Comunicación      | Navegador ↔️ Servidor (HTML) | Front-end / cliente ↔️ API (JSON)                           |
| Patrón            | MVC completo                 | MVC sin “V” (a veces llamado **Model–Controller**)          |

---

# JWT

https://www.jwt.io/

![alt text](image-3.png)

WT (JSON Web Token) es un estándar para la creación de tokens de acceso. Se utiliza principalmente para autenticación y autorización.

Funcionamiento:

- Autenticación: El usuario se autentica ingresando sus credenciales.
- Generación: El servidor genera un JWT y lo devuelve al cliente.
- Autorización: El cliente envía el JWT en las solicitudes subsecuentes para acceder a recursos protegidos.
- Verificación: El servidor verifica el JWT antes de conceder acceso.

## Necesidad en HTTP

- Seguridad: JWT asegura que solo las solicitudes autenticadas puedan acceder a recursos protegidos.
- Eficiencia: Reduce la sobrecarga del servidor ya que no requiere almacenar sesiones en el servidor.
- Escalabilidad: Facilita la expansión del sistema sin preocuparse de la gestión de sesiones.

![alt text](image-1.png)

## Partes

Un JWT (JSON Web Token) está compuesto por tres partes principales, y cada una está separada por un punto (.):

1. Header (Encabezado)
2. Payload (Carga Útil)
3. Signature (Firma)

![alt text](image-2.png)

### Estructura de un JWT

Un JWT completo podría verse algo así:

```
eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c
```

### 1. Header (Encabezado)

El encabezado típicamente consiste en dos partes: el tipo de token (que es JWT) y el algoritmo de firma utilizado (por ejemplo, HMAC SHA256 o RSA).


```
{
  "alg": "HS256",
  "typ": "JWT"
}
```

Este JSON es luego codificado en Base64Url para formar la primera parte del JWT.

### 2. Payload (Carga Útil)

El payload contiene las reclamaciones (claims). Las reclamaciones son declaraciones sobre una entidad (normalmente, el usuario) y datos adicionales. Hay tres tipos de reclamos:

- **Restringidos (Registered Claims):** Un conjunto de claims predeterminadas que no son obligatorias pero se recomiendan, como iss (emisor), exp (expiración), sub (sujeto), y aud (audiencia).
- **Públicos (Public Claims):** Para información personalizada y debe evitar colisiones de nombre (name collision) utilizando URLs o URNs como nombres.
- **Privados (Private Claims):** Para compartir información entre partes que han acordado utilizar esos claims y no están registrados.

```
{
  "sub": "1234567890",
  "name": "John Doe",
  "admin": true
}
```

Este JSON también es codificado en Base64Url para formar la segunda parte del JWT.

### 3. Signature (Firma)

Para crear la firma, se toma el header y el payload, se codifican en Base64Url, y se unen con un punto (.). Luego, esta cadena se firma utilizando el algoritmo especificado en el Header y una clave secreta.

```
HMACSHA256(
  base64UrlEncode(header) + "." + base64UrlEncode(payload),
  secret)
```