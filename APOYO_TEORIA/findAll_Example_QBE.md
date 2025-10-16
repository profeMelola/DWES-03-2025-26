# 🔍 `findAll(Example<S> example)` en Spring Data JPA

## 📘 Descripción general
El método `findAll(Example<S> example)` forma parte de **Spring Data JPA** y se utiliza para realizar consultas dinámicas mediante la **API Query by Example (QBE)**.

---

## 🧩 Firma del método

```java
<S extends T> List<S> findAll(Example<S> example)
```

### Parámetros y significado

| Elemento | Descripción |
|-----------|--------------|
| `<S extends T>` | Indica que `S` es un subtipo de `T`, la entidad gestionada por el repositorio. |
| `List<S>` | Lista de resultados del tipo `S`. |
| `Example<S> example` | Objeto contenedor que define los valores de ejemplo usados para filtrar los resultados. |

---

## 🧠 ¿Qué es Query by Example (QBE)?
La **API Query by Example** permite construir consultas dinámicas a partir de una **entidad de ejemplo** (también llamada *probe*).  
Spring compara los campos **no nulos** de esa entidad con los registros almacenados en la base de datos.

---

## 🧾 Ejemplo básico

### Entidad
```java
@Entity
public class Customer {
    @Id
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
}
```

### Repositorio
```java
public interface CustomerRepository extends JpaRepository<Customer, Long> {}
```

### Uso del método `findAll(Example<S> example)`
```java
Customer probe = new Customer();
probe.setFirstName("John");  // solo filtrará por este campo

Example<Customer> example = Example.of(probe);

List<Customer> result = customerRepository.findAll(example);
```
👉 Esta llamada generará una consulta equivalente a:
```sql
SELECT * FROM customer WHERE first_name = 'John';
```

---

## ⚙️ Personalización con `ExampleMatcher`

Puedes controlar cómo se comparan los campos mediante un `ExampleMatcher`:

```java
ExampleMatcher matcher = ExampleMatcher.matching()
    .withIgnoreCase()
    .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);

Example<Customer> example = Example.of(probe, matcher);

List<Customer> result = customerRepository.findAll(example);
```
🔹 En este caso, se buscarán todos los clientes cuyo `firstName` **contenga** “john” (ignorando mayúsculas/minúsculas).

---

## ✅ Ventajas de Query by Example

- No requiere escribir consultas JPQL ni SQL.
- Ideal para **buscadores y filtros dinámicos**.
- Totalmente integrado en `JpaRepository` y `MongoRepository`.
- Fácil de combinar con paginación y ordenación (`findAll(Example<S> example, Pageable pageable)`).

---

## 📚 Referencias

- [Documentación oficial de Spring Data JPA](https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#query-by-example)
- [Ejemplo práctico en GitHub (Spring Data Examples)](https://github.com/spring-projects/spring-data-examples)
