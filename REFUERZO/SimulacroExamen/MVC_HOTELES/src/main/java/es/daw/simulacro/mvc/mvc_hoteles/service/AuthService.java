package es.daw.simulacro.mvc.mvc_hoteles.service;

import es.daw.simulacro.mvc.mvc_hoteles.dto.AuthRequestDTO;
import es.daw.simulacro.mvc.mvc_hoteles.dto.AuthResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final WebClient webClientAuth;

    // PENDIENTE GESTIONAR EXCEPCIÓN SI HAY PROBLEMAS AL CONECTAR AL API REST (WebClientRequestException)
    // Crear excepción propia ConnectApiRestException... (creo y propago)
    // Implementar un @ControllerAdvice (@ExceptionHandler de dicha excepción)
    public String obtenerToken(String username, String password) {
        System.out.println("**************");
        System.out.println("login: " + username);
        System.out.println("password: " + password);
        System.out.println("**************");

        try {
            AuthRequestDTO request = new AuthRequestDTO(username, password);
            AuthResponseDTO response = webClientAuth.post()
                    .uri("/auth/login")
                    .header("Content-Type", "application/json")
                    .bodyValue(request)
                    .retrieve()
                    .bodyToMono(AuthResponseDTO.class)
                    .block();

            return response.getToken();
        }catch (WebClientResponseException e){
            System.out.println("************** ERROR DE LOGIN ****************");
            System.out.println(e.getMessage());
            System.out.println("**********************************************");
            return "ERROR: "+e.getMessage();
        }catch (Exception e){
            return "ERROR: No se pudo conectar al servidor de autenticación";
        }

    }

}
