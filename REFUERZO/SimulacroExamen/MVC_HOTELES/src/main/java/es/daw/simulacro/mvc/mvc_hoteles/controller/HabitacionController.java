package es.daw.simulacro.mvc.mvc_hoteles.controller;

import es.daw.simulacro.mvc.mvc_hoteles.dto.Habitacion;
import es.daw.simulacro.mvc.mvc_hoteles.service.HabitacionService;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/habitaciones")
public class HabitacionController {

    private final HabitacionService habitacionService;

    public HabitacionController(HabitacionService habitacionService) {
        this.habitacionService = habitacionService;
    }

    @GetMapping("/{codigo}/buscar")
    public String verHabitaciones(@PathVariable String codigo,
                                  HttpSession session,
                                  Model model) {
        List<Habitacion> habitaciones = habitacionService.buscarHabitacionesPorHotel(codigo);
        model.addAttribute("habitaciones", habitaciones);
        model.addAttribute("hotelCodigo", codigo);

        String rol = (String) session.getAttribute("rol");
        System.out.println("*** Ver habitaciones: "+rol);
        model.addAttribute("rol", rol != null ? rol : "INVITADO");

        return "verHabitaciones";
    }

    @PostMapping("/eliminar")
    public String eliminarHabitacion(@RequestParam String codigo,
                                     @RequestParam String hotelCodigo,
                                     HttpSession session,
                                     Model model
                                     //RedirectAttributes redirectAttributes
                                     ) {

        System.out.println("***************** ELIMINAR HABITACIÓN *******************");
        String rol = (String) session.getAttribute("rol");
        System.out.println("* rol: " + rol);
        String token = (String) session.getAttribute("jwt");
        System.out.println("* token: " + token);

//        if (!"ADMIN".equals(rol)) {
//            System.out.println("* No tienes permisos para eliminar el habitacion");
//            redirectAttributes.addFlashAttribute("error", "No tienes permisos para eliminar.");
//            return "redirect:/hoteles/buscar";
//        }

        try {
            System.out.println("* eliminando el habitacion...");
            habitacionService.eliminarHabitacion(codigo, token);
            //redirectAttributes.addFlashAttribute("msg", "Habitación eliminada correctamente.");
            model.addAttribute("msg", "Habitación eliminada correctamente.");
        } catch (Exception e) {
            System.out.println("* error: " + e.getMessage());
            //redirectAttributes.addFlashAttribute("error", "No se pudo eliminar la habitación.");
            model.addAttribute("error", "No se pudo eliminar la habitación.");
        }
        // Recarga las habitaciones directamente
        List<Habitacion> habitaciones = habitacionService.buscarHabitacionesPorHotel(hotelCodigo);
        model.addAttribute("habitaciones", habitaciones);
        model.addAttribute("hotelCodigo", hotelCodigo);
        //model.addAttribute("rol", rol != null ? rol : "INVITADO");

        System.out.println("* chimpún!!!!");
        //return "redirect:/habitaciones/"+hotelCodigo+"/buscar";
        return "verHabitaciones";
    }

}

