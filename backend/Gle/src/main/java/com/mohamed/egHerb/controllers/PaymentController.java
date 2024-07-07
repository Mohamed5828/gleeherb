package com.mohamed.egHerb.controllers;

import com.mohamed.egHerb.dto.DiscountRequest;
import com.mohamed.egHerb.service.EmailVerificationService;
import com.mohamed.egHerb.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class PaymentController {
    @Autowired
    private final PaymentService paymentService;
    @Autowired
    private final EmailVerificationService emailVerificationService;

    public PaymentController(PaymentService paymentService, EmailVerificationService emailVerificationService) {
        this.paymentService = paymentService;
        this.emailVerificationService = emailVerificationService;
    }

    @PostMapping("/codpayment")
    public boolean placeCODOrder(@RequestBody DiscountRequest discountRequest ){
        return paymentService.makeCodRequest(discountRequest);
    }

    @PostMapping("/api/paymentinitialization")
    public String paymobPayment(@RequestBody(required = false) DiscountRequest discountRequest) {
        return paymentService.doThePayment(discountRequest);
//    public Mono<Boolean> paymobPayment() {
//        Mono<String> tokenMono = paymentService.initializePayment();
//        return tokenMono.flatMap(token -> {
//            System.out.println(token);
//            Mono<String> regMono = paymentService.orderRegistration(token);
//            return regMono.map(reg -> {
//                System.out.println(reg);
//                return true;
//            });
//        });
//    }
    }
}