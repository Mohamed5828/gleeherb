package com.mohamed.egHerb.dto;

import com.google.gson.annotations.SerializedName;
import com.mohamed.egHerb.entity.CartItem;
import lombok.Getter;

@Getter
public class CreatePayment {
    @SerializedName("items")
    CartItem[] items;

}