package com.emovie.movie_rental_management_system.service;

import com.emovie.movie_rental_management_system.constants.Constants;
import com.emovie.movie_rental_management_system.customExceptions.InvalidCustomerDataException;
import com.emovie.movie_rental_management_system.customExceptions.InvalidMovieDataException;
import com.emovie.movie_rental_management_system.customExceptions.InvalidRentalPeriodException;
import com.emovie.movie_rental_management_system.entity.Customer;
import com.emovie.movie_rental_management_system.entity.Movie;
import com.emovie.movie_rental_management_system.entity.MovieRental;

import com.emovie.movie_rental_management_system.model.CustomerDTO;
import com.emovie.movie_rental_management_system.model.MovieRentalDTO;
import com.emovie.movie_rental_management_system.repository.CustomerRepo;
import com.emovie.movie_rental_management_system.repository.MovieRepo;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private static final Logger logger = LoggerFactory.getLogger(CustomerService.class);

    private final CustomerRepo customerRepo;
    private final MovieRepo movieRepo;

    /***
     * //Check if the customer id provide in the request, exists in our DB to make sure the customer is valid.
     */
    public CustomerDTO fetchCustomerById(String customerId) {
        logger.info("fetchCustomerById for the customer " + customerId);

        Customer customer =
                customerRepo
                        .findById(customerId)
                        .orElseThrow(
                                () -> new InvalidCustomerDataException(
                                        Constants.INVALID_CUSTOMER_ID_ERROR + customerId));
        return buildCustomerData(customer);
    }

    /**
     * Method to convert entity object to DTO object
     */
    private CustomerDTO buildCustomerData(Customer customer) {
        logger.info("buildCustomerData");

        // Check if the customer from the DB is null
        if (customer == null) {
            return null;
        }

        // create Customer DTO object and populate the data from the entity to DTO
        CustomerDTO customerDto = new CustomerDTO();
        customerDto.setCustomerName(customer.getCustomerName());
        customerDto.setCustomerId(customer.getCustomerId());
        if (customer.getRentals() != null && !customer.getRentals().isEmpty()) {
            List<MovieRentalDTO> custMovieRentalInfo = new ArrayList<MovieRentalDTO>();
            for (MovieRental rental : customer.getRentals()) {
                MovieRentalDTO movieRentalDTO = new MovieRentalDTO();
                movieRentalDTO.setRentalPeriod(rental.getRentalPeriod());
                movieRentalDTO.setMovieId(rental.getMovieId());
                custMovieRentalInfo.add(movieRentalDTO);
            }
            customerDto.setRentals(custMovieRentalInfo);
        }
        return customerDto;
    }

    /**
     * Check if the Customer exists, if the customer id and rental infomations are correct
     */
    public boolean validateCustomerData(CustomerDTO customerRequest, CustomerDTO customerDtoFromDB) {
        logger.info("validateCustomerData");

        boolean validCustomerAndRentalInfo = false;
        try {
            if (customerDtoFromDB != null) {

                // Get all the Available movies from the DB
                List<Movie> movies = movieRepo.findAll();

                if (customerRequest.getRentals() != null && !customerRequest.getRentals().isEmpty()) {

                    // ceck if the provided mavie id is valid
                    boolean validMovieId = validateCustomerMovies(customerRequest.getRentals(), movies.stream().map(Movie::getMovieId).collect(Collectors.toList()));

                    // Check if the rental period is valid
                    boolean validRentalPeriod = validateRentalPeriod(customerRequest.getRentals());

                    if (validMovieId && validRentalPeriod) validCustomerAndRentalInfo = true;
                }
            }
        } catch (InvalidCustomerDataException invalidCustomerDataException) {
            logger.info(invalidCustomerDataException.getMessage());
        }
        return validCustomerAndRentalInfo;
    }

    /**
     * Validate Rental days return false if rental period is less than or equals to 0 else return false
     */
    public static boolean validateRentalPeriod(List<MovieRentalDTO> rentalDetails) {
        logger.info("validateRentalPeriod");
        try {
            return rentalDetails.stream()
                    .allMatch(rentalData -> {
                        if (rentalData.getRentalPeriod() <= 0) {
                            throw new InvalidRentalPeriodException(Constants.INVALID_RENT_DAYS_ERROR);
                        }
                        return rentalData.getRentalPeriod() > 0;
                    });
        } catch (InvalidRentalPeriodException invalidRentalPeriodException) {
            logger.info(invalidRentalPeriodException.getMessage());
            return false;
        }
    }

    /**
     * Method to validate customer rental movie IDs. returns true if the movie ids are valid else returns false
     */
    public static boolean validateCustomerMovies(List<MovieRentalDTO> rentalDetails, List<String> validMovieIds) {
        logger.info("Validate Customer Movies :: ");
        try {
            rentalDetails.stream()
                    .forEach(rental -> {
                        if (!validMovieIds.contains(rental.getMovieId())) {
                            throw new InvalidMovieDataException(Constants.INVALID_MOVIEID_ERROR);
                        }
                    });
            return true;
        } catch (InvalidMovieDataException invalidMovieDataException) {
            logger.info(invalidMovieDataException.getMessage());
            return false;
        }
    }

}
