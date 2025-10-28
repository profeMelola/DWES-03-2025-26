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

Principalmente con colecciones **Set y List** pero también podemos usarlo con **Map**

<img width="828" height="581" alt="imagen" src="https://github.com/user-attachments/assets/d8923865-bd86-413e-a345-b9eeaa286185" />

El API Stream también se usa con **Java NIO**, de hecho, NIO.2 (desde Java 8) incorpora métodos que devuelven Streams, lo cual permite procesar archivos y directorios de forma funcional, igual que con colecciones.

| Fuente del Stream      | Método NIO                           | Tipo de Stream devuelto |
| ---------------------- | ------------------------------------ | ----------------------- |
| Directorio             | `Files.list(Path)`                   | `Stream<Path>`          |
| Directorios recursivos | `Files.walk(Path)`                   | `Stream<Path>`          |
| Líneas de texto        | `Files.lines(Path)`                  | `Stream<String>`        |
| Archivo grande         | `Files.find(Path, int, BiPredicate)` | `Stream<Path>`          |


### ¿De dónde vienen los Streams?

| Fuente                         | Ejemplo                                 | Tipo de Stream   |
| ------------------------------ | --------------------------------------- | ---------------- |
| Colecciones                    | `lista.stream()`                        | `Stream<T>`      |
| Arrays                         | `Arrays.stream(array)`                  | `Stream<T>`      |
| Generadores                    | `Stream.of(...)`, `Stream.iterate(...)` | `Stream<T>`      |
| **Java NIO (ficheros)**        | `Files.list(Path)`                      | `Stream<Path>`   |
| **Java NIO (líneas de texto)** | `Files.lines(Path)`                     | `Stream<String>` |



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


---

## API Stream con Map

```
// Recorrer e imprimir (con forEach)
productos.forEach((clave, valor) -> 
    System.out.println(clave + " cuesta " + valor + " €"));

// Filtrar con Stream (por ejemplo, precios mayores de 1.0)
productos.entrySet().stream()
    .filter(e -> e.getValue() > 1.0)
    .forEach(e -> System.out.println(e.getKey() + " -> " + e.getValue()));


// Obtener solo los nombres de productos caros
List<String> caros = productos.entrySet().stream()
    .filter(e -> e.getValue() > 1.0)
    .map(Map.Entry::getKey)
    .toList();

System.out.println(caros); 


// Sumar todos los precios
double total = productos.values().stream()
    .mapToDouble(Double::doubleValue)
    .sum();

System.out.println("Total: " + total);
```

---

## Java NIO y el API Stream

### Listar archivos de un directorio


Con File y bucles:


```
File carpeta = new File("src");
for (File f : carpeta.listFiles()) {
    System.out.println(f.getName());
}

```

Con NIO + Stream:


```
import java.nio.file.*;
import java.io.IOException;

public class EjemploNIO1 {
    public static void main(String[] args) throws IOException {
        Files.list(Path.of("src"))
             .forEach(System.out::println);
    }
}

```

### Listar todos los archivos (incluso subcarpetas)


```
Files.walk(Path.of("src"))
     .forEach(System.out::println);

```

### Filtrar archivos


```
Files.walk(Path.of("src"))
     .filter(p -> p.toString().endsWith(".java"))
     .forEach(System.out::println);

```

### Leer líneas de un fichero


```
Path ruta = Path.of("src/ejemplo.txt");

Files.lines(ruta)
     .forEach(System.out::println);

Files.lines(Path.of("datos.txt"))
     .filter(linea -> linea.contains("error"))
     .forEach(System.out::println);


```
