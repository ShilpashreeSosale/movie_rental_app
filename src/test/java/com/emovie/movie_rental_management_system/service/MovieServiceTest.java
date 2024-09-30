package com.emovie.movie_rental_management_system.service;

import com.emovie.movie_rental_management_system.service.MovieService;
import com.emovie.movie_rental_management_system.entity.Movie;
import com.emovie.movie_rental_management_system.repository.MovieRepo;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;

@SpringBootTest(classes = {MovieService.class})
public class MovieServiceTest {
    @SpyBean
    MovieService movieService;
    @MockBean
    MovieRepo movieRepo;

    @Test
    void testfetchAvailableMovies() {
        // mock test data
        Movie movie1 = new Movie("F001", "You've Got Mail", "regular");
        Movie movie2 = new Movie("F002", "Matrix", "regular");
        Movie movie3 = new Movie("F003", "Cars", "childrens");
        Movie movie4 = new Movie("F004", "Fast & Furious X", "new");

        List<Movie> movieList = Arrays.asList(movie1, movie2, movie3, movie4);

        // Mocking Repo call with the mock data
        when(movieRepo.findAll()).thenReturn(movieList);

        // Calling the actual service method to be tested
        Map<String, Movie> moviesMap = movieService.fetchAvailableMovies();

        // Map should not be null
        assertNotNull(moviesMap);

        // There should be 4 entries in the map
        assertEquals(4, moviesMap.size());

        // Ensure each movie is correctly mapped
        assertEquals(movie1, moviesMap.get("F001"));
        assertEquals(movie4, moviesMap.get("F004"));
    }

    @Test
    void testfetchAvailableMoviesWithEmptyList() {
        // Mocking Repo call with the mock data
        when(movieRepo.findAll()).thenReturn(Arrays.asList());

        // Calling the actual service method to be tested
        Map<String, Movie> moviesMap = movieService.fetchAvailableMovies();

        //Map should not be null
        assertNotNull(moviesMap);
        // Map should be empty
        assertTrue(moviesMap.isEmpty());
    }

}
