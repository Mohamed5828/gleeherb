package com.mohamed.egHerb.service;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.mohamed.egHerb.dao.OrderDetailRepository;
import com.mohamed.egHerb.dto.DiscountRequest;
import com.mohamed.egHerb.dto.OrderRegistrationResponse;
import com.mohamed.egHerb.entity.OrderDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientException;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.function.BiFunction;

@Service
public class PaymentService {
    private static final String INIT_ENDPOINT = "/api/auth/tokens";
    private static final String ORDER_REG_ENDPOINT = "/api/ecommerce/orders";
    private static final String ORDER_KEY_ENDPOINT = "/api/acceptance/payment_keys";
    private static final String API_KEY = "ZXlKaGJHY2lPaUpJVXpVeE1pSXNJblI1Y0NJNklrcFhWQ0o5LmV5SmpiR0Z6Y3lJNklrMWxjbU5vWVc1MElpd2ljSEp2Wm1sc1pWOXdheUk2T1RZM09EWTJMQ0p1WVcxbElqb2lhVzVwZEdsaGJDSjkuOHdIM0F6Mm55Z0JENkhCRmRJdFI5eVdnMEZVN3R2Z3I1aVdfUmRmaTktTVRqRkhubWRhWExodlhkUHpJdkVTOE9wOWtBMkJhaVlORjU0ak83cTVDVkE=";
    private final WebClient webClient;
    private final PaymentRequestService paymentRequestService;
    private final OrderDetailService orderDetailService;
    private final ObjectMapper objectMapper;
    private final DiscountService discountService;
    private final OrderDetailRepository orderDetailRepository;
    private final EmailVerificationService emailVerificationService;


    @Autowired
    public PaymentService(WebClient.Builder webClientBuilder ,
                          PaymentRequestService paymentRequestService,
                          OrderDetailService orderDetailService, ObjectMapper objectMapper, DiscountService discountService, OrderDetailRepository orderDetailRepository, EmailVerificationService emailVerificationService) {
        this.webClient = webClientBuilder.baseUrl("https://accept.paymob.com").build();
        this.paymentRequestService = paymentRequestService;
        this.orderDetailService = orderDetailService;
        this.objectMapper = objectMapper;
        this.discountService = discountService;
        this.orderDetailRepository = orderDetailRepository;
        this.emailVerificationService = emailVerificationService;
    }
    public Mono<String> initializePayment(){
        HashMap<String, String> requestBody = new HashMap<>();
        requestBody.put("api_key" , API_KEY);
        return sendApiRequest(requestBody, INIT_ENDPOINT, this::handleAuthResponse)
                .map(ResponseEntity::getBody);
    }
    public Mono<OrderRegistrationResponse> orderRegistration(String accessToken , float discountRate) {
        OrderDetail orderDetail = orderDetailService.constructOrderDetail(discountRate);
        ObjectNode orderJson = paymentRequestService.buildOrderRequest(orderDetail, String.valueOf(accessToken) );
        JsonNode total = orderJson.get("amount_cents");
        // Make API request using WebClient
        return webClient.post()
                .uri(ORDER_REG_ENDPOINT)
                .bodyValue(orderJson)
                .retrieve()
                .bodyToMono(String.class)
                .map(orderString -> new OrderRegistrationResponse(orderString , total));
    }

    private Mono<ResponseEntity<String>> sendApiRequest(Object requestBody, String endpoint, BiFunction<String, ResponseEntity<String>, Mono<ResponseEntity<String>>> responseHandler) {
        return webClient.post()
                .uri(endpoint)
                .header("Content-Type", "application/json")
                .bodyValue(requestBody)
                .retrieve()
                .toEntity(String.class)
                .flatMap(response -> responseHandler.apply(response.getBody(), response))
                .onErrorResume(this::handleError);
    }


    private Mono<ResponseEntity<String>> handleAuthResponse(String responseBody, ResponseEntity<String> response) {
        if (response.getStatusCode().is2xxSuccessful()) {
            try {
                JsonNode jsonNode = objectMapper.readTree(responseBody);
                String token = jsonNode.get("token").asText();
                return Mono.just(ResponseEntity.ok(token));
            } catch (JsonProcessingException e) {
                return Mono.error(new RuntimeException("Error processing JSON response", e));
            }
        } else {
            return Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("ERROR Processing Response"));
        }
    }
    public long handleOrderResponse(String responseBody)  {
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode jsonObject = mapper.readTree(responseBody);
            long id = jsonObject.get("id").asLong();
            return id;
        } catch (JsonParseException e) {
            e.printStackTrace();
            return -1;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    public Mono<String> acquirePaymentKey(String id, String token, int total) {
        ObjectNode finalData = paymentRequestService.buildFinalData(id,token, total );

        try {
            return webClient.post()
                    .uri(ORDER_KEY_ENDPOINT)
                    .header("Content-Type", "application/json")
                    .bodyValue(finalData)
                    .retrieve()
                    .bodyToMono(String.class);
        } catch (WebClientException e) {
            return Mono.error(e);
        } catch (Exception e) {
            return Mono.error(e);
        }
    }


    private Mono<ResponseEntity<String>> handleError(Throwable throwable) {
        // Handle different types of errors appropriately
        if (throwable instanceof JsonProcessingException) {
            return Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error processing JSON"));
        } else {
            return Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error processing request"));
        }
    }

    public String doThePayment(DiscountRequest discountRequest) {
        float discountRate = discountService.verifyDiscountValue(discountRequest);
        Mono<String> token = initializePayment();
        Mono<OrderRegistrationResponse> registrationMono = orderRegistration(token.block() , discountRate);
        OrderRegistrationResponse registrationResponse = registrationMono.block();
        assert registrationResponse != null;
        String orderString = registrationResponse.getOrderString();
        JsonNode totalNode = registrationResponse.getTotal(); // Assuming "total" is the key for the numeric value
            int totalValue = totalNode.asInt();
            totalValue *= 100;

        String paymobOrderId = String.valueOf(handleOrderResponse(orderString));
        Mono<String> paymentKeyToken = acquirePaymentKey(paymobOrderId ,token.block(), totalValue );
        String paymentKey = paymentKeyToken.block();

//        emailVerificationService.sendOrderConfirmationMail();
        return paymentKey;    }

    public boolean makeCodRequest(DiscountRequest discountRequest) {
        float discountValue = discountService.verifyDiscountValue(discountRequest);
        OrderDetail orderDetail = orderDetailService.constructOrderDetail(discountValue);
        orderDetail.setPaymentMethod("Cash");
        orderDetailRepository.save(orderDetail);
        return true;
    }
}
