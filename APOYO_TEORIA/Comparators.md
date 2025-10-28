# Comparator en Java

Un Comparator define la forma de comparar dos objetos para ordenarlos.

```
@FunctionalInterface
public interface Comparator<T> {
    int compare(T o1, T o2);
}
```

| Método                                 | Descripción                             |
| -------------------------------------- | --------------------------------------- |
| `comparing()`                          | Crea un comparator por clave            |
| `comparingInt()` / `comparingDouble()` | Comparación eficiente por primitivo     |
| `reversed()`                           | Invierte el orden                       |
| `thenComparing()`                      | Añade orden secundario                  |
| `Collections.sort(list, comp)`         | Ordena con Comparator (antes de Java 8) |
| `list.sort(comp)`                      | Método preferido (Java 8+)              |
| `Comparator.reverseOrder()`            | Orden inverso natural                   |

---

## Comparator clásico

```
List<Persona> lista = new ArrayList<>(personas);

Collections.sort(lista, new Comparator<Persona>() {
    @Override
    public int compare(Persona p1, Persona p2) {
        return p1.edad - p2.edad; // orden ascendente
    }
});
System.out.println(lista);
```

## Con lambda

```
List<Persona> lista = new ArrayList<>(personas);

lista.sort((p1, p2) -> p1.edad - p2.edad);
System.out.println(lista);

```

---
## Uso de métodos estáticos de Comparator

Comparator.comparing(...)

Permite crear un Comparator fácilmente a partir de una función de clave (key extractor).

Con lambda:

``` 
lista.sort(Comparator.comparing(p -> p.edad));
``` 

Con referencia a método:

``` 
lista.sort(Comparator.comparing(Persona::getEdad));
```

### Orden inverso

```
lista.sort(Comparator.comparing(Persona::getEdad).reversed());
```

### Comparar por otro campo

```
lista.sort(Comparator.comparing(Persona::getNombre));

```

---

## Orden compuesto (anidaciones)

```
lista.sort(
    Comparator.comparing(Persona::getEdad)
              .thenComparing(Persona::getNombre)
);

```

---

## Comparadores con tipo primitivo

Para evitar autoboxing, Java tiene comparadores especializados:

- comparingInt(ToIntFunction)
- comparingDouble(ToDoubleFunction)
- comparingLong(ToLongFunction)

```
lista.sort(Comparator.comparingInt(Persona::getEdad));

```

---

## Comparadores en el API Stream

```
personas.stream()
        .sorted(Comparator.comparing(Persona::getEdad))
        .forEach(System.out::println);

// Con varios criterios
personas.stream()
        .sorted(Comparator.comparing(Persona::getNombre)
                          .thenComparingInt(Persona::getEdad))
        .forEach(System.out::println);

``` 
