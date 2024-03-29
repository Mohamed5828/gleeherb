package com.mohamed.egHerb.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@AllArgsConstructor
@Table(name = "products")
public class Product extends AbstractEntityModel{
    @Id
    @Column(name = "id")
    private Integer id;

    @Column(name = "suggested_use", columnDefinition = "TEXT")
    private String suggestedUse;

    @Column(name = "other_ingredients", columnDefinition = "TEXT")
    private String otherIngredients;

    @Column(name = "first_image")
    private String firstImage;

    @Column(name = "preview_image")
    private String previewImage;

    @Column(name = "second_image")
    private String secondImage;

    @Column(name = "title")
    private String title;

    @Column(name = "description" , columnDefinition = "TEXT")
    private String description;

    @Column(name = "weight")
    private String weight;

    @Column(name = "quantity")
    private String quantity;

    @Column(name = "price_uae")
    private Integer priceUae;

    @Column(name = "price_eg")
    private Integer priceEg;

    @Column(name = "expiry_date")
    private String expiryDate;

    @Column(name = "rating")
    private Integer rating;

    @Column(name = "first_available")
    private String firstAvailable;

    @Column(name = "product_batch")
    private String productBatch;

    @Column(name = "warning" , columnDefinition = "TEXT")
    private String warning;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<Review> reviews;
    public Product() {
    }


    public Product(int productId) {
        id = productId;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", suggestedUse='" + suggestedUse + '\'' +
                ", otherIngredients='" + otherIngredients + '\'' +
                ", firstImage='" + firstImage + '\'' +
                ", secondImage='" + secondImage + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", weight='" + weight + '\'' +
                ", quantity='" + quantity + '\'' +
                ", priceUae=" + priceUae +
                ", priceEg=" + priceEg +
                ", expiryDate='" + expiryDate + '\'' +
                ", rating=" + rating +
                ", firstAvailable='" + firstAvailable + '\'' +
                ", productBatch='" + productBatch + '\'' +
                ", warning='" + warning + '\'' +
                '}';
    }
}
