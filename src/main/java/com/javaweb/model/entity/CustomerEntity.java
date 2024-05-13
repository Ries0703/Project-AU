package com.javaweb.model.entity;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "customers")
@Getter
@Setter
public class CustomerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "email")
    private String email;

    @Column(name = "vip_type")
    private String vipType;

    @Column(name = "vip_expiration")
    private LocalDate vipExpiration;

    @Column(name = "password")
    private String password;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "phone_number")
    private String phoneNumber;

    @OneToMany(mappedBy = "orderOwner", fetch = FetchType.LAZY)
    List<OrderEntity> orderEntityList;

    @OneToMany(mappedBy = "feedbackOwner", fetch = FetchType.LAZY)
    List<FeedbackEntity> feedbackList;
}