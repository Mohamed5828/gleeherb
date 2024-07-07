package com.mohamed.egHerb.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Discount extends AbstractEntityModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "discount_code")
    private String discountCode;

    @Column(name = "discount_value")
    private float discountValue;

    @Column(name = "usage_number")
    private int usageNumber;

    @Override
    public String toString() {
        return "Discount{" +
                "id=" + id +
                ", discountCode='" + discountCode + '\'' +
                ", discountValue='" + discountValue + '\'' +
                ", usageNumber=" + usageNumber +
                '}';
    }
}
