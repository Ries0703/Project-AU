package com.javaweb.model.entity;

import lombok.Getter;
import lombok.Setter;


import javax.persistence.*;

@Entity
@Table(name = "orderlines_combo",
        uniqueConstraints = @UniqueConstraint(columnNames = {"id_order", "id_combo"}))
@Getter
@Setter
public class OrderlinesComboEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_order", nullable = false)
    OrderEntity orderEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_combo", nullable = false)
    ComboEntity comboEntity;

    @Column(name = "quantity", nullable = false)
    private Integer quantity = 1;
}