package com.mohamed.egHerb.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.mohamed.egHerb.dao.UserAddressRepository;
import com.mohamed.egHerb.dao.UserRepository;
import com.mohamed.egHerb.entity.AppUser;
import com.mohamed.egHerb.entity.OrderDetail;
import com.mohamed.egHerb.entity.Product;
import com.mohamed.egHerb.entity.UserAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PaymentRequestService {

    @Autowired
    private final OrderDetailService orderDetailService;
    @Autowired
    private final ObjectMapper objectMapper;
    @Autowired
    private final JwtService jwtService;
    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final UserAddressRepository userAddressRepository;
    PaymentRequestService(OrderDetailService orderDetailService , ObjectMapper objectMapper, JwtService jwtService, UserRepository userRepository, UserAddressRepository userAddressRepository){
        this.orderDetailService = orderDetailService;
        this.objectMapper = objectMapper;
        this.jwtService = jwtService;
        this.userRepository = userRepository;
        this.userAddressRepository = userAddressRepository;
    }



    public ObjectNode buildOrderRequest(OrderDetail orderDetail, String accessToken ) {
        List<Product> orderProducts = orderDetailService.getProductsForOrderDetail(orderDetail);
        ObjectNode orderJson = objectMapper.createObjectNode();
        orderJson.put("auth_token" ,accessToken);
        orderJson.put("delivery_needed" , "false");
        orderJson.put("currency", "EGP");
        orderJson.put( "amount_cents", String.valueOf(orderDetail.getTotal() ));
        ArrayNode itemArrayNode = objectMapper.createArrayNode();
        for(Product product : orderProducts){
            ObjectNode itemOrder = objectMapper.createObjectNode();
            itemOrder.put("name" , product.getTitle());
            itemOrder.put("amount_cents" , product.getPriceEg()*100);
            itemOrder.put("quantity" , product.getQuantity());
            itemArrayNode.add(itemOrder);
        }
        orderJson.set("items" , itemArrayNode);
        return orderJson;
    }

    public ObjectNode buildFinalData(String id, String accessToken , int total) {
        int userId;
        try {
            userId = jwtService.extractUserIdFromToken();
            System.out.println("User ID extracted from token: " + userId);
        } catch (Exception e) {
            System.out.println("Error extracting user ID from token: " + e.getMessage());
            throw new RuntimeException("Invalid token. Cannot extract user ID.", e);
        }
        AppUser customer = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalStateException("User Not Found With Id = " + userId));

        Optional<UserAddress> customerAddressOptional = userAddressRepository.findByUserId(userId);
        UserAddress customerAddress = customerAddressOptional.get();

        ObjectMapper objectMapper = new ObjectMapper();

        ObjectNode orderJson = objectMapper.createObjectNode();
        orderJson.put("auth_token", accessToken);
        orderJson.put("amount_cents", String.valueOf(total));
        orderJson.put("expiration", "3600");
        orderJson.put("order_id", id);

        ArrayNode itemArrayNode = objectMapper.createArrayNode();
        ObjectNode itemOrder = objectMapper.createObjectNode();
        itemOrder.put("apartment", String.valueOf(customerAddress.getApartment()));
        itemOrder.put("email", customer.getEmail());
        itemOrder.put("floor",String.valueOf(customerAddress.getFloor()));
        itemOrder.put("first_name", customer.getFirstName());
        itemOrder.put("street", customerAddress.getAddressLine1());
        itemOrder.put("building", String.valueOf(customerAddress.getBuilding()));
        itemOrder.put("phone_number", String.valueOf(customerAddress.getMobile()));
        itemOrder.put("city", String.valueOf(customerAddress.getCity()));
        itemOrder.put("country", "Eg");
        itemOrder.put("last_name", customer.getFirstName());

        orderJson.set("billing_data", itemOrder);
        orderJson.put("currency", "EGP");
        orderJson.put("integration_id", "4545317");

        // Convert orderJson to string with all values as strings
        String jsonString = null;
        try {
            jsonString = objectMapper.writeValueAsString(orderJson);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        System.out.println("Final Data : " + jsonString);
        return orderJson;
    }
}

//    FinalOrderDTO finalOrderDTO = new FinalOrderDTO();
//        finalOrderDTO.setAuthToken(accessToken);
//                finalOrderDTO.setAmountCents("50");
//                finalOrderDTO.setExpiration("3600");
//                finalOrderDTO.setOrderId(String.valueOf(id));
//                finalOrderDTO.setCurrency("EGP");
//                finalOrderDTO.setIntegrationId("4403519");
//
//                FinalItemDTO finalItemDTO = new FinalItemDTO();
//                finalItemDTO.setApartment(customerAddress.getApartment());
//                finalItemDTO.setEmail(customer.getEmail());
//                finalItemDTO.setFloor(customerAddress.getFloor());
//                finalItemDTO.setFirstName(customer.getFirstName());
//                finalItemDTO.setStreet(customerAddress.getAddressLine1());
//                finalItemDTO.setBuilding(String.valueOf(customerAddress.getBuilding()));
//                finalItemDTO.setPhoneNumber(String.valueOf(customerAddress.getMobile()));
//                finalItemDTO.setCity(String.valueOf(customerAddress.getCity()));
//                finalItemDTO.setCountry("Egypt");
//                finalItemDTO.setLastName(customer.getLastName());
//
//                List<FinalItemDTO> billingDataList = new ArrayList<>();
//        billingDataList.add(finalItemDTO);
//        finalOrderDTO.setBilling_data(billingDataList);
//
//        System.out.println("Final Data : " + finalOrderDTO);