package com.mohamed.egHerb.dto;

import com.mohamed.egHerb.entity.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserOrderDTO {

    private String image;
    private String title;
    private String quantity;
    private String date;
    private String total;
    private OrderStatus orderStatus;

    @Override
    public String toString() {
        return "UserOrderDTO{" +
                "image='" + image + '\'' +
                ", title='" + title + '\'' +
                ", quantity=" + quantity +
                ", date='" + date + '\'' +
                ", total='" + total + '\'' +
                '}';
    }
}
