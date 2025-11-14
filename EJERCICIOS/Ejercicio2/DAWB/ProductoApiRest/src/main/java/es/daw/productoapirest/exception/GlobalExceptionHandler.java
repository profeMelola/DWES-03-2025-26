package es.daw.productoapirest.exception;

import jakarta.validation.ConstraintViolationException;
import org.apache.coyote.Response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorDTO> handleConstraintViolationException(ConstraintViolationException ex) {
        ErrorDTO errorDTO = new ErrorDTO();
        errorDTO.setMessage("Error de constraint violation!!!! torpedo!!!");
        errorDTO.setTimestamp(LocalDateTime.now());

        Map<String, String> errores = new HashMap<>();

        ex.getConstraintViolations().forEach(cv -> {
            String campo = cv.getPropertyPath().toString();
            String valor = cv.getMessage();
            errores.put(campo, valor);
        });

        errorDTO.setDetails(errores);

        return new ResponseEntity<>(errorDTO, HttpStatus.BAD_REQUEST);

    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDTO> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex){

//        List<String> errores = ex.getBindingResult()
//                .getFieldErrors()
//                .stream()
//                .map( error -> error.getField()+": "+error.getDefaultMessage())
//                .toList();

        Map<String,String> errores = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .collect(Collectors.toMap(
                        error -> error.getField(),
                          error -> error.getDefaultMessage()
                ));


//        Map<String,String> errores2 = new HashMap<>();
//        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
//        for (FieldError fieldError : fieldErrors) {
//            errores.put(fieldError.getField(),fieldError.getDefaultMessage());
//        }

        ErrorDTO errorDTO = new ErrorDTO(
                "Error de validación",
                LocalDateTime.now(),
                //String.join(";",errores)
                errores
        );

        //return new ResponseEntity<>(errorDTO, HttpStatus.BAD_REQUEST);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDTO);

    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDTO> handleException(Exception ex){
        ErrorDTO errorDTO = new ErrorDTO();
        errorDTO.setMessage("Pedazo de error genérico");
        errorDTO.setTimestamp(LocalDateTime.now());

        Map<String,String> error = new HashMap<>();
        error.put("message", ex.getMessage());

        errorDTO.setDetails(error);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorDTO); //500

    }

    @ExceptionHandler(NumberFormatException.class)
    public ResponseEntity<ErrorDTO> handleNumberFormatException(NumberFormatException ex) {
        ErrorDTO error = new ErrorDTO(
                "Invalid number format... torpedo!",
                LocalDateTime.now(),
                new HashMap<>()
        );
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ProductoNotFoundException.class)
    public ResponseEntity<ErrorDTO> handleProductoNotFoundException(ProductoNotFoundException ex) {

        // 1. crear el objeto ErrorDTO con toda la info

        ErrorDTO errorDTO = ErrorDTO.builder()
                .message("PRODUCTO NO ENCONTRADO")
                .timestamp(LocalDateTime.now())
                .details(
                        Map.of("exception", ex.getClass().getSimpleName(),
                                "message", ex.getMessage()
                        )

                )
                .build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorDTO);

    }
}
