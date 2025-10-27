package es.daw.productoapirest.exception;

import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDTO> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex){

        List<String> errores = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map( error -> error.getField()+": "+error.getDefaultMessage())
                .toList();

        ErrorDTO errorDTO = new ErrorDTO(
                "Error de validación",
                LocalDateTime.now(),
                String.join(";",errores)
        );

        //return new ResponseEntity<>(errorDTO, HttpStatus.BAD_REQUEST);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDTO);

    }
}
