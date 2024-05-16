package com.javaweb.model.entity;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;
import java.util.Date;
import java.util.List;

import javax.persistence.*;

@Entity
@Table(name = "restaurants")
@Getter
@Setter
public class RestaurantEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "res_name")
    private String name;

    @Column(name = "res_category")
    private String category;

    @Column(name = "res_address")
    private String address;

    @Column(name = "start_time")
    private LocalTime startTime;

    @Column(name = "end_time")
    private LocalTime endTime;

    @Column(name = "phone", length = 20)
    private String phone;

    @Column(name = "post_code", length = 20)
    private Integer postCode;

    @OneToMany(mappedBy = "restaurantEntity", fetch = FetchType.LAZY)
    List<OrderEntity> orderEntityList;

    @OneToMany(mappedBy = "restaurantEntity", fetch = FetchType.LAZY)
    List<FeedbackEntity> feedbackEntityList;

    @OneToMany(mappedBy = "restaurantEntity", fetch = FetchType.LAZY)
    List<DishEntity> dishEntityList;

    @OneToMany(mappedBy = "restaurantEntity", fetch = FetchType.LAZY)
    List<ComboEntity> comboEntityList;


}
