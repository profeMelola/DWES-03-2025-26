package es.daw.simulacro.api.exception;

public class HabitacionNotFoundException extends RuntimeException {
    public HabitacionNotFoundException(String codigo) {
        super("Habitación no encontrada con código: " + codigo);
    }
}

