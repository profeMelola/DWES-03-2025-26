package es.daw.simulacro.api.service;

import es.daw.simulacro.api.dto.CategoriaDTO;
import es.daw.simulacro.api.dto.HotelRequestDTO;
import es.daw.simulacro.api.dto.HotelResponseDTO;
import es.daw.simulacro.api.entity.Categoria;
import es.daw.simulacro.api.entity.Hotel;
import es.daw.simulacro.api.exception.CategoriaNotFoundException;
import es.daw.simulacro.api.exception.HotelNotFoundException;
import es.daw.simulacro.api.repository.CategoriaRepository;
import es.daw.simulacro.api.repository.HotelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HotelService {

    private final HotelRepository hotelRepository;
    private final CategoriaRepository categoriaRepository;

    public List<HotelResponseDTO> buscarHoteles(String localidad, String codigoCategoria) {
        List<Hotel> hoteles;
        if (localidad != null) {
            hoteles = hotelRepository.findByLocalidadIgnoreCase(localidad);
        } else if (codigoCategoria != null) {
            hoteles = hotelRepository.findByCategoria_Codigo(codigoCategoria);
        } else {
            hoteles = hotelRepository.findAll();
        }
        System.out.println("* HOTELES: " + hoteles.size());
        return hoteles.stream().map(this::toDTO).collect(Collectors.toList());
    }

    public HotelResponseDTO crearHotel(HotelRequestDTO dto) {
        Categoria categoria = categoriaRepository.findByCodigo(dto.getCodigoCategoria())
                .orElseThrow(() -> new CategoriaNotFoundException(dto.getCodigoCategoria()));

        Hotel hotel = Hotel.builder()
                .codigo(dto.getCodigo())
                .nombre(dto.getNombre())
                .descripcion(dto.getDescripcion())
                .piscina(dto.isPiscina())
                .localidad(dto.getLocalidad())
                .categoria(categoria)
                .build();

        return toDTO(hotelRepository.save(hotel));
    }

    public Hotel getByCodigo(String codigo) {
        return hotelRepository.findByCodigo(codigo)
                .orElseThrow(() -> new HotelNotFoundException(codigo));
    }

    private HotelResponseDTO toDTO(Hotel h) {
        return HotelResponseDTO.builder()
                .codigo(h.getCodigo())
                .nombre(h.getNombre())
                .descripcion(h.getDescripcion())
                .piscina(h.isPiscina())
                .localidad(h.getLocalidad())
                .categoria(CategoriaDTO.builder()
                        .codigo(h.getCategoria().getCodigo())
                        .nombre(h.getCategoria().getNombre())
                        .build())
                .build();
    }
}
