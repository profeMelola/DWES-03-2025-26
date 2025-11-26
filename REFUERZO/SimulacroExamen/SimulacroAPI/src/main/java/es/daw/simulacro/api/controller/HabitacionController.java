package es.daw.simulacro.api.controller;

import es.daw.simulacro.api.dto.HabitacionRequestDTO;
import es.daw.simulacro.api.dto.HabitacionResponseDTO;
import es.daw.simulacro.api.service.HabitacionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/habitaciones")
class HabitacionController {

    private final HabitacionService habitacionService;



    @GetMapping("/{codigoHotel}/buscar")
    public ResponseEntity<List<HabitacionResponseDTO>> buscar(@PathVariable String codigoHotel,
                                                              @RequestParam(required = false) Integer tamanoMinimo,
                                                              @RequestParam(required = false) Double precioMinimo,
                                                              @RequestParam(required = false) Double precioMaximo) {
        return ResponseEntity.ok(habitacionService.buscarDisponibles(codigoHotel, tamanoMinimo, precioMinimo, precioMaximo));
    }

    @PostMapping("/{codigoHotel}")
    public ResponseEntity<HabitacionResponseDTO> crear(@PathVariable String codigoHotel,
                                                       @RequestBody @Valid HabitacionRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(habitacionService.crearHabitacion(codigoHotel, dto));
    }

    @DeleteMapping("/{codigoHabitacion}")
    public ResponseEntity<Void> eliminar(@PathVariable String codigoHabitacion) {
        habitacionService.eliminar(codigoHabitacion);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{codigoHabitacion}/ocupar")
    public ResponseEntity<HabitacionResponseDTO> ocupar(@PathVariable String codigoHabitacion) {
        return ResponseEntity.ok(habitacionService.ocupar(codigoHabitacion));
    }
}
