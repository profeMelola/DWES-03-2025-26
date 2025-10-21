package es.daw.productoapirest.controller;

import es.daw.productoapirest.entity.Producto;
import es.daw.productoapirest.mapper.ProductoMapper;
import es.daw.productoapirest.repository.ProductoRepository;
import es.daw.productoapirest.service.ProductoService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.daw.productoapirest.dto.ProductoDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.swing.*;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/productos")
public class ProductoController {

    /*
- Viola la separación de responsabilidades (`SRP`, Single Responsibility Principle) porque el controlador estaría asumiendo parte de la lógica de negocio (manejar el flujo de datos entre el DTO y la entidad).
- Escalar y mantener el código podría ser más complicado si en el futuro necesitas lógica de negocio adicional (tendrás que refactorizar para introducir un servicio).
     */
// Casos muy simples, como un proyecto pequeño de una clase o ejemplos de demostración rápida.
//    @Autowired
//    private ProductoMapper productoMapper;

    private final ProductoService productoService;

//    @Autowired
//    private ProductoRepository productoRepository;

    // - Al declarar las dependencias como `final`, te aseguras de que éstas se inicialicen una única vez (en el constructor) y no puedan ser sobrescritas. Esto mejora la estabilidad del código al garantizar que las dependencias no cambien.
    // - Puedes crear fácilmente objetos de prueba y pasar implementaciones "mock" o "fake" a los tests unitarios de la clase. No dependes de un framework como Spring para inicializar las dependencias en el momento de las pruebas.
    // - Con la inyección por constructor, es inmediato saber cuáles son las dependencias que necesita una clase, ya que están listadas explícitamente en la firma del constructor. Esto evita confusión sobre cómo y cuándo se inicializan.

    private final ProductoMapper productoMapper;

//    public ProductoController(ProductoService productoService, ProductoMapper productoMapper) {
//        this.productoService = productoService;
//        this.productoMapper = productoMapper;
//    }

    @PostMapping
    public ResponseEntity<ProductoDTO> crearProducto(@RequestBody ProductoDTO productoDTO) {
//        // Convertir ProductoDTO a una entidad Producto
//        Producto producto = productoMapper.toEntity(productoDTO);
//
//        // Guardar la entidad en la base de datos
//        Producto productoGuardado = productoRepository.save(producto);
//
//        // Convertir la entidad guardada de vuelta a ProductoDTO
//        ProductoDTO productoGuardadoDTO = productoMapper.toDTO(productoGuardado);
//
//        return ResponseEntity.status(HttpStatus.CREATED).body(productoGuardadoDTO);
        ProductoDTO nuevoProducto = productoService.crearProducto(productoDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoProducto);

    }



//        /**
//         * Crear un nuevo producto
//         */
//        @PostMapping
//        public ResponseEntity<ProductoDTO> crearProducto(@RequestBody ProductoDTO productoDTO) {
//            ProductoDTO nuevoProducto = productoService.crearProducto(productoDTO);
//            return ResponseEntity.status(HttpStatus.CREATED).body(nuevoProducto);
//        }
//
//        /**
//         * Obtener todos los productos
//         */
//        @GetMapping
//        public ResponseEntity<List<ProductoDTO>> obtenerTodos() {
//            List<ProductoDTO> productos = productoService.obtenerTodos();
//            return ResponseEntity.ok(productos);
//        }
//
//        /**
//         * Obtener un producto por su ID
//         */
//        @GetMapping("/{id}")
//        public ResponseEntity<ProductoDTO> obtenerPorId(@PathVariable Integer id) {
//            ProductoDTO producto = productoService.obtenerPorId(id);
//            return ResponseEntity.ok(producto);
//        }
//
//        /**
//         * Actualizar un producto existente
//         */
//        @PutMapping("/{id}")
//        public ResponseEntity<ProductoDTO> actualizarProducto(
//                @PathVariable Integer id,
//                @RequestBody ProductoDTO productoDTO) {
//            ProductoDTO actualizado = productoService.actualizarProducto(id, productoDTO);
//            return ResponseEntity.ok(actualizado);
//        }

        /**
         * Eliminar un producto por ID
         */
        @DeleteMapping("/{id}")
        public ResponseEntity<Void> eliminarProducto(@PathVariable Integer id) {
            productoService.eliminarProducto(id);
            return ResponseEntity.noContent().build();
        }
    }


}