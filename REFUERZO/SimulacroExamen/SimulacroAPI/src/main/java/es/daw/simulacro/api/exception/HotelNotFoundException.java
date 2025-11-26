package es.daw.simulacro.api.exception;

public class HotelNotFoundException extends RuntimeException {
    public HotelNotFoundException(String codigo) {
        super("Hotel no encontrado con código: " + codigo);
    }
}

