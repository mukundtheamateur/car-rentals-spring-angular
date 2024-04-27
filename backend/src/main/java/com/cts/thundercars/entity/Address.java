package com.cts.thundercars.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "address")
@Data
@EqualsAndHashCode(callSuper = true)
public class Address extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank(message="Pickup address should not be blank")
    @Column(name = "pickup_address", nullable = false, length = 200)
    private String pickupAddress;

    @NotBlank(message="Drop address should not be blank")
    @Column(name = "drop_address", nullable = false, length = 200)
    private String dropAddress;

}

