package com.emovie.movie_rental_management_system.entity;

import jakarta.persistence.*;

import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Getter;

import java.io.Serial;
import java.io.Serializable;

@Entity
@Table(name = "Movie_Rental")
@NamedQuery(name = "MovieRental.findAll", query = "SELECT mr FROM MovieRental mr")
@Getter
@Setter
@NoArgsConstructor
public class MovieRental extends Audit implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "rental_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MOVIE_RENTAL_SEQ")
    @SequenceGenerator(
            name = "MOVIE_RENTAL_SEQ",
            sequenceName = "MOVIE_RENTAL_SEQ",
            allocationSize = 1)
    private Long rentalId;

    @Column(name = "customer_id", insertable = false, updatable = false)
    private String customerId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", insertable = false, updatable = false)
    Customer customer;

    @Column(name = "movie_id", insertable = false, updatable = false)
    private String movieId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "movie_id", insertable = false, updatable = false)
    private Movie movie;

    @Column(name = "rental_period")
    private Integer rentalPeriod;
}
