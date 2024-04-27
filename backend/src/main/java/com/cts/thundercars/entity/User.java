package com.cts.thundercars.entity;

import java.sql.Date;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "user")
public class User extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Email
    @NotBlank(message="Email should not be blank")
    @Column(name = "email", nullable = false, unique=true)
    private String email;

    
    @NotBlank(message="Phone should not be blank")
    @Column(name = "phone", nullable = false, length = 13)
    private String phone;

    @NotBlank(message="fullname should not be blank")
    @Column(name = "fullname", nullable = false)
    private String fullname;

    @Column(name = "verified", columnDefinition = "boolean default false")
    private Boolean verified;

    @Column(name = "date_of_birth", nullable = false)
    private Date dateOfBirth;

    @Column(name = "enable_email_notification", columnDefinition = "boolean default true")
    private Boolean enableEmailNotification;

    @Column(name = "avatar")
    private String avatar;

    @Column(name = "bio")
    private String bio;

    @NotBlank(message="City should not be blank")
    @Column(name = "city", nullable = false)
    private String city;

    @Column(name = "blacklisted", columnDefinition = "boolean default false")
    private Boolean blacklisted;

    @Column(name = "pay_later", columnDefinition = "boolean default true")
    private Boolean payLater;
    
    @Column(name="refresh_token")
    private String refreshToken;
    
    @NotBlank
    @Column(name="password")
    private String password;
    
    @ManyToOne(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinColumn(name="role_id")
    private Roles role;
    
    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name="bookings")
    private List<Bookings> bookings;

}



