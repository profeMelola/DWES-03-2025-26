package es.daw.productoapirest.controller;

import es.daw.productoapirest.dto.ProductoDTO;
import es.daw.productoapirest.repository.ProductoRepository;
import es.daw.productoapirest.service.ProductoService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor // crear un constructor con propiedades final
@RestController
@RequestMapping("/api/productos")
@Validated
/*
Spring no ejecuta el Bean Validation sobre parámetros simples (como @PathVariable o @RequestParam).
Por eso tu @Pattern no dispara la excepción ConstraintViolationException.

En los endpoint tipo:
@PostMapping
public ResponseEntity<ProductoDTO> crearProducto(@Valid @RequestBody ProductoDTO productoDTO) {
    ...
}
@Valid (de jakarta.validation.Valid) ya activa automáticamente la validación del cuerpo del request (@RequestBody).
Si hay errores, lanza MethodArgumentNotValidException
 */
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

    // ----------------------------------
    // CONFIGURACIÓN PERSONALIZADA
    @Value("${config.daw.code}")
    private String code_conf;
    @Value("${config.daw.message}")
    private String message_conf;
    //-------------------------------------


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


    // PENDIENTE CAPTURAR EXCEPTION ConstraintViolationException
    @DeleteMapping("/{codigo}")
    public ResponseEntity<Void> delete(@PathVariable
                              @Pattern(regexp = "^[0-9]{3}[A-Z]{1}$",
                                      message="El código debe tener 3 dígitos + 1 letra May al final"
                              )
                                           String codigo) {
//        if (productoService.deleteByCodigo(codigo))
//            return ResponseEntity.noContent().build(); // 204... ok!
        //return ResponseEntity.notFound().build(); // 404

        productoService.deleteByCodigo(codigo);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

    }

    @GetMapping("/parse-int")
    public String parseInteger(@RequestParam(name="numero",defaultValue = "666")
                               //int number){
                                   String number) {
        int parsedNumber = Integer.parseInt(number); // Puede lanzar NumberFormatException
        return "Parsed number: " + parsedNumber;

        //return "Parsed number "+number;
    }


    @PutMapping("/{codigo}")
    public ResponseEntity<ProductoDTO> update(
            @PathVariable String codigo,
            @Valid @RequestBody ProductoDTO productoDTO
    ) {

        Optional<ProductoDTO> dto = productoService.update(codigo, productoDTO);

        if (dto.isPresent()) {
            return ResponseEntity.ok(dto.get());
        }

        return ResponseEntity.notFound().build();

    }

    @PatchMapping("/{codigo}")
    public ResponseEntity<ProductoDTO> updateParcial(@Valid @RequestBody Map<String,Object> camposActualizados, @PathVariable String codigo) {

        Optional<ProductoDTO> dto = productoService.updateParcial(codigo, camposActualizados);

        if (dto.isPresent()) {
            return ResponseEntity.ok(dto.get());
        }

        return ResponseEntity.notFound().build();
    }

    @PatchMapping("/dto/{codigo}")
    public ResponseEntity<ProductoDTO> updateParcial(@Valid @RequestBody ProductoDTO productoDTO, @PathVariable String codigo) {

        Optional<ProductoDTO> dto = productoService.updateParcialdto(codigo, productoDTO);

        if (dto.isPresent()) {
            return ResponseEntity.ok(dto.get());
        }

        return ResponseEntity.notFound().build();
    }


    // ---------------- CONF -------------------------
    @GetMapping("/values-conf")
    public Map<String,String> values(){
        Map<String,String> json = new HashMap<>();
        json.put("code",code_conf);
        json.put("message",message_conf);
        return json;
    }


    @GetMapping("/values-conf2")
    public Map<String,String> values(@Value("${config.daw.code}") String code, @Value("${config.daw.message}") String message){
        Map<String,String> json = new HashMap<>();
        json.put("code",code);
        json.put("message",message);
        return json;
    }


}
