package com.mohamed.egHerb.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Entity
@Getter
@Setter
@Table(name="payment_details")
public class PaymentDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private OrderDetail order;

    @Column(name="status")
    private String status;

    @Column(name = "created_at")
    private Timestamp createdAt;

    @Column(name = "modified_at")
    private Timestamp modifiedAt;

    public PaymentDetail(){}
    public PaymentDetail(OrderDetail orderId, String status, Timestamp createdAt, Timestamp modifiedAt) {
        this.order = orderId;
        this.status = status;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }
    @Override
    public String toString() {
        return "PaymentDetail{" +
                "id=" + id +
                ", orderId=" + order +
                ", status='" + status + '\'' +
                ", createdAt=" + createdAt +
                ", modifiedAt=" + modifiedAt +
                '}';
    }
}
