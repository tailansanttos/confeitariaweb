package com.tailan.confeitaria.web.infra.handle;

import com.tailan.confeitaria.web.infra.exception.ResourceNotFoundException;
import com.tailan.confeitaria.web.infra.exception.ResourceThisPresentException;
import com.tailan.confeitaria.web.services.dtos.api.ErrorResponseDTO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionGlobalHandle {
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handleResourceNotFoundException(ResourceNotFoundException ex,   HttpServletRequest request) {
        ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO(ex.getMessage(), "Resource Not Found", HttpStatus.NOT_FOUND.value(), request.getRequestURI());
        return new ResponseEntity<>(errorResponseDTO, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ResourceThisPresentException.class)
    public ResponseEntity<ErrorResponseDTO> handleResourceThisPresentException(ResourceThisPresentException ex,   HttpServletRequest request) {
        ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO(ex.getMessage(), "Resource is present.", HttpStatus.CONFLICT.value(), request.getRequestURI());
        return new ResponseEntity<>(errorResponseDTO, HttpStatus.CONFLICT);
    }
}
