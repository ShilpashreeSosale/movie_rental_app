package com.emovie.movie_rental_management_system.controller;

import com.emovie.movie_rental_management_system.model.CustomerDTO;

import com.emovie.movie_rental_management_system.service.MovieRentalInfoService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("customer/movie")
public class MovieRentalInfoController {
    private static final Logger logger = LoggerFactory.getLogger(MovieRentalInfoController.class);
    private final MovieRentalInfoService movieRentalInfoService;

    @Autowired
    public MovieRentalInfoController(MovieRentalInfoService movieRentalInfoService) {
        this.movieRentalInfoService = movieRentalInfoService;
    }

    /**
     * Returns Movie rental information for a customer.
     *
     * @return rental info of that customer.
     * @Param The customer for whom rental info is being requested.
     */
    @PostMapping("/rentalInfo")
    public ResponseEntity<String> getMovieRentalInfo(@RequestBody CustomerDTO customer) {
        logger.info("Get movie rental info for the customer: {}", customer.getCustomerName());
        String rentalInfo = movieRentalInfoService.rentalMoviesInfoStatement(customer);
        return ResponseEntity.ok(rentalInfo);
    }

}
