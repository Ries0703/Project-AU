package com.javaweb.model.entity;

import lombok.Getter;
import lombok.Setter;

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

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "res_name", nullable = false)
    private String name;

    @Column(name = "res_category", nullable = false)
    private String category;

    @Column(name = "res_address", nullable = false)
    private String address;

    @Column(name = "start_time", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date startTime;

    @Column(name = "end_time", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date endTime;

    @Column(name = "phone", nullable = false, length = 20)
    private String phone;

    @Column(name = "post_code", nullable = false, length = 20)
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
