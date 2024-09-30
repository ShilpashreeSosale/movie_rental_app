package com.emovie.movie_rental_management_system.customExceptions;

@SuppressWarnings("serial")
public class InvalidCustomerDataException extends RuntimeException {

    public InvalidCustomerDataException(String msg) {
        super(msg);
    }
}