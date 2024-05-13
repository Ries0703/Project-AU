package com.javaweb.model.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;


@Entity
@Table(name = "shipping")
@Getter
@Setter
public class ShippingEntity {

    @Id
    @Column(name = "id_ship")
    private Integer idShip;

    @ManyToOne
    @JoinColumn(name = "id_order")
    OrderEntity orderEntity;

    @Column(name = "method")
    private String method;

    @Column(name = "status")
    private String status;
}
