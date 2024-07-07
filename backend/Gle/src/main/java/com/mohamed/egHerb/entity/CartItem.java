package com.mohamed.egHerb.entity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;


@Entity
@Getter
@Setter

@Table(name="cart_items")
public class CartItem extends AbstractEntityModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private AppUser user;

    @Column(name = "quantity")
    private int quantity;

    public  CartItem(){}
    public CartItem(int productId, int quantity,int userId) {
        this.product = new Product(productId) ;
        this.quantity = quantity;
        this.user = new AppUser(userId) ;

    }

    @Override
    public String toString() {
        return "CartItem{" +
                "id=" + id +
                ", productId=" + product +
                ", userId=" + user +
                ", quantity=" + quantity +
                '}';
    }
}
