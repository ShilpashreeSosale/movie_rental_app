package com.emovie.movie_rental_management_system.repository;

import com.emovie.movie_rental_management_system.entity.MovieRental;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieRentalRepo extends JpaRepository<MovieRental, Long> {
}
