package com.emovie.movie_rental_management_system.repository;

import com.emovie.movie_rental_management_system.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MovieRepo extends JpaRepository<Movie, String> {

    Optional<Movie> findByMovieId(String movieId);
}
