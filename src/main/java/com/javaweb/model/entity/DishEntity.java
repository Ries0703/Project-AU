package com.javaweb.model.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "dish")
@Getter
@Setter
public class DishEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_restaurant", nullable = false)
    private RestaurantEntity restaurantEntity;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "price", nullable = false, precision = 10, scale = 2)
    private Double price;

    @Lob
    @Column(name = "image", nullable = false)
    private byte[] image;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "bestseller_status", nullable = false)
    private boolean bestsellerStatus = false;

    @ManyToMany(mappedBy = "dishEntityList", fetch = FetchType.LAZY)
    List<ComboEntity> comboEntityList;
}
