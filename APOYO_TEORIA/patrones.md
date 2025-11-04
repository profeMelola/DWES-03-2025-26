# Patrones de diseño y principios SOLID

Los patrones de diseño son soluciones generalmente aplicables a problemas comunes en el diseño de software. 

Proporcionan un enfoque probado y estructurado para resolver problemas recurrentes y mejorar la calidad y flexibilidad del código.

Los principios SOLID y los patrones de diseño son herramientas clave para desarrollar software orientado a objetos limpio, mantenible y extensible.

![solid](solid.gif)

## Principios SOLID
Los Principios SOLID son cinco principios que guían el diseño de software orientado a objetos para crear sistemas más robustos, mantenibles y escalables:
- **Principio de responsabilidad única (SRP):** Una clase debe tener una, y solo una, razón para cambiar.

**Ejemplo:**
```java
// ❌ Ejemplo incorrecto
class Report {
    void generateReport() { /* genera el reporte */ }
    void saveToFile() { /* guarda el reporte en archivo */ }
}

// ✅ Aplicando SRP
class ReportGenerator {
    void generateReport() { /* genera el reporte */ }
}

class ReportSaver {
    void saveToFile() { /* guarda el reporte */ }
}
```

- **Principio abierto/cerrado (OCP):** Las entidades de software deben estar abiertas para la extensión, pero cerradas para la modificación.

**Ejemplo:**
```java
interface Shape {
    double area();
}

class Circle implements Shape {
    private double radius;
    public Circle(double radius) { this.radius = radius; }
    public double area() { return Math.PI * radius * radius; }
}

class Rectangle implements Shape {
    private double width, height;
    public Rectangle(double width, double height) {
        this.width = width; this.height = height;
    }
    public double area() { return width * height; }
}

class AreaCalculator {
    public double calculateArea(Shape shape) {
        return shape.area();
    }
}
```


- **Principio de sustitución de Liskov (LSP):** Los objetos de una superclase deben poder ser reemplazados por objetos de una subclase sin afectar la corrección del programa.

**Ejemplo:**
```java
class Bird {
    void fly() { System.out.println("Vuelo"); }
}

class Sparrow extends Bird { }

// Si tuviéramos una subclase que no vuela, rompe LSP:
class Penguin extends Bird {
    @Override
    void fly() { throw new UnsupportedOperationException("No puede volar"); }
}
```


- **Principio de segregación de interfaces (ISP):** Los clientes no deben ser forzados a depender de interfaces que no usan.

**Ejemplo:**
```java
// ❌ Interface demasiado grande
interface Worker {
    void work();
    void eat();
}

// ✅ Segregar en interfaces más específicas
interface Workable { void work(); }
interface Eatable { void eat(); }

class Human implements Workable, Eatable {
    public void work() { System.out.println("Trabajando"); }
    public void eat() { System.out.println("Comiendo"); }
}

class Robot implements Workable {
    public void work() { System.out.println("Trabajando sin parar"); }
}
```

- **Principio de inversión de dependencias (DIP):** Los módulos de alto nivel no deben depender de los módulos de bajo nivel; ambos deben depender de abstracciones.


**Ejemplo:**
```java
interface MessageService {
    void sendMessage(String msg);
}

class EmailService implements MessageService {
    public void sendMessage(String msg) {
        System.out.println("Email enviado: " + msg);
    }
}

class Notification {
    private final MessageService service;
    public Notification(MessageService service) {
        this.service = service;
    }
    void notifyUser(String message) {
        service.sendMessage(message);
    }
}
```

---
## Patrones de diseño en Java

Existen otros Tipos de Patrones de Diseño que se clasifican en:
- **Patrones de creación** (cómo se instancian los objetos)
- **Estructurales** (cómo se componen las clases y objetos)
- **De comportamiento** (cómo interactúan los objetos)
- **Arquitectónicos** (estructuras globales de las aplicaciones, como MVC o Microservicios).

### 1. Patrón Singleton (Creacional)
Garantiza que solo exista una instancia de una clase y proporciona un punto global de acceso a ella.

```java
public class Singleton {
    private static Singleton instance;

    private Singleton() { }

    public static Singleton getInstance() {
        if (instance == null) {
            instance = new Singleton();
        }
        return instance;
    }
}
```

### 2. Patrón Builder (Creacional)

Permite construir objetos complejos paso a paso.

El patrón Builder se usa cuando una clase tiene varios atributos opcionales o cuando queremos crear objetos inmutables de forma clara y legible, sin necesidad de múltiples constructores o de llamar repetidamente a setters.

En un DTO como ErrorDTO, es útil porque:

