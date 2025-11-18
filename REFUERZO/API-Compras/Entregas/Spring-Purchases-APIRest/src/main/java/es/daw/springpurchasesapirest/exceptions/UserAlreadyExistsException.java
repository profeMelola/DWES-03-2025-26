package es.daw.springpurchasesapirest.exceptions;

import javax.naming.AuthenticationException;

public class UserAlreadyExistsException extends AuthenticationException {
    public UserAlreadyExistsException(String message) {
        super(message);
    }
}
