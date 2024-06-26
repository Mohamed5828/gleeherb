package com.mohamed.egHerb.controllers;

import com.mohamed.egHerb.dto.UserOrderDTO;
import com.mohamed.egHerb.service.OrderDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private final OrderDetailService orderDetailService;
    @Autowired

    public OrderController(OrderDetailService orderDetailService) {
        this.orderDetailService = orderDetailService;

    }

    @GetMapping("")
    public List<UserOrderDTO> getOrderedProducts(){
       return orderDetailService.getOrderedProductsForUser();
    }
}
