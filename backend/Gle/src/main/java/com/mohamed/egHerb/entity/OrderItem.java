package com.mohamed.egHerb.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime ;

@Entity
@Getter
@Setter
@Table(name = "order_items")
public class OrderItem extends AbstractEntityModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private OrderDetail order;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(name = "quantity")
    private int quantity;

    public OrderItem(){}

    public OrderItem(OrderDetail orderId,int quantity, Product productId) {
        this.order = orderId;
        this.product = productId;
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "OrderItem{" +
                "id=" + id +
                ", product=" + product +
                ", quantity=" + quantity +
                '}';
    }
}


