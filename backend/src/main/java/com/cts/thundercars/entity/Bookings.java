package com.cts.thundercars.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.sql.Date;

import com.cts.thundercars.constant.StatusType;

@Entity
@Table(name = "bookings")
@Data
@EqualsAndHashCode(callSuper = true)
public class Bookings extends BaseEntity {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
    private Integer id;

    @NotNull(message="From date should not be null")
    @Column(name = "from_date", nullable = false)
    private Date fromDate;

    @NotNull(message="To date should not be null")
    @Column(name = "to_date", nullable = false)
    private Date toDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private StatusType status;

    @Column(name = "cancellation", columnDefinition = "boolean default false")
    private Boolean cancellation;

    @Column(name = "amendments", columnDefinition = "boolean default false")
    private Boolean amendments;

    @Column(name = "theft_protection", columnDefinition = "boolean default false")
    private Boolean theftProtection;

    @Column(name = "collision_damage", columnDefinition = "boolean default false")
    private Boolean collisionDamage;

    @Column(name = "full_insurance", columnDefinition = "boolean default false")
    private Boolean fullInsurance;

    @Column(name = "additional_driver", columnDefinition = "boolean default false")
    private Boolean additionalDriver;

    @Column(name = "price")
    private Integer price;

    @Column(name = "cancel_request", columnDefinition = "boolean default false")
    private Boolean cancelRequest;
    

    @NotNull(message="Car dealer should not be null")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "car_dealer")
    private CarDealer carDealer;

    @NotNull(message="User should not be null")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    @NotNull(message="Car should not be null")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "car_id")
    private Car car;

    @NotNull(message="Address should not be null")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "address_id")
    private Address address;    
    
}
