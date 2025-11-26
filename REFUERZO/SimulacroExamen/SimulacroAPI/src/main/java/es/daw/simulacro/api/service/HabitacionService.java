package es.daw.simulacro.api.service;

import es.daw.simulacro.api.dto.HabitacionRequestDTO;
import es.daw.simulacro.api.dto.HabitacionResponseDTO;
import es.daw.simulacro.api.entity.Habitacion;
import es.daw.simulacro.api.entity.Hotel;
import es.daw.simulacro.api.exception.HabitacionNotFoundException;
import es.daw.simulacro.api.repository.HabitacionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HabitacionService {

    private final HabitacionRepository habitacionRepository;
    private final HotelService hotelService;

    public List<HabitacionResponseDTO> buscarDisponibles(String codigoHotel, Integer tamanoMin, Double precioMin, Double precioMax) {
        // Validar que el hotel existe
        hotelService.getByCodigo(codigoHotel); // Lanzará HotelNotFoundException si no existe

        if (tamanoMin != null && precioMin != null && precioMax != null) {
            return habitacionRepository.findByHotel_CodigoAndOcupadaFalseAndTamanoGreaterThanEqualAndPrecioNocheBetween(
                            codigoHotel, tamanoMin, precioMin, precioMax)
                    .stream().map(this::toDTO).collect(Collectors.toList());
        } else {
            return habitacionRepository.findByHotel_CodigoAndOcupadaFalse(codigoHotel)
                    .stream().map(this::toDTO).collect(Collectors.toList());
        }
    }

    public HabitacionResponseDTO crearHabitacion(String codigoHotel, HabitacionRequestDTO dto) {
        Hotel hotel = hotelService.getByCodigo(codigoHotel);
        Habitacion h = Habitacion.builder()
                .codigo(dto.getCodigo())
                .tamano(dto.getTamano())
                .doble(dto.isDoble())
                .precioNoche(dto.getPrecioNoche())
                .incluyeDesayuno(dto.isIncluyeDesayuno())
                .ocupada(false)
                .hotel(hotel)
                .build();
        return toDTO(habitacionRepository.save(h));
    }

    public void eliminar(String codigoHabitacion) {
        Habitacion h = habitacionRepository.findByCodigo(codigoHabitacion)
                .orElseThrow(() -> new HabitacionNotFoundException(codigoHabitacion));
        habitacionRepository.delete(h);
    }

    public HabitacionResponseDTO ocupar(String codigoHabitacion) {
        Habitacion h = habitacionRepository.findByCodigo(codigoHabitacion)
                .orElseThrow(() -> new HabitacionNotFoundException(codigoHabitacion));
        h.setOcupada(true);
        return toDTO(habitacionRepository.save(h));
    }

    private HabitacionResponseDTO toDTO(Habitacion h) {
        return HabitacionResponseDTO.builder()
                .codigo(h.getCodigo())
                .tamano(h.getTamano())
                .doble(h.isDoble())
                .precioNoche(h.getPrecioNoche())
                .incluyeDesayuno(h.isIncluyeDesayuno())
                .ocupada(h.isOcupada())
                .build();
    }
}
