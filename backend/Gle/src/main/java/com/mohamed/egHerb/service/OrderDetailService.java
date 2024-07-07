package com.mohamed.egHerb.service;

import com.mohamed.egHerb.dao.OrderDetailRepository;
import com.mohamed.egHerb.dao.UserRepository;
import com.mohamed.egHerb.dto.UserOrderDTO;
import com.mohamed.egHerb.entity.OrderDetail;
import com.mohamed.egHerb.entity.OrderItem;
import com.mohamed.egHerb.entity.OrderStatus;
import com.mohamed.egHerb.entity.Product;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class OrderDetailService {
    @Autowired
    private OrderDetailRepository orderDetailRepository;
    @Autowired
    private final JwtService jwtService;
    @Autowired
    private final OrderItemService orderItemService;
    @Autowired
    private final UserRepository userRepository;
    public OrderDetailService(OrderDetailRepository orderDetailRepository, JwtService jwtService, OrderItemService orderItemService, UserRepository userRepository) {
        this.orderDetailRepository = orderDetailRepository;
        this.jwtService = jwtService;
        this.orderItemService = orderItemService;
        this.userRepository = userRepository;
    }

    public OrderDetail getOrderDetailById(int orderDetailId){
        return orderDetailRepository.findById(orderDetailId).orElse(null);
    }
    public List<OrderItem> getOrderItemsForOrderDetail(OrderDetail orderDetail) {
        return orderDetail != null ? orderDetail.getOrderItems() : Collections.emptyList();
    }
    public List<Product> getProductsForOrderDetail(OrderDetail orderDetail){
        List<OrderItem> orderItems = getOrderItemsForOrderDetail(orderDetail);
        List<Product> products = new ArrayList<>();
        for(OrderItem orderItem : orderItems){
            Product product = orderItem.getProduct();
            product.setQuantity(String.valueOf(orderItem.getQuantity()));
            products.add(product);
        }
        return products;
    }
    @Transactional
    public OrderDetail constructOrderDetail( float discountValue) {
        int userId;
        try {
            userId = jwtService.extractUserIdFromToken();
            System.out.println("User ID extracted from token: " + userId);
        } catch (Exception e) {
            System.out.println("Error extracting user ID from token: " + e.getMessage());
            throw new RuntimeException("Invalid token. Cannot extract user ID.", e);
        }
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setUser(userRepository.findById(userId)
                .orElseThrow(() -> new IllegalStateException("User Not Found With Id = " + userId)));
        orderDetail.setOrderStatus(OrderStatus.processing);
        orderDetail = orderDetailRepository.save(orderDetail);
        List<OrderItem> orderItems = orderItemService.addCartToOrderItem(userId ,orderDetail.getId(), discountValue);
        orderDetail.setOrderItems(orderItems);
        orderDetail.setPaymentMethod("Credit Card");
        orderDetail = orderDetailRepository.save(orderDetail);
        //the following line cause stackoverflow;
        //System.out.println("Order detail constructed successfully. " + orderDetail);
        return orderDetail;
    }
    public List<OrderDetail> getOrderDetailsByUserId(int userId){
        return orderDetailRepository.findAllByUserId(userId);
    }
    public List<List<UserOrderDTO>> getOrderedProductsForUser() {
        int userId = jwtService.extractUserIdFromToken();
        List<OrderDetail> userOrderDetails = getOrderDetailsByUserId(userId);
        List<List<UserOrderDTO>> allUserOrders = new ArrayList<>();
        for (OrderDetail orderDetail : userOrderDetails) {
            List<Product> userOrderedProducts = getProductsForOrderDetail(orderDetail);
            List<UserOrderDTO> userOrderDTOList = new ArrayList<>();
            for (Product product : userOrderedProducts) {
                UserOrderDTO userOrderDTO = new UserOrderDTO();
                userOrderDTO.setImage(product.getFirstImage());
                userOrderDTO.setQuantity(product.getQuantity());
                userOrderDTO.setTitle(product.getTitle());
                userOrderDTO.setPrice(String.valueOf(product.getPriceEg()));
                userOrderDTO.setDate(String.valueOf(orderDetail.getCreationDate()));
                userOrderDTO.setTotal(String.valueOf(orderDetail.getTotal()));
                userOrderDTO.setOrderStatus(orderDetail.getOrderStatus());
                userOrderDTOList.add(userOrderDTO);
            }
            allUserOrders.add(userOrderDTOList);
        }
        return allUserOrders;
    }
}
