package es.daw.simulacro.mvc.mvc_hoteles.service;

import es.daw.simulacro.mvc.mvc_hoteles.dto.Hotel;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HotelService {

    private final WebClient webClientHotel;

    public List<Hotel> buscarHoteles() {
        return webClientHotel.get()
                .uri("/hoteles/buscar")
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<Hotel>>() {})
                .block();
    }
}

