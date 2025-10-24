package es.daw.productoapirest.controller;

import es.daw.productoapirest.dto.ProductoDTO;
import es.daw.productoapirest.repository.ProductoRepository;
import es.daw.productoapirest.service.ProductoService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor // crear un constructor con propiedades final
@RestController
@RequestMapping("/api/productos")
public class ProductoController {

    // 1. inyección por propiedad
    // - Viola la separación de responsabilidades (`SRP`, Single Responsibility Principle) porque el controlador estaría
    // asumiendo parte de la lógica de negocio (manejar el flujo de datos entre el DTO y la entidad).
    // - Escalar y mantener el código podría ser más complicado si en el futuro necesitas lógica de negocio adicional (tendrás que refactorizar para introducir un servicio).
    // Casos muy simples, como un proyecto pequeño de una clase o ejemplos de demostración rápida.
//    @Autowired
//    private ProductoRepository productoRepository;

    // - Al declarar las dependencias como `final`, te aseguras de que éstas se inicialicen una única vez (en el constructor) y no puedan ser sobrescritas.
    //  Esto mejora la estabilidad del código al garantizar que las dependencias no cambien.
    // - Puedes crear fácilmente objetos de prueba y pasar implementaciones "mock" o "fake" a los tests unitarios de la clase.
    // - No dependes de un framework como Spring para inicializar las dependencias en el momento de las pruebas.
    // - Con la inyección por constructor, es inmediato saber cuáles son las dependencias que necesita una clase,
    // ya que están listadas explícitamente en la firma del constructor. Esto evita confusión sobre cómo y cuándo se inicializan.
    // 2. inyección por constructor

    private final ProductoService productoService;


    @GetMapping
    public ResponseEntity<List<ProductoDTO>> findAll() {
        return ResponseEntity.ok(productoService.findAll());
    }

    // Encontrar producto por código
    @GetMapping("/{codigo}")
    public ResponseEntity<ProductoDTO> findByCodigo(
            @Pattern(regexp = "^[0-9]{3}[A-Z]$",message="El código debe tener 3 digitos + 1 letra May")
                                                        @PathVariable String codigo) {
        Optional<ProductoDTO> prodDTO = productoService.findByCodigo(codigo);
        if (prodDTO.isPresent()) {
            return ResponseEntity.ok(prodDTO.get()); //200
        } else {
            return ResponseEntity.notFound().build(); //
        }
    }

    @PostMapping
    public ResponseEntity<ProductoDTO> save(@Valid @RequestBody ProductoDTO productoDTO) {
        // Cuando se da de alta un producto siempre pertenece al fabricante con id 1
        Optional<ProductoDTO> creado = productoService.crearProducto(productoDTO);

        if  (creado.isPresent()) {
            //return ResponseEntity.ok(creado.get()); // 200
            return ResponseEntity.status(HttpStatus.CREATED).body(creado.get());
        }
        return ResponseEntity.notFound().build();  //no encontrado.... es el fabricante

    }

    @DeleteMapping("/{codigo}")
    public ResponseEntity<Void> delete(@PathVariable String codigo) {
        // PENDIENTE VALIDACIONES... 3 DÍGITOS Y UNA LETRA AL FINAL...
        if (productoService.deleteByCodigo(codigo))
            return ResponseEntity.noContent().build();
        return ResponseEntity.notFound().build();
    }


}
