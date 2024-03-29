package com.mohamed.egHerb.controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.mohamed.egHerb.dto.OrderRegistrationResponse;
import com.mohamed.egHerb.service.EmailVerificationService;
import com.mohamed.egHerb.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;


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
    @PostMapping("/api/paymentinitialization")
    public String paymobPayment(){
        return paymentService.doThePayment();
    }

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
