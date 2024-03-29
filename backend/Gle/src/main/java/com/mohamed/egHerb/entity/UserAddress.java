package com.mohamed.egHerb.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name="user_address")
public class UserAddress {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "user_id")
    private AppUser user;

    @Column(name = "address_line1")
    private String addressLine1;

    @Column(name = "apartment")
    private String apartment;

    @Column(name = "floor")
    private String floor;

    @Enumerated(EnumType.STRING)
    @Column (name = "city")
    private Cities city;

    @Column(name = "area")
    private String area;

    @Column(name = "mobile")
    private String mobile;

    @Column(name = "building")
    private String building;

    public UserAddress(){}

    public UserAddress(int id, AppUser user, String addressLine1, String apartment, String floor, Cities city, String area, String mobile) {
        this.id = id;
        this.user = user;
        this.addressLine1 = addressLine1;
        this.apartment = apartment;
        this.floor = floor;
        this.city = city;
        this.area = area;
        this.mobile = mobile;
    }

    @Override
    public String toString() {
        return "UserAddress{" +
                "id=" + id +
                ", user=" + user +
                ", addressLine1='" + addressLine1 + '\'' +
                ", apartment='" + apartment + '\'' +
                ", floor='" + floor + '\'' +
                ", city=" + city +
                ", area='" + area + '\'' +
                ", mobile=" + mobile +
                '}';
    }
}
