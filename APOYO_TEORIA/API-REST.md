# API REST

![alt text](image.png)

Una API REST (Representational State Transfer) es una interfaz que permite la comunicación entre sistemas mediante el protocolo HTTP.

Importancia:

- Escalabilidad: Las APIs REST son fácilmente escalables debido a su naturaleza estateless (sin estado).
- Flexibilidad: Permiten cualquier formato de datos (JSON, XML, etc.).
- Simplicidad: Son fáciles de usar y entender, lo que acelera el desarrollo y mantenimiento.

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