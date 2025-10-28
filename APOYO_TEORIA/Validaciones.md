# Validaciones

## Validaciones de Jakarta

- Validar datos de entrada del cliente (lo que viene del JSON o la URL).
- Dar feedback rápido, sin tocar la base de datos.
- Garantizar que la API no procese peticiones inválidas.
- Se ejecutan antes de llegar a la capa de servicio o repositorio.
- No tienen efecto sobre lo que está en la base de datos.

## Validaciones de Hibernate

- Proteger la integridad del dominio y la base de datos.
- Asegurar que incluso si alguien usa otra capa o proceso (servicio batch, script, etc.), no se graben datos inválidos.
- Refuerza la consistencia si tu entidad se usa en varios contextos.
- Se ejecutan al persistir la entidad (en save(), flush(), etc.).
- Si la entidad viene ya validada desde un DTO, normalmente nunca fallarán, pero sirven como “última línea de defensa”.

# Diferencias

| Propósito                                   | ✅ **Anotación estándar (Jakarta)**                             | 🚫 **Alternativa de Hibernate** | 💡 **Comentario / Recomendación**                           |
| ------------------------------------------- | -------------------------------------------------------------- | ------------------------------- | ----------------------------------------------------------- |
| **Campo no nulo**                           | `@NotNull`                                                     | —                               | La más básica y universal.                                  |
| **Cadena no vacía (no null ni "")**         | `@NotBlank`                                                    | —                               | Ideal para `String`, más potente que `@NotEmpty`.           |
| **Colección / array no vacío**              | `@NotEmpty`                                                    | —                               | Asegura que haya al menos un elemento.                      |
| **Longitud / tamaño de cadena o colección** | `@Size(min, max)`                                              | `@Length(min, max)`             | Usa `@Size` (es estándar). `@Length` es solo para `String`. |
| **Valor mínimo / máximo (numérico)**        | `@Min`, `@Max`                                                 | `@Range(min, max)`              | Prefiere `@Min/@Max`; `@Range` no es estándar.              |
| **Número positivo / negativo**              | `@Positive`, `@PositiveOrZero`, `@Negative`, `@NegativeOrZero` | —                               | Recomendadas por legibilidad.                               |
| **Número dentro de rango decimal**          | `@DecimalMin`, `@DecimalMax`                                   | —                               | Mejor precisión para `BigDecimal`.                          |
| **Patrón de texto**                         | `@Pattern(regexp = "regex")`                                   | —                               | Usa esta para expresiones regulares.                        |
| **Correo electrónico**                      | `@Email`                                                       | —                               | Estándar desde Bean Validation 2.0.                         |
| **Validar un objeto anidado**               | `@Valid`                                                       | —                               | Permite validar campos dentro de otro objeto.               |
| **Fecha pasada / futura**                   | `@Past`, `@PastOrPresent`, `@Future`, `@FutureOrPresent`       | —                               | Muy útil en entidades con fechas.                           |
| **Valor booleano verdadero**                | `@AssertTrue` / `@AssertFalse`                                 | —                               | Ideal para validaciones personalizadas.                     |
| **Formato de URL**                          | —                                                              | `@URL`                          | Solo existe en Hibernate Validator. Muy útil para enlaces.  |
| **Número de tarjeta de crédito válido**     | —                                                              | `@CreditCardNumber`             | Hibernate-specific, útil si lo necesitas.                   |
| **ISBN (libros)**                           | —                                                              | `@ISBN`                         | Hibernate-specific, valida ISBN-10 o ISBN-13.               |
| **Elementos únicos en una colección**       | —                                                              | `@UniqueElements`               | Hibernate-specific, no hay equivalente estándar.            |
| **Identificador UUID válido**               | —                                                              | `@UUID`                         | Hibernate-specific, útil si manejas UUIDs como texto.       |
| **Valor no igual a otro campo**             | —                                                              | (custom constraint)             | No hay estándar, debes crear una anotación propia.          |
