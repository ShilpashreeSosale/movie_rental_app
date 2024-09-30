package com.emovie.movie_rental_management_system.customExceptions;

@SuppressWarnings("serial")
public class InvalidRentalPeriodException extends RuntimeException {

    public InvalidRentalPeriodException(String msg) {
        super(msg);
    }
}