- No siempre quieres rellenar todos los campos (por ejemplo, a veces solo message, otras también details).
- Evitas crear objetos parcialmente inicializados con new y setters.
- Permite un código más legible y fluido al construir la respuesta del API.

**Versión con Lombok**

```
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Map;

@Data
@Builder
public class ErrorDTO {
    private String message;
    private LocalDateTime timestamp;
    private Map<String, String> details;
}

```

Incluso si queremos que ErrorDTO sea inmutable (que una vez creado no se pueda modificar), quitaríamos la anotación @Data para que no tenga setters...

**Versión manual**

```

public class ErrorDTO {
    private String message;
    private LocalDateTime timestamp;
    private Map<String, String> details;

    // Constructor privado: solo el Builder puede crear instancias
    // no se puede invocar directamente con new desde fuera. Solo el Builder puede hacerlo.
    private ErrorDTO(Builder builder) {
        this.message = builder.message;
        this.timestamp = builder.timestamp;
        this.details = builder.details;
    }

    // Clase interna estática Builder
    // Es estática: no depende de una instancia de ErrorDTO
    // Contiene los mismos campos que la clase principal.
    // Ofrece métodos encadenables (fluent API) para asignar valores.
    public static class Builder {
        private String message;
        private LocalDateTime timestamp;
        private Map<String, String> details;

        // Métodos del Builder
        // Asigna un valor a un campo del builder.
        // Esto permite encadenar llamadas
        public Builder message(String message) {
            this.message = message;
            return this;
        }

        public Builder timestamp(LocalDateTime timestamp) {
            this.timestamp = timestamp;
            return this;
        }

        public Builder details(Map<String, String> details) {
            this.details = details;
            return this;
        }

        // Crea el objeto definitivo ErrorDTO, pasándole el builder con los valores ya configurados.
        public ErrorDTO build() {
            return new ErrorDTO(this);
        }
    }
}

```

**Uso:**

```
        ErrorDTO errorDTO = ErrorDTO.builder()
                .message("PRODUCTO NO ENCONTRADO")
                .timestamp(LocalDateTime.now())
                .details(
                        Map.of("exception", ex.getClass().getSimpleName(),
                                "message", ex.getMessage()
                        )

                )
                .build();
```

### 3. Patrón Strategy (Comportamiento)
Permite cambiar el comportamiento de una clase en tiempo de ejecución.

```java
interface PaymentStrategy {
    void pay(double amount);
}

class CreditCardPayment implements PaymentStrategy {
    public void pay(double amount) {
        System.out.println("Pagando " + amount + " con tarjeta de crédito.");
    }
}

class PaypalPayment implements PaymentStrategy {
    public void pay(double amount) {
        System.out.println("Pagando " + amount + " con PayPal.");
    }
}

class PaymentContext {
    private PaymentStrategy strategy;

    public PaymentContext(PaymentStrategy strategy) {
        this.strategy = strategy;
    }

    public void executePayment(double amount) {
        strategy.pay(amount);
    }
}

// Uso
PaymentContext context = new PaymentContext(new PaypalPayment());
context.executePayment(150.0);
```

### 4. Patrón Observer (Comportamiento)
Define una dependencia uno-a-muchos entre objetos, de modo que cuando uno cambia de estado, todos los dependientes son notificados.

```java
import java.util.*;

interface Observer {
    void update(String message);
}

class User implements Observer {
    private String name;
    public User(String name) { this.name = name; }
    public void update(String message) {
        System.out.println(name + " recibió notificación: " + message);
    }
}

class Channel {
    private List<Observer> observers = new ArrayList<>();
    void subscribe(Observer o) { observers.add(o); }
    void notifyObservers(String msg) {
        observers.forEach(o -> o.update(msg));
    }
}

// Uso
Channel canal = new Channel();
canal.subscribe(new User("Ana"));
canal.subscribe(new User("Luis"));
canal.notifyObservers("Nuevo video publicado.");
```

### 5. Patrón Factory Method (Creacional)
Define una interfaz para crear objetos, pero deja que las subclases decidan qué clase instanciar.

```java
interface Shape {
    void draw();
}

class Circle implements Shape {
    public void draw() { System.out.println("Dibujando un círculo"); }
}

class Square implements Shape {
    public void draw() { System.out.println("Dibujando un cuadrado"); }
}

class ShapeFactory {
    public static Shape createShape(String type) {
        return switch (type.toLowerCase()) {
            case "circle" -> new Circle();
            case "square" -> new Square();
            default -> throw new IllegalArgumentException("Forma desconocida");
        };
    }
}

// Uso
Shape shape = ShapeFactory.createShape("circle");
shape.draw();
```

