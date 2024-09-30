package com.emovie.movie_rental_management_system.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "Customer")
@NamedQuery(name = "Customer.findAll", query = "SELECT c FROM Customer c")
@Getter
@Setter
@NoArgsConstructor
public class Customer extends Audit implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "customer_id")
    private String customerId;

    @Column(name = "customer_name")
    private String customerName;

    @OneToMany(mappedBy = "customer", fetch = FetchType.LAZY)
    private List<MovieRental> rentals;
}
