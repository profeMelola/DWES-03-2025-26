package es.daw.simulacro.api.exception;

public class CategoriaNotFoundException extends RuntimeException {
    public CategoriaNotFoundException(String codigo) {
        super("Categoría no encontrada con código: " + codigo);
    }
}

