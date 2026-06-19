package com.isabelle.crud.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

//@ResponseStatus(HttpStatus.UNPROCESSABLE_CONTENT)
public class CompanyDeletionNotAllowedException extends RuntimeException {
    public CompanyDeletionNotAllowedException(String message) {
        super(message);
    }
}
