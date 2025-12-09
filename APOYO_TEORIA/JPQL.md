# JPQL — Conceptos Fundamentales

JPQL (*Java Persistence Query Language*) es el lenguaje de consultas orientado a objetos de JPA/Hibernate.  

Permite consultar entidades **sin depender del motor de base de datos**, y operar sobre **objetos Java**, no sobre tablas.


| Concepto      | SQL           | JPQL                     |
|--------------|---------------|--------------------------|
| Trabaja con… | tablas        | entidades                |
| Selecciona…  | columnas      | objetos                  |
| Joins        | manuales      | por relaciones           |
| Portabilidad | baja          | alta                     |
| Objetivo     | consultar BD  | consultar el modelo OO   |


---

## 1. ¿Qué es JPQL?

JPQL es un lenguaje similar a SQL, pero:

- Trabaja con **entidades**, no tablas.
- Selecciona **objetos**, no columnas.
- Usa **atributos** Java, no nombres de columnas SQL.
- Permite navegar relaciones (`o.user`, `r.dishes`) directamente.

**Ejemplo JPQL:**

```jpql
SELECT r FROM Restaurant r WHERE r.name = 'Burger Planet'
```

Esto devuelve un **Restaurant**, no un conjunto de filas.

---

## 2. ¿Por qué JPQL?

JPQL permite:

- Consultas independientes de la base de datos (MySQL, PostgreSQL, H2…)
- Trabajar con entidades y relaciones del modelo
- Consultas complejas sin usar SQL nativo
- Construir DTOs directamente desde la consulta

Su objetivo es actuar sobre el **modelo orientado a objetos**, no sobre la estructura física.

---

## 3. Estructura básica de una consulta

```text
SELECT <entidad o campo>
FROM <Entidad> alias
[JOIN ...]
[WHERE ...]
[ORDER BY ...]
```

Ejemplo:

```jpql
SELECT d FROM Dish d WHERE d.price > 10
```

---

## 4. Parámetros en JPQL

```jpql
SELECT d FROM Dish d WHERE d.category = :cat
```

```java
@Query("""
    SELECT d FROM Dish d WHERE d.category = :cat
""")
List<Dish> findByCategory(@Param("cat") String category);
```

---

## 5. Joins en JPQL

JPQL utiliza **relaciones de entidades**, no claves foráneas.

### JOIN simple

```jpql
SELECT d FROM Dish d JOIN d.restaurant r WHERE r.name = :name
```

### JOIN múltiple

```jpql
SELECT o 
FROM Order o
JOIN o.user u
JOIN o.restaurant r
WHERE u.username = :user
```

---

## 6. JOIN FETCH — Carga de relaciones LAZY

`JOIN FETCH` sirve para **cargar relaciones LAZY durante la consulta**, evitando:

- múltiples consultas posteriores  
- `LazyInitializationException`  
- necesidad de `@Transactional` para hidratar la colección  

Ejemplo:

```jpql
SELECT r FROM Restaurant r
JOIN FETCH r.dishes
WHERE r.id = :id
```

➡ El objeto `Restaurant` se devuelve **con la lista de platos ya cargada**.

---

## 7. Seleccionar campos específicos

JPQL permite seleccionar:

### Atributos individuales

```jpql
SELECT d.name FROM Dish d
```

Resultado: `List<String>`

---

### Múltiples atributos (Object[])

```jpql
SELECT d.name, d.price FROM Dish d
```

Resultado: `List<Object[]>`

---

### DTO con constructor

```jpql
SELECT new com.foodexpress.dto.DishSummary(d.name, d.price)
FROM Dish d
```

➡ El DTO debe tener un constructor compatible.

---

## 8. Funciones y agregaciones

Funciones típicas:

- `COUNT()`
- `SUM()`
- `AVG()`
- `MIN()`
- `MAX()`

Ejemplo:

```jpql
SELECT SUM(od.subtotal)
FROM OrderDetail od
JOIN od.order o
WHERE o.user.id = :id
```

---

## 9. Agrupaciones

```jpql
SELECT r.name, COUNT(o)
FROM Order o
JOIN o.restaurant r
GROUP BY r.name
HAVING COUNT(o) > 5
```

---

## 10. Cuándo usar JPQL en Spring (`@Query`)

Usa `@Query` cuando:

- Necesitas **joins** complejos
- Necesitas **JOIN FETCH** para cargar relaciones LAZY
- Quieres devolver **DTOs**
- Quieres **agregaciones** o **GROUP BY**
- Quieres filtros múltiples combinados

No es necesario para:

- `findByName`, `findByPriceLessThan`, etc.  
  → los métodos por convención son suficientes.

---

## 11. Qué NO es JPQL

JPQL **NO**:

- Usa nombres de tablas SQL (`restaurants`)
- Permite `SELECT *`
- Usa columnas (`restaurant_id`)
- Acepta funciones específicas de MySQL
- Devuelve filas → siempre devuelve entidades o datos mapeados

Para SQL nativo:

```java
@Query(value = "SELECT * FROM restaurants", nativeQuery = true)
```

---


## Ejemplos básicos

### Consulta simple

```jpql
SELECT d FROM Dish d WHERE d.price < 10
```

### Join entre entidades

```jpql
SELECT o FROM Order o JOIN o.user u WHERE u.username = :name
```

### Join fetch para cargar relaciones

```jpql
SELECT r FROM Restaurant r JOIN FETCH r.dishes WHERE r.id = :id
```
