package com.emovie.movie_rental_management_system.model;

import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDTO {
    private String customerId;
    private String customerName;
    private List<MovieRentalDTO> rentals;
}
