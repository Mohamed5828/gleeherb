package com.mohamed.egHerb.service;

import com.mohamed.egHerb.dao.OrderDetailRepository;
import com.mohamed.egHerb.dao.OrderItemsRepository;
import com.mohamed.egHerb.entity.CartItem;
import com.mohamed.egHerb.entity.OrderDetail;
import com.mohamed.egHerb.entity.OrderItem;
import com.mohamed.egHerb.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
public class OrderItemService {
    @Autowired
    private final JwtService jwtService;
    @Autowired
    private final CartItemService cartItemService;
    @Autowired
    private final OrderItemsRepository orderItemsRepository;
    @Autowired
    private final OrderDetailRepository orderDetailRepository;
    public OrderItemService(JwtService jwtService, CartItemService cartItemService, OrderItemsRepository orderItemsRepository, OrderDetailRepository orderDetailRepository) {
        this.jwtService = jwtService;
        this.cartItemService = cartItemService;
        this.orderItemsRepository = orderItemsRepository;
        this.orderDetailRepository = orderDetailRepository;
    }
    public List<OrderItem> addCartToOrderItem(int userId , Integer orderDetailId) {
        List<CartItem> currentCartItems = cartItemService.getCart();
        OrderDetail orderDetail = orderDetailRepository.findById(orderDetailId)
                        .orElseThrow(()->new RuntimeException("Order Detail Not Found For ID = " + orderDetailId));
        AtomicInteger total = new AtomicInteger();
        List<OrderItem> orderItems = currentCartItems.stream().map(cartItem -> {
            OrderItem orderItem = new OrderItem();
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setProduct(cartItem.getProduct());
            orderItem.setOrder(orderDetail);
            int productPrice = cartItem.getProduct().getPriceEg();
            total.addAndGet(productPrice * cartItem.getQuantity());
            return orderItem;
        })
                .map(orderItemsRepository::save)
                .collect(Collectors.toList());
        orderDetail.setTotal(total.get());
        orderDetailRepository.save(orderDetail);
        try {
            cartItemService.deleteByUserId(userId);
        } catch (Exception e) {
            throw new RuntimeException("Failed to delete cart items for user: " + userId, e);
        }
        return orderItems;
    }
}

