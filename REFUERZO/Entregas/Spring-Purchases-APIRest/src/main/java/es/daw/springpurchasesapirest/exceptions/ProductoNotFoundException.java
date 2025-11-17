package es.daw.springpurchasesapirest.exceptions;

public class ProductoNotFoundException extends RuntimeException{
    public ProductoNotFoundException(String message) {
        super(message);
    }
}
