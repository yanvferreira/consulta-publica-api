package br.jus.tjrn.consulta.shared.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import br.jus.tjrn.consulta.shared.response.ApiResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiResponse<Void>> handleBussiness(IllegalArgumentException ex) {

        return ResponseEntity.badRequest().body(
            ApiResponse.<Void>builder()
                .success(false)
                .message(ex.getMessage())
                .data(null)
                .build()   
        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleGeneric(Exception ex) {

        return ResponseEntity.internalServerError().body(
            ApiResponse.<Void>builder()
                .success(false)
                .message("Erro interno no servidor.")
                .data(null)
                .build()
        );
    }

}
