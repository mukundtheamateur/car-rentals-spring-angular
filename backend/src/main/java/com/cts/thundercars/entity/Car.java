package com.cts.thundercars.entity;


import com.cts.thundercars.constant.FuelPolicyType;
import com.cts.thundercars.constant.FuelType;
import com.cts.thundercars.constant.GearBoxType;
import com.cts.thundercars.constant.WheelDriveType;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "car")
public class Car extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank(message="car name can not be blank")
    @Column(name = "car_name", nullable = false)
    private String carName;

    @Column(name = "price", nullable = false)
    private Integer price;

    @Column(name = "deposit", nullable = false)
    private Integer deposit;

    @Enumerated(EnumType.STRING)
    @Column(name = "fuel_type", nullable = false)
    private FuelType fuelType;

    @Enumerated(EnumType.STRING)
    @Column(name = "gearbox", nullable=false)
    private GearBoxType gearbox;

    @NotBlank(message="car image can not be blank")
    @Column(name = "image", nullable = false)
    private String image;

    @Column(name = "seats", nullable = false)
    private Integer seats;

    @Column(name = "doors", nullable = false)
    private Integer doors;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "fuelpolicy", nullable=false)
    private FuelPolicyType fuelpolicy;

	@Column(name = "mileage")
    private Integer mileage;
	
    @Column(name = "cancellation", nullable = false)
    private Integer cancellation;

    @Column(name = "amendments")
    private Integer amendments;

    @Column(name = "theft_protection", nullable = false)
    private Integer theftProtection;

    @Column(name = "collision_damage", nullable = false)
    private Integer collisionDamage;

    @Column(name = "full_insurance", nullable = false)
    private Integer fullInsurance;

    @Column(name = "additional_driver", nullable = false)
    private Integer additionalDriver;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "wheel_drive", nullable=false)
    private WheelDriveType wheelDrive;
   
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "dealer_id", nullable=false)
    private CarDealer dealer; 
}


