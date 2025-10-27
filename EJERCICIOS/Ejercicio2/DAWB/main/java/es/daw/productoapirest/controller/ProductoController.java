package es.daw.productoapirest.controller;

import es.daw.productoapirest.dto.ProductoDTO;
import es.daw.productoapirest.repository.ProductoRepository;
import es.daw.productoapirest.service.ProductoService;
import jakarta.validation.Valid;
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
    // - Viola la separación de responsabilidades (`SRP`, Single Responsibility Principle) porque el controlador estaría asumiendo parte de la lógica de negocio (manejar el flujo de datos entre el DTO y la entidad).
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
    public ResponseEntity<ProductoDTO> findByCodigo(@PathVariable String codigo) {
        Optional<ProductoDTO> prodDTO = productoService.findByCodigo(codigo);

        if (prodDTO.isPresent()) {
            return ResponseEntity.ok(prodDTO.get()); //200
        } else {
            return ResponseEntity.notFound().build(); //404
        }
    }

    @PostMapping
    public ResponseEntity<ProductoDTO> create(@Valid @RequestBody ProductoDTO productoDTO) {
        Optional<ProductoDTO> dto = productoService.create(productoDTO);
        if (dto.isPresent()) {
            //return ResponseEntity.ok(dto.get()); // 200
            return ResponseEntity.status(HttpStatus.CREATED).body(dto.get());
        }else{
            return ResponseEntity.badRequest().build(); // algún problema con argumentos de entrado
            //return ResponseEntity.internalServerError().build(); //500
        }
    }


    @DeleteMapping("/{codigo}")
    public ResponseEntity<Void> delete(@PathVariable String codigo) {
        if (productoService.deleteByCodigo(codigo))
            return ResponseEntity.noContent().build(); // 204... ok!

        return ResponseEntity.notFound().build(); // 404
    }


}
