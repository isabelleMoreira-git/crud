package com.isabelle.crud.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

//@ResponseStatus(value = HttpStatus.UNPROCESSABLE_CONTENT)
public class CustomerAlreadyExistsException extends RuntimeException{

    public CustomerAlreadyExistsException (String message){
        super(message);
    }
}
// todo: Alterar ela pra 422.
// todo: Usar Try-Catch