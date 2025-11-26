package es.daw.simulacro.api.controller;

import es.daw.simulacro.api.exception.CategoriaNotFoundException;
import es.daw.simulacro.api.exception.HabitacionNotFoundException;
import es.daw.simulacro.api.exception.HotelNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(HotelNotFoundException.class)
    public ResponseEntity<?> handleHotelNotFound(HotelNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error(ex.getMessage()));
    }

    @ExceptionHandler(HabitacionNotFoundException.class)
    public ResponseEntity<?> handleHabitacionNotFound(HabitacionNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error(ex.getMessage()));
    }

    @ExceptionHandler(CategoriaNotFoundException.class)
    public ResponseEntity<?> handleCategoriaNotFound(CategoriaNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error(ex.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGenerico(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error("Error interno: " + ex.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleMethodArgumentNotValid(MethodArgumentNotValidException e) {
        //String message = e.getBindingResult().getAllErrors().get(0).getDefaultMessage();
        String message = e.getBindingResult().getFieldError().getDefaultMessage();

//        ExceptionDTO error = new ExceptionDTO(
//                HttpStatus.BAD_REQUEST.value(),
//                "Error de validación",
//                message,
//                LocalDateTime.now()
//        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error(message));

    }

    private Map<String, Object> error(String mensaje) {
        return Map.of(
                "error", mensaje,
                "timestamp", LocalDateTime.now()
        );
    }
}

