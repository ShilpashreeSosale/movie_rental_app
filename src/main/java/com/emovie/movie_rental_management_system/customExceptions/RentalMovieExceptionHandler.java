package com.emovie.movie_rental_management_system.customExceptions;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class RentalMovieExceptionHandler {
    @ExceptionHandler(InvalidCustomerDataException.class)
    public ResponseEntity<ErrorResponse> handleCustomerNotFoundException(InvalidCustomerDataException e) {
        return buildErrorResponseEntity(e, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidMovieDataException.class)
    public ResponseEntity<ErrorResponse> handleMovieNotFoundException(InvalidMovieDataException e) {
        return buildErrorResponseEntity(e, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidRentalPeriodException.class)
    public ResponseEntity<ErrorResponse> handleInvalidDaysException(InvalidRentalPeriodException e) {
        return buildErrorResponseEntity(e, HttpStatus.BAD_REQUEST);
    }

    private ResponseEntity<ErrorResponse> buildErrorResponseEntity(
            Exception exception, HttpStatus httpStatus) {
        ErrorResponse errorResponse =
                new ErrorResponse(LocalDateTime.now(), exception.getMessage());
        return new ResponseEntity<>(errorResponse, httpStatus);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse> handleRuntimeException(
            RuntimeException e, HttpServletRequest request) {
        return buildErrorResponseEntity(e, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
