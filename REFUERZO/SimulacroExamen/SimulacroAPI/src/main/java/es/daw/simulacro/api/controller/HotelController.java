package es.daw.simulacro.api.controller;

import es.daw.simulacro.api.dto.HotelRequestDTO;
import es.daw.simulacro.api.dto.HotelResponseDTO;
import es.daw.simulacro.api.service.HotelService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/hoteles")
class HotelController {

    private final HotelService hotelService;

//    @GetMapping
//    public ResponseEntity<List<HotelResponseDTO>> getAllHotels() {
//        return ResponseEntity.ok(hotelService.buscarHoteles(null, null));
//    }

    @GetMapping("/buscar")
    public ResponseEntity<List<HotelResponseDTO>> buscar(@RequestParam(required = false) String localidad,
                                                         @RequestParam(required = false) String codigoCategoria) {
        return ResponseEntity.ok(hotelService.buscarHoteles(localidad, codigoCategoria));
    }

    @PostMapping
    public ResponseEntity<HotelResponseDTO> crear(@RequestBody @Valid HotelRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(hotelService.crearHotel(dto));
    }
}
