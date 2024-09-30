package com.emovie.movie_rental_management_system.service;

import com.emovie.movie_rental_management_system.customExceptions.InvalidCustomerDataException;
import com.emovie.movie_rental_management_system.entity.Customer;
import com.emovie.movie_rental_management_system.entity.Movie;
import com.emovie.movie_rental_management_system.entity.MovieRental;
import com.emovie.movie_rental_management_system.model.CustomerDTO;
import com.emovie.movie_rental_management_system.model.MovieRentalDTO;
import com.emovie.movie_rental_management_system.repository.CustomerRepo;
import com.emovie.movie_rental_management_system.repository.MovieRepo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = {CustomerService.class})
public class CustomerServiceTest {

    @SpyBean
    CustomerService customerService;
    @MockBean
    CustomerRepo custRepo;

    @MockBean
    MovieRepo movieRepo;

    @BeforeEach
    public void setUp() {
        List<Movie> movies = Arrays.asList(new Movie("F001", "Matrix", "regular"), new Movie("F002", "Cars", "Children"));
        List<MovieRentalDTO> rentalsDto = Arrays.asList(new MovieRentalDTO("F001", 5));


        Customer customer = new Customer();
        customer.setCustomerName("C. U. Stomer");
        customer.setCustomerId("CUStomer");

        List<MovieRental> rentals = new ArrayList<MovieRental>();

        MovieRental movieRental1 = new MovieRental();
        movieRental1.setMovieId("F001");
        movieRental1.setRentalPeriod(3);

        MovieRental movieRental2 = new MovieRental();
        movieRental2.setMovieId("F002");
        movieRental2.setRentalPeriod(3);

        rentals.add(movieRental1);
        rentals.add(movieRental2);
        customer.setRentals(rentals);

        when(custRepo.findById(anyString())).thenReturn(Optional.of(customer));

        CustomerDTO custDTO = new CustomerDTO();
        custDTO.setCustomerId("AMaria");
        custDTO.setCustomerName("A.Maria");
        custDTO.setRentals(rentalsDto);

        when(movieRepo.findAll()).thenReturn(movies);
        when(customerService.fetchCustomerById("AMaria")).thenReturn(custDTO);
    }

    @Test
    void testFetchCustomerById() {
        CustomerDTO custDTO = customerService.fetchCustomerById("CUStomer");

        assertEquals("C. U. Stomer", custDTO.getCustomerName());
        assertEquals(2, custDTO.getRentals().size());
    }

    @Test
    void testCustomerNotFound() {
        when(custRepo.findById(anyString())).thenReturn(Optional.empty());

        assertThrows(InvalidCustomerDataException.class, () -> customerService.fetchCustomerById("CUStomer"));
    }

    @Test
    void testValidateCustomerMoviesWithValidData() {
        List<MovieRentalDTO> rentalDetails = Arrays.asList(
                new MovieRentalDTO("F001", 3),  // All valid IDs
                new MovieRentalDTO("F002", 1)
        );

        List<String> validMovieIds = Arrays.asList("F001", "F002");

        boolean result = customerService.validateCustomerMovies(rentalDetails, validMovieIds);
        assertTrue(result);
    }

    @Test
    void testValidateCustomerMoviesWithInValidData() {
        List<MovieRentalDTO> rentalDetails = Arrays.asList(
                new MovieRentalDTO("F003", 3),  // All valid IDs
                new MovieRentalDTO("F004", 1)
        );

        List<String> validMovieIds = Arrays.asList("F001", "F002");

        boolean result = customerService.validateCustomerMovies(rentalDetails, validMovieIds);
        assertFalse(result);
    }

    @Test
    void testValidateRentalPeriodWithValidData() {
        List<MovieRentalDTO> rentalDetails = Arrays.asList(
                new MovieRentalDTO("F001", 3),
                new MovieRentalDTO("Foo2", 1)
        );

        boolean result = customerService.validateRentalPeriod(rentalDetails);

        assertTrue(result);
    }

    @Test
    void testValidateRentalPeriodWithZeroDay() {
        List<MovieRentalDTO> rentalDetails = Arrays.asList(
                new MovieRentalDTO("F001", 0),
                new MovieRentalDTO("Foo2", 1)
        );

        boolean result = customerService.validateRentalPeriod(rentalDetails);

        assertFalse(result);
    }

    @Test
    void testValidateRentalPeriodWithNegativeValue() {
        List<MovieRentalDTO> rentalDetails = Arrays.asList(
                new MovieRentalDTO("F001", 10),
                new MovieRentalDTO("Foo2", -1)
        );

        boolean result = customerService.validateRentalPeriod(rentalDetails);

        assertFalse(result);
    }

    @Test
    void testValidateCustomerDataWithValidData() {
        List<MovieRentalDTO> rentalsDto = Arrays.asList(new MovieRentalDTO("F001", 5));
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setCustomerId("CUStomer");
        customerDTO.setCustomerName("C. U. Stomer");
        customerDTO.setRentals(rentalsDto);

        assertTrue(customerService.validateCustomerData(customerDTO, customerDTO));
    }

    @Test
    void testValidateCustomerDataWithInValidData() {
        List<MovieRentalDTO> rentalsDto = Arrays.asList(new MovieRentalDTO("F001", 5));
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setCustomerId("");
        customerDTO.setCustomerName("C. U. Stomer");
        customerDTO.setRentals(rentalsDto);

        assertFalse(customerService.validateCustomerData(customerDTO, null));
    }

    @Test
    void testValidateCustomerDataWithInValidRentalData() {
        List<MovieRentalDTO> rentalsDto = Arrays.asList(new MovieRentalDTO("F001", -5));
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setCustomerId("CUStomer");
        customerDTO.setCustomerName("C. U. Stomer");
        customerDTO.setRentals(rentalsDto);

        assertFalse(customerService.validateCustomerData(customerDTO, customerDTO));
    }

    @Test
    void testValidateCustomerDataWithInValidMovieData() {
        List<MovieRentalDTO> rentalsDto = Arrays.asList(new MovieRentalDTO("F006", 5));
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setCustomerId("CUStomer");
        customerDTO.setCustomerName("C. U. Stomer");
        customerDTO.setRentals(rentalsDto);

        assertFalse(customerService.validateCustomerData(customerDTO, customerDTO));
    }
}
