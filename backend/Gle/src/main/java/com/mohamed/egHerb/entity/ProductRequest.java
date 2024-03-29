package com.mohamed.egHerb.entity;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ProductRequest {
    private String title;
    private String secondImage;
    private String description;
    private String suggestedUseData;
    private String otherIngredData;

    // Add other fields as needed
    ProductRequest(){}

    public ProductRequest(String title, String secondImage, String description, String suggestedUseData, String otherIngredData, List<String> categories, List<String> categoriesDescription) {
        this.title = title;
        this.secondImage = secondImage;
        this.description = description;
        this.suggestedUseData = suggestedUseData;
        this.otherIngredData = otherIngredData;
   }

    @Override
    public String toString() {
        return "ProductRequest{" +
                "title='" + title + '\'' +
                ", secondImage='" + secondImage + '\'' +
                ", description='" + description + '\'' +
                ", suggestedUseData='" + suggestedUseData + '\'' +
                ", otherIngredData='" + otherIngredData + '\'' +
                '}';
    }
}

