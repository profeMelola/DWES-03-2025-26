package es.daw.simulacro.mvc.mvc_hoteles.controller;

import es.daw.simulacro.mvc.mvc_hoteles.dto.Hotel;
import es.daw.simulacro.mvc.mvc_hoteles.service.HotelService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/hoteles")
@RequiredArgsConstructor
public class HotelController {

    private final HotelService hotelService;

    @GetMapping
    public String hoteles(Model model) {
        List<Hotel> hoteles = hotelService.buscarHoteles();
        model.addAttribute("hoteles", hoteles);

        return "buscarHoteles"; // vista Thymeleaf que muestra resultados

    }
}
