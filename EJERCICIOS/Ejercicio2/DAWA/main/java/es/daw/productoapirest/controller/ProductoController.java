package es.daw.productoapirest.controller;

import es.daw.productoapirest.dto.ProductoDTO;
import es.daw.productoapirest.entity.Producto;
import es.daw.productoapirest.repository.ProductoRepository;
import es.daw.productoapirest.service.ProductoService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor // crear un constructor con propiedades final
@RestController
@RequestMapping("/api/productos")
@Validated
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
    public ResponseEntity<Void> delete(@PathVariable
        @Pattern(
                regexp = "^[0-9]{3}[A-Z]$",
                message="El código debe empezar por 3 dígitos seguido de una letra mayúscula"
        )
        String codigo
    ) {
//        if (productoService.deleteByCodigo(codigo)) {
//            System.out.println("************* BORRADO ************");
//            return ResponseEntity.noContent().build();//204, ok
//        }

        productoService.deleteByCodigo(codigo);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build(); //204

    }

    @GetMapping("/parse-int")
    public String parseInteger(@RequestParam(name = "numero",defaultValue = "666") String number) {
        int parsedNumber = Integer.parseInt(number); // Puede lanzar NumberFormatException
        return "Parsed number: " + parsedNumber;
    }

    @PutMapping("/{codigo}")
    public ResponseEntity<ProductoDTO> update(
            @PathVariable String codigo,
            @Valid @RequestBody ProductoDTO productoDTO
    ){
        Optional<ProductoDTO> dto = productoService.update(codigo, productoDTO);

        if (dto.isPresent()) {
            return ResponseEntity.ok(dto.get());
        }
        return ResponseEntity.notFound().build();

    }

    @PatchMapping("/dto/{codigo}")
    public ResponseEntity<ProductoDTO> updateParcial(@Valid @RequestBody ProductoDTO productoDTO, @PathVariable String codigo) {
        Optional<ProductoDTO> dto = productoService.updateParcial(codigo, productoDTO);

        if (dto.isPresent()) {
            return ResponseEntity.ok(dto.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PatchMapping("/{codigo}")
    public ResponseEntity<ProductoDTO> updateParcial(@Valid @RequestBody Map<String,Object> propiedades, @PathVariable String codigo) {

      /*
            REFLEXIÓN!!!!
            Se utiliza para acceder y manipular campos (atributos) de una clase mediante la reflexión.
            La reflexión es una poderosa característica de Java que te permite inspeccionar, analizar y
            modificar la estructura y el comportamiento de clases, métodos, constructores y
            campos en tiempo de ejecución.
         */
        Optional<ProductoDTO> dto = productoService.updateParcialReflect(codigo,propiedades);

        if (dto.isPresent()) {
            return ResponseEntity.ok(dto.get());
        }

        return ResponseEntity.notFound().build();
    }

}
