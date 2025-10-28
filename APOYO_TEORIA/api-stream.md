# API Stream

El API Stream de Java (introducido en Java 8) es una forma moderna y funcional de trabajar con colecciones de datos (listas, conjuntos, etc.) de manera más declarativa y limpia.

👉 En lugar de decir **“cómo hacerlo”** (bucles for), decimos **“qué queremos hacer”** sobre los datos.

✅ Código más limpio y fácil de leer
✅ Evita bucles anidados
✅ Facilita operaciones sobre datos (filtrar, transformar, agrupar)
✅ Permite paralelizar (con .parallelStream())

Más información [Programación Funcional](https://github.com/profeMelola/DWES-02-2025-26/blob/main/APOYO_TEORIA/Interfaces%20funcionales%20y%20lambdas.md)

---
## Con qué clases se usa?

Principalmente con colecciones Set y List.

<img width="828" height="581" alt="imagen" src="https://github.com/user-attachments/assets/d8923865-bd86-413e-a345-b9eeaa286185" />


---
## Conceptos básicos

| Concepto                    | Qué hace                                            | Ejemplo simple                             |
| --------------------------- | --------------------------------------------------- | ------------------------------------------ |
| **Stream**                  | Flujo de datos sobre los que aplicamos operaciones. | `lista.stream()`                           |
| **Operaciones intermedias** | Transforman el flujo (devuelven otro Stream).       | `filter`, `map`, `sorted`                  |
| **Operaciones terminales**  | Cierran el flujo (devuelven un resultado).          | `forEach`, `collect`, `count`, `findFirst` |

---
## Métodos más comunes

### Operaciones intermedias

| Método              | Qué hace                             | Ejemplo               |
| ------------------- | ------------------------------------ | --------------------- |
| `filter(Predicate)` | Filtra elementos según una condición | `.filter(x -> x > 5)` |
| `map(Function)`     | Transforma los elementos             | `.map(x -> x * 2)`    |
| `sorted()`          | Ordena los elementos                 | `.sorted()`           |
| `distinct()`        | Elimina duplicados                   | `.distinct()`         |
| `limit(n)`          | Toma solo los primeros n elementos   | `.limit(3)`           |
| `skip(n)`           | Salta los primeros n elementos       | `.skip(2)`            |

### Operaciones terminales

| Método                         | Qué hace              | Ejemplo                         |
| ------------------------------ | --------------------- | ------------------------------- |
| `forEach(Consumer)`            | Recorre los elementos | `.forEach(System.out::println)` |
| `collect(Collectors.toList())` | Convierte a lista     | `.collect(Collectors.toList())` |
| `count()`                      | Cuenta los elementos  | `.count()`                      |
| `findFirst()`                  | Devuelve el primero   | `.findFirst().get()`            |
| `anyMatch(Predicate)`          | ¿Alguno cumple?       | `.anyMatch(x -> x > 10)`        |
| `allMatch(Predicate)`          | ¿Todos cumplen?       | `.allMatch(x -> x > 0)`         |
| `noneMatch(Predicate)`         | ¿Ninguno cumple?      | `.noneMatch(x -> x < 0)`        |
| `reduce()`                     | Combina todos en uno  | `.reduce(0, (a,b) -> a + b)`    |

### Operaciones de cortocircuito (Short-circuit operations):

| Método                 | Tipo         | ¿Cuándo se detiene?                                                          | Descripción                                             | Ejemplo                                  |
| :--------------------- | :----------- | :--------------------------------------------------------------------------- | :------------------------------------------------------ | :--------------------------------------- |
| `anyMatch(Predicate)`  | **Terminal** | Se detiene tan pronto como **encuentra un elemento que cumple la condición** | Comprueba si **al menos uno** cumple                    | `lista.stream().anyMatch(x -> x > 10)`   |
| `allMatch(Predicate)`  | **Terminal** | Se detiene tan pronto como **encuentra un elemento que no cumple**           | Comprueba si **todos** cumplen                          | `lista.stream().allMatch(x -> x > 0)`    |
| `noneMatch(Predicate)` | **Terminal** | Se detiene tan pronto como **encuentra un elemento que cumple**              | Comprueba si **ninguno** cumple                         | `lista.stream().noneMatch(x -> x < 0)`   |
| `findFirst()`          | **Terminal** | Se detiene **al encontrar el primer elemento**                               | Devuelve el **primer elemento** del Stream (si existe)  | `lista.stream().findFirst().get()`       |
| `findAny()`            | **Terminal** | Se detiene **al encontrar cualquier elemento**                               | Devuelve **uno cualquiera** (útil con *parallelStream*) | `lista.parallelStream().findAny().get()` |

---

## Paso a paso

```
List<Integer> transactionsIds = 
    transactions.stream()
                .filter(t -> t.getType() == Transaction.GROCERY)
                .sorted(comparing(Transaction::getValue).reversed())
                .map(Transaction::getId)
                .collect(toList());
```

<img width="831" height="724" alt="imagen" src="https://github.com/user-attachments/assets/f7d46283-f39e-4e22-bf89-7f208a430291" />



