package com.emovie.movie_rental_management_system.model;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MovieRentalDTO {
    private String movieId;
    private int rentalPeriod;
}
