package com.mohamed.egHerb.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DiscountRequest {
    private String discountName;
    private float discountValue;
    private int discountUsage = 0;
}
