# FoodExpress API Rest

Simula una plataforma de pedidos de comida a domicilio tipo Uber Eats /
Glovo.

![alt text](image.png)

Trabajarás con:

- Spring Boot
- Spring Data JPA
- Spring Security + JWT
- H2 persistente en disco
- JPA avanzado
- Paginación

---

## Modelo de datos

| Entidad | Descripción |
|----------|--------------|
| **Usuario** | Cliente, repartidor o administrador del sistema |
| **Rol** | Define permisos (`ADMIN`, `CLIENTE`, `REPARTIDOR`) |
| **Restaurante** | Negocio registrado en la plataforma |
| **Plato** | Producto ofrecido por cada restaurante |
| **Pedido** | Solicitud de comida realizada por un usuario |
| **DetallePedido** | Relación N:M entre pedido y platos (con cantidad y subtotal) |

---

## Configuración de la BD H2 (persistente)

```properties
spring.datasource.url=jdbc:h2:file:./data/foodexpressdb;DB_CLOSE_ON_EXIT=FALSE;AUTO_RECONNECT=TRUE
spring.datasource.username=sa
spring.datasource.password=
spring.datasource.driver-class-name=org.h2.Driver
spring.jpa.hibernate.ddl-auto=validate
spring.jpa.show-sql=true
spring.h2.console.enabled=true
spring.h2.console.path=/h2-console
```

**No se borra** entre ejecuciones.

---

## Endpoints principales


### Autenticación (JWT)

| Método | Endpoint | Descripción |
|--------|-----------|-------------|
| `POST` | `/auth/login` | Inicia sesión y devuelve el token JWT |
| `POST` | `/auth/register` | Registra un nuevo usuario (rol CLIENTE por defecto) |
| `GET` | `/auth/profile` | Devuelve los datos del usuario autenticado |

---

### Usuarios y Roles (solo ADMIN)

| Método | Endpoint | Descripción |
|--------|-----------|-------------|
| `GET` | `/api/usuarios` | Lista paginada de usuarios |
| `GET` | `/api/usuarios/{id}` | Obtiene un usuario por ID |
| `POST` | `/api/usuarios` | Crea un nuevo usuario con rol |
| `PUT` | `/api/usuarios/{id}` | Actualiza usuario existente |
| `DELETE` | `/api/usuarios/{id}` | Elimina un usuario |

---

### Restaurantes

| Método | Endpoint | Descripción |
|--------|-----------|-------------|
| `GET` | `/api/restaurantes` | Lista paginada de restaurantes |
| `GET` | `/api/restaurantes/{id}` | Detalle con platos incluidos |
| `POST` | `/api/restaurantes` | Crear nuevo restaurante *(ADMIN)* |
| `PUT` | `/api/restaurantes/{id}` | Actualizar restaurante *(ADMIN)* |
| `DELETE` | `/api/restaurantes/{id}` | Eliminar restaurante *(ADMIN)* |
| `GET` | `/api/restaurantes/buscar?nombre=...` | Buscar por nombre o zona |

---

### Platos

| Método | Endpoint | Descripción |
|--------|-----------|-------------|
| `GET` | `/api/platos` | Lista paginada de platos |
| `GET` | `/api/platos/{id}` | Obtiene un plato por ID |
| `GET` | `/api/platos/categoria/{categoria}` | Filtra por categoría |
| `GET` | `/api/platos/restaurante/{restauranteId}` | Platos de un restaurante |
| `POST` | `/api/platos` | Crear plato *(ADMIN)* |
| `PUT` | `/api/platos/{id}` | Actualizar plato *(ADMIN)* |
| `DELETE` | `/api/platos/{id}` | Eliminar plato *(ADMIN)* |

---

### Pedidos

| Método | Endpoint | Descripción |
|--------|-----------|-------------|
| `GET` | `/api/pedidos` | Lista de pedidos (propios o todos según rol) |
| `GET` | `/api/pedidos/{id}` | Detalle completo del pedido |
| `POST` | `/api/pedidos` | Crear nuevo pedido *(CLIENTE)* |
| `PUT` | `/api/pedidos/{id}/estado` | Cambiar estado *(REPARTIDOR/ADMIN)* |
| `DELETE` | `/api/pedidos/{id}` | Cancelar pedido *(CLIENTE)* |
| `GET` | `/api/pedidos/usuario/{usuarioId}` | Pedidos por usuario *(ADMIN)* |
| `GET` | `/api/pedidos/fecha?desde=...&hasta=...` | Filtrar por rango de fechas |

---

### Reportes y Estadísticas (solo ADMIN)

| Endpoint | Descripción |
|-----------|-------------|
| `/api/reportes/ventas` | Ventas totales por restaurante |
| `/api/reportes/top-platos` | Platos más vendidos |
| `/api/reportes/clientes-frecuentes` | Clientes con más pedidos |
| `/api/reportes/ingresos-categoria` | Ingresos por categoría de plato |
| `/api/reportes/ticket-medio` | Promedio de gasto por restaurante |
| `/api/reportes/repartidores-top` | Repartidores con más entregas |

---

## Roles y accesos

| Rol | Permisos |
|------|-----------|
| `ADMIN` | CRUD completo + reportes |
| `CLIENTE` | Crear y consultar sus pedidos |
| `REPARTIDOR` | Ver y actualizar pedidos asignados |

---

