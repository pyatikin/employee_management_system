package com.pyatkin.is.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "Address")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long addressId;

    @Column(name = "City", nullable = false)
    private String city;

    @Column(name = "Street", nullable = false)
    private String street;

    @Column(name = "House", nullable = false)
    private String house;

    // Геттеры и сеттеры
}
