package com.emovie.movie_rental_management_system.service;

import com.emovie.movie_rental_management_system.constants.Constants;

import com.emovie.movie_rental_management_system.customExceptions.InvalidCustomerDataException;

import com.emovie.movie_rental_management_system.entity.Movie;
import com.emovie.movie_rental_management_system.model.CustomerDTO;
import com.emovie.movie_rental_management_system.model.MovieRentalDTO;
import lombok.RequiredArgsConstructor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class MovieRentalInfoService {
    private static final Logger logger = LoggerFactory.getLogger(MovieRentalInfoService.class);

    private final CustomerService customerservice;
    private final MovieService movieService;

    /**
     * Get Rental movies Information statement
     */
    public String rentalMoviesInfoStatement(CustomerDTO customerRequest) {
        logger.info("RentalMoviesInfoStatement for the customer :: " + customerRequest);

        double totalRentalAmount = 0;
        int frequentEnterPoints = 0;

        String rentalMoviesInfoStatement = "";

        try {
            if (customerRequest != null && customerRequest.getCustomerId() != null && !customerRequest.getCustomerId().isEmpty()) {

                CustomerDTO customerFromDB = customerservice.fetchCustomerById(customerRequest.getCustomerId());
                // Check if the provided Customer details are valid before calculating the rent
                if (customerservice.validateCustomerData(customerRequest, customerFromDB)) {

                    // Fetch movies map from DB
                    Map<String, Movie> movies = movieService.fetchAvailableMovies();

                    rentalMoviesInfoStatement = Constants.RENTAL_RECORD_FOR + customerFromDB.getCustomerName() + "\n";

                    // for each movie rented by the customer check the movie code and based on that calculate the rent
                    for (MovieRentalDTO movieRented : customerRequest.getRentals()) {
                        double thisMovieAmount = 0;

                        Movie movie = movies.get(movieRented.getMovieId());

                        int rentDays = movieRented.getRentalPeriod();

                        // Calculate the movie rent based on the Movie information and rental period
                        thisMovieAmount = calculateMovieRent(movie, rentDays);

                        //add frequent bonus points
                        frequentEnterPoints++;

                        // add bonus for two day new release rental
                        if (movie.getMovieCode().equalsIgnoreCase(Constants.NEW) && movieRented.getRentalPeriod() > 2)
                            frequentEnterPoints++;

                        //print figures for this rental
                        rentalMoviesInfoStatement += "\t" + movies.get(movieRented.getMovieId()).getMovieTitle() + "\t" + thisMovieAmount + "\n";

                        totalRentalAmount = totalRentalAmount + thisMovieAmount;
                    }
                } else {
                    throw new InvalidCustomerDataException(Constants.INVALID_CUSTOMER_DATA_ERROR);
                }
            } else {
                throw new InvalidCustomerDataException(Constants.INVALID_CUSTOMER_ID_ERROR);
            }
        } catch (InvalidCustomerDataException invalidCustomerDataException) {
            throw new InvalidCustomerDataException(invalidCustomerDataException.getMessage());
        }
        // add footer lines
        rentalMoviesInfoStatement += Constants.AMOUNT_OWED_IS + totalRentalAmount + "\n" + Constants.YOU_EARNED + frequentEnterPoints + Constants.FREQUENCY_POINTS;

        return rentalMoviesInfoStatement;
    }

    /**
     * Calculate Rent
     */
    private double calculateMovieRent(Movie movie, int days) {
        logger.info("Calculate Movie Rent :: " + movie, +days);
        double movieRent = 0;

        if (movie.getMovieCode().equalsIgnoreCase(Constants.REGULAR)) {
            movieRent = 2;

            if (days > 2) {
                movieRent = ((days - 2) * 1.5) + movieRent;
            }
        } else if (movie.getMovieCode().equalsIgnoreCase(Constants.NEW)) {
            movieRent = days * 3;
        } else if (movie.getMovieCode().equalsIgnoreCase(Constants.CHILDRENS)) {
            movieRent = 1.5;

            if (days > 3) {
                movieRent = ((days - 3) * 1.5) + movieRent;
            }
        }
        return movieRent;
    }
}

