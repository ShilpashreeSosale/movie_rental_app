package com.emovie.movie_rental_management_system.customExceptions;

@SuppressWarnings("serial")
public class InvalidMovieDataException extends RuntimeException {

    public InvalidMovieDataException(String msg) {
        super(msg);
    }
}