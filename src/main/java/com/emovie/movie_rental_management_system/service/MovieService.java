package com.emovie.movie_rental_management_system.service;

import com.emovie.movie_rental_management_system.entity.Movie;

import com.emovie.movie_rental_management_system.repository.MovieRepo;

import lombok.RequiredArgsConstructor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MovieService {
    private static final Logger logger = LoggerFactory.getLogger(MovieService.class);

    private final MovieRepo movieRepo;


    /***
     * Fetch available movies from the DB andprepare a mapping of MovieId and Movie
     */
    public Map<String, Movie> fetchAvailableMovies() {
        logger.info("Fetch Available Movies :: ");

        // Fetch all the available movie details from the DB and prepare a map of code of the movie and movie object
        Map<String, Movie> movies = movieRepo.findAll().stream()
                .collect(
                        Collectors.toMap(
                                Movie::getMovieId,
                                Function.identity(),
                                (firstValue, nextValue) -> firstValue));
        return movies;
    }

}
