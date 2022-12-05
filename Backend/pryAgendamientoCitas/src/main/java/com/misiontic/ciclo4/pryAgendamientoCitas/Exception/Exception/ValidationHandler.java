package com.misiontic.ciclo4.pryAgendamientoCitas.Exception.Exception;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.validation.FieldError;

import com.misiontic.ciclo4.pryAgendamientoCitas.Exception.Entity.ErrorResponse;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.misiontic.ciclo4.pryAgendamientoCitas.Exception.Entity.Error;

@RestControllerAdvice
public class ValidationHandler {
    @ExceptionHandler
    protected ResponseEntity<ErrorResponse>  handledException(InvalidDataException ex){
        List<Error> listError = new ArrayList<>();
        ex.getResult().getAllErrors().forEach((err)->{
            Error error = new Error();
            error.setField(((FieldError) err).getField());
            error.setMessage(err.getDefaultMessage());
            listError.add(error);
        });
        return new ResponseEntity<>(new ErrorResponse(400, "Error de Data", new Date(), listError),HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler 
    protected ResponseEntity<ErrorResponse> handledException(InvalidFormatException ex){
        Error error = new Error("Dato incorrecto", "El dato documento solo debe contener numeros");
        List<Error> listError = new ArrayList<>();
        listError.add(error);
        return new ResponseEntity<>(new ErrorResponse(404, "Datos", new Date(), listError),HttpStatus.NOT_FOUND);
    }

}
