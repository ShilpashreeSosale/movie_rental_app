package com.emovie.movie_rental_management_system.model;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MovieDTO {
    private String movieId;
    private String movieTitle;
    private String movieCode;
}
