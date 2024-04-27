package com.cts.thundercars.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "car_dealer")
@Data
@EqualsAndHashCode(callSuper = true)
public class CarDealer extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank(message="Dealer name should not be blank")
    @Column(name = "dealer_name", nullable = false, length = 200)
    private String dealerName;

    @Email
    @NotBlank(message="Email should not be blank")
    @Column(name = "email", unique=true, nullable = false, length = 200)
    private String email;
    
    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "refresh_token", length = 1000)
    private String refreshToken;

    @NotBlank(message="Phone should not be blank")
    @Column(name = "phone", nullable = false, length = 13)
    private String phone;

    @Column(name = "verified", columnDefinition = "boolean default false")
    private Boolean verified;

    @Column(name = "all_cities", length = 1000)
    private String allCities;

    @Column(name = "avatar", length = 200)
    private String avatar;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id")
    private Roles role;

}

