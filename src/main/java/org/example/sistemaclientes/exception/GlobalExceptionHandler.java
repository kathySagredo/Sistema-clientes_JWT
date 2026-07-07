package org.example.sistemaclientes.exception;


import org.example.sistemaclientes.dto.ErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RecursoNoEncontradoException.class)
    public ResponseEntity<ErrorResponse> manejarNoEncontrado(RecursoNoEncontradoException ex){
        return ResponseEntity.status(404).body(new ErrorResponse(
                LocalDateTime.now(), 404, ex.getMessage()
        ));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> manejarValidacion(MethodArgumentNotValidException ex){
        return ResponseEntity.badRequest().body(new ErrorResponse(
                LocalDateTime.now(), 400, "Datos invalidos"
        ));
    }
}
