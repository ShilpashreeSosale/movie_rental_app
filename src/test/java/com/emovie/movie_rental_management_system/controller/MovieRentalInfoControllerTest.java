package com.emovie.movie_rental_management_system.controller;

import com.emovie.movie_rental_management_system.constants.Constants;
import com.emovie.movie_rental_management_system.customExceptions.InvalidCustomerDataException;
import com.emovie.movie_rental_management_system.model.CustomerDTO;
import com.emovie.movie_rental_management_system.model.MovieRentalDTO;
import com.emovie.movie_rental_management_system.service.MovieRentalInfoService;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@WebMvcTest(MovieRentalInfoController.class)
public class MovieRentalInfoControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MovieRentalInfoService movieRentalInfoService;

    private CustomerDTO validCustomer;
    private CustomerDTO invalidCustomer;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    public void setUp() {
        //Valid Customer
        validCustomer = new CustomerDTO();
        validCustomer.setCustomerName("C. U. Stomer");
        validCustomer.setCustomerId("CUStomer");

        List<MovieRentalDTO> rentals = new ArrayList<MovieRentalDTO>();

        MovieRentalDTO movieRental1 = new MovieRentalDTO();
        movieRental1.setMovieId("F001");
        movieRental1.setRentalPeriod(3);

        MovieRentalDTO movieRental2 = new MovieRentalDTO();
        movieRental2.setMovieId("F002");
        movieRental2.setRentalPeriod(1);

        rentals.add(movieRental1);
        rentals.add(movieRental2);
        validCustomer.setRentals(rentals);

        //InValid Customer
        invalidCustomer = new CustomerDTO();
        invalidCustomer.setCustomerName("C. U. Stomer");
        invalidCustomer.setRentals(rentals);
    }

    @Test
    public void testGetMovieRentalInfoWithValidCustomerData() throws Exception {
        String expectedResponse = "Rental Record for C. U. Stomer\n\tYou've Got Mail\t3.5\n\tMatrix\t2.0\nAmount owed is 5.5\nYou earned 2 frequent points\n";
        when(movieRentalInfoService.rentalMoviesInfoStatement(any())).thenReturn(expectedResponse);

        this.mockMvc.perform(post("/customer/movie/rentalInfo", validCustomer)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validCustomer)))
                .andExpect(status().isOk())
                .andExpect(content().string(expectedResponse));
    }

    @Test
    public void testGetMovieRentalInfoWithInvalidCustomer() throws Exception {
        when(movieRentalInfoService.rentalMoviesInfoStatement(any()))
                .thenThrow(new InvalidCustomerDataException(Constants.INVALID_CUSTOMER_DATA_ERROR));

        String errorResponse = mockMvc.perform(post("/customer/movie/rentalInfo", invalidCustomer)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidCustomer)))
                .andExpect(status().isBadRequest())
                .andReturn()
                .getResponse().getContentAsString();
        assertTrue(errorResponse.contains(Constants.INVALID_CUSTOMER_DATA_ERROR));
    }
}
