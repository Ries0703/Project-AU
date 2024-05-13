package com.javaweb.model.entity;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "combo")
@Getter
@Setter
public class ComboEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "id_restaurant")
    private RestaurantEntity restaurantEntity;

    @Column(name = "name")
    private String name;

    @Column(name = "price")
    private Double price;

    @Column(name = "image")
    private byte[] image;

    @Column(name = "description")
    private String description;

    @Column(name = "bestseller_status")
    private boolean bestsellerStatus;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinTable(
            name = "dish_combo",
            joinColumns = @JoinColumn(name = "ID_combo"),
            inverseJoinColumns = @JoinColumn(name = "ID_dish")
    )
    List<DishEntity> dishEntityList;
}
