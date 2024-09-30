package com.emovie.movie_rental_management_system.service;

import com.emovie.movie_rental_management_system.customExceptions.InvalidCustomerDataException;
import com.emovie.movie_rental_management_system.entity.Movie;
import com.emovie.movie_rental_management_system.model.CustomerDTO;
import com.emovie.movie_rental_management_system.model.MovieRentalDTO;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = {MovieRentalInfoService.class})
public class RentalInfoServiceTest {
    @SpyBean
    MovieRentalInfoService movieRentalInfoService;

    @MockBean
    MovieService movieService;

    @MockBean
    CustomerService customerService;

    @BeforeEach
    public void setUp() {
        Map<String, Movie> movies = new HashMap<String, Movie>();
        movies.put("F001", new Movie("F001", "You've Got Mail", "regular"));
        movies.put("F002", new Movie("F002", "Matrix", "regular"));
        movies.put("F003", new Movie("F003", "Cars", "childrens"));
        movies.put("F004", new Movie("F004", "Fast & Furious X", "new"));

        when(movieService.fetchAvailableMovies()).thenReturn(movies);
    }

    @Test
    void testCalculateMovieRent() {
        CustomerDTO customerFromDB = new CustomerDTO();
        customerFromDB.setCustomerName("C. U. Stomer");
        customerFromDB.setCustomerId("CUStomer");

        when(customerService.fetchCustomerById("CUStomer")).thenReturn(customerFromDB);

        CustomerDTO customerRequest = new CustomerDTO();
        customerRequest.setCustomerName("C. U. Stomer");
        customerRequest.setCustomerId("CUStomer");

        List<MovieRentalDTO> rentals = new ArrayList<MovieRentalDTO>();

        MovieRentalDTO movieRental1 = new MovieRentalDTO();
        movieRental1.setMovieId("F001");
        movieRental1.setRentalPeriod(3);

        MovieRentalDTO movieRental2 = new MovieRentalDTO();
        movieRental2.setMovieId("F002");
        movieRental2.setRentalPeriod(1);

        rentals.add(movieRental1);
        rentals.add(movieRental2);
        customerRequest.setRentals(rentals);

        when(customerService.validateCustomerData(any(), any())).thenReturn(true);

        String actualStatement = movieRentalInfoService.rentalMoviesInfoStatement(customerRequest);

        String ExpectedStatement = "Rental Record for C. U. Stomer\n\tYou've Got Mail\t3.5\n\tMatrix\t2.0\nAmount owed is 5.5\nYou earned 2 frequent points\n";

        assertEquals(ExpectedStatement, actualStatement);
    }

    @Test
    void testCalculateMovieRentWithInvalidCustDetails() {
        CustomerDTO customer = new CustomerDTO();
        customer.setCustomerName("C. U. Stomer");
        customer.setCustomerId("CUStomer");
        when(customerService.fetchCustomerById("CUStomer")).thenReturn(customer);

        when(customerService.validateCustomerData(any(), any())).thenReturn(false);

        assertThrows(InvalidCustomerDataException.class, () -> movieRentalInfoService.rentalMoviesInfoStatement(customer));
    }

}

