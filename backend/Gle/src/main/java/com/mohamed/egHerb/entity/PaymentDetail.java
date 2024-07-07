package com.mohamed.egHerb.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Entity
@Getter
@Setter
@Table(name="payment_details")
public class PaymentDetail extends AbstractEntityModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private OrderDetail order;

    @Column(name="status")
    private String status;


    public PaymentDetail(){}
    public PaymentDetail(OrderDetail orderId, String status) {
        this.order = orderId;
        this.status = status;
    }
    @Override
    public String toString() {
        return "PaymentDetail{" +
                "id=" + id +
                ", orderId=" + order +
                ", status='" + status + '\'' +
                '}';
    }
}
