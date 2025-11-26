package es.daw.simulacro.mvc.mvc_hoteles.service;

import es.daw.simulacro.mvc.mvc_hoteles.dto.Habitacion;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;

@Service
public class HabitacionService {

    private final WebClient webClientHotel;

    public HabitacionService(WebClient webClientHotel) {
        this.webClientHotel = webClientHotel;
    }

    public List<Habitacion> buscarHabitacionesPorHotel(String codigoHotel) {
        Habitacion[] habitaciones = webClientHotel.get()
                .uri("/habitaciones/{codigo}/buscar", codigoHotel)
                .retrieve()
                .bodyToMono(Habitacion[].class)
                .block();

        return habitaciones != null ? Arrays.asList(habitaciones) : List.of();
    }

    public void eliminarHabitacion(String codigoHabitacion, String token) {
        System.out.println("* eliminarHabitacion, codigoHabitacion: " + codigoHabitacion + ", token: " + token);
        webClientHotel.delete()
                .uri("/habitaciones/"+codigoHabitacion, codigoHabitacion)
                .header("Authorization","Bearer "+token)
                .retrieve()
                .bodyToMono(Void.class)
                .block();
        System.out.println("* eliminarHabitacion completo");
    }
}

