package com.emovie.movie_rental_management_system.entity;

import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Entity
@Table(name = "Movie")
@NamedQuery(name = "Movie.findAll", query = "SELECT m FROM Movie m")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Movie extends Audit implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "movie_id")
    private String movieId;

    @Column(name = "movie_title")
    private String movieTitle;

    @Column(name = "movie_code")
    private String movieCode;
}

