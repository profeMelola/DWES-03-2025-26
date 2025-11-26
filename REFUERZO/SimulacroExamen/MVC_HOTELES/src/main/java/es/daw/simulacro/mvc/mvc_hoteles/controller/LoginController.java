package es.daw.simulacro.mvc.mvc_hoteles.controller;

import es.daw.simulacro.mvc.mvc_hoteles.dto.Hotel;
import es.daw.simulacro.mvc.mvc_hoteles.service.AuthService;
import es.daw.simulacro.mvc.mvc_hoteles.service.HotelService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class LoginController {

    private final AuthService authService;
    private final HotelService hotelService;


//    @GetMapping("/login")
//    public String mostrarFormulario() {
//        return "login";
//    }

    @PostMapping("/login")
    public String procesarLogin(@RequestParam String username,
                                @RequestParam String password,
                                HttpSession session,
                                Model model) {

        String token = null;

        if ("invitado".equalsIgnoreCase(username)) {
//            session.setAttribute("modo", "invitado");
//            session.setAttribute("rol", "INVITADO");

        } else {
            System.out.println("***************************");
            System.out.println("* username: " + username);
            System.out.println("* password: " + password);
            System.out.println("***************************");

            token = authService.obtenerToken(username, password);
            System.out.println("***************************");
            System.out.println("* token: " + token);
            System.out.println("***************************");
            if (token == null || token.isEmpty() || token.startsWith("ERROR")) {
                model.addAttribute("error", token);
                return "login";
            }
            session.setAttribute("jwt", token);
            if(username.equalsIgnoreCase("admin"))
                session.setAttribute("rol", "ADMIN");
            else
                session.setAttribute("rol", "USER");
        }

        // Buscar todos los hoteles sin filtro de ciudad
        List<Hotel> hoteles = hotelService.buscarHoteles();
        model.addAttribute("hoteles", hoteles);

        return "buscarHoteles"; // vista Thymeleaf que muestra resultados
    }
}
