package com.javaweb.model.entity;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "orderlines_dish",
        uniqueConstraints = @UniqueConstraint(columnNames = {"id_order", "id_dish"}))
@Getter
@Setter
public class OrderlinesDishEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_order", nullable = false)
    OrderEntity orderEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_dish", nullable = false)
    DishEntity dishEntity;

    @Column(name = "quantity", nullable = false)
    private Integer quantity = 1;
}
