package com.javaweb.model.entity;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "orders")
@Getter
@Setter
public class OrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_customer", nullable = false)
    private CustomerEntity orderOwner;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_restaurant", nullable = false)
    private RestaurantEntity restaurantEntity;

    @Column(name = "customer_address", nullable = false)
    private String customerAddress;

    @Column(name = "postcode_address", nullable = false, length = 20)
    private Integer postCodeAddress;

    @Column(name = "card_number", nullable = false)
    private String cardNumber;

    @Column(name = "ccv", nullable = false, length = 10)
    private String ccv;

    @Column(name = "expiration", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date expiration;

    @OneToMany(mappedBy = "orderEntity", fetch = FetchType.LAZY)
    private List<ShippingEntity> shippingEntityList;

    @OneToMany(mappedBy = "orderEntity", fetch = FetchType.LAZY)
    private List<OrderlinesComboEntity> orderlinesComboEntityList;

    @OneToMany(mappedBy = "orderEntity")
    private List<OrderlinesDishEntity> orderlinesDishEntityList;
}