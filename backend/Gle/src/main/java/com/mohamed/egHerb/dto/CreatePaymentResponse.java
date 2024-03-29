package com.mohamed.egHerb.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CreatePaymentResponse {
    private String clientSecret;
}