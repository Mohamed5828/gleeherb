package com.mohamed.egHerb.controllers;

import com.mohamed.egHerb.dto.DiscountRequest;
import com.mohamed.egHerb.service.DiscountService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DiscountController {
    private final DiscountService discountService;

    public DiscountController(DiscountService discountService) {
        this.discountService = discountService;
    }

    @PostMapping("/api/discount")
    public float verifyDiscount(@RequestBody DiscountRequest discountRequest){
        return discountService.verifyDiscountValue(discountRequest);
    }
@PostMapping("/api/adddiscount")
    public void addDiscount(@RequestBody DiscountRequest discountRequest){
         discountService.addDiscountValue(discountRequest);
    }

}
