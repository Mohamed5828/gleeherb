package com.mohamed.egHerb.service;

import com.mohamed.egHerb.dao.CartItemRepository;
import com.mohamed.egHerb.dao.UserRepository;
import com.mohamed.egHerb.entity.AppUser;
import com.mohamed.egHerb.entity.CartItem;
import com.mohamed.egHerb.entity.Product;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class CartItemService {

    @Autowired
    private CartItemRepository cartItemRepository;
    @Autowired
    private final JwtService jwtService;
    @Autowired
    private final UserRepository userRepository;

    public CartItemService(CartItemRepository cartItemRepository, JwtService jwtService, UserRepository userRepository){
        this.cartItemRepository = cartItemRepository;
        this.jwtService = jwtService;
        this.userRepository = userRepository;
    }

    public List<CartItem> getCart() {
        int userId = jwtService.extractUserIdFromToken();
        return cartItemRepository.findByUserId(userId);
    }
    public List<Product> getCartProducts() {
        int userId = jwtService.extractUserIdFromToken();
        List<CartItem> cartItems = cartItemRepository.findByUserId(userId);
        List<Product> cartProducts = new ArrayList<>();
        for(CartItem cartItem:cartItems) {
            Product product =  cartItem.getProduct();
            product.setQuantity(String.valueOf(cartItem.getQuantity()));
            cartProducts.add(product);
        }
        return cartProducts;
    }


    public void addToCart(CartItem cartItem) {
        int userId;
        try {
            userId = jwtService.extractUserIdFromToken();
        } catch (Exception e) {
            throw new RuntimeException("Invalid token. Cannot extract user ID.", e);
        }

        // Check if the user exists
        AppUser user = userRepository.findById(userId)
                .orElseThrow(() -> {
                    System.out.println("User not found with ID: " + userId);
                    return new RuntimeException("User not found with ID: " + userId);
                });

        // Check if the cart already contains the same product for the same user
        Optional<CartItem> existingCartItem = cartItemRepository.findByUserAndProduct(user, cartItem.getProduct());

        if (existingCartItem.isPresent()) {
            // Update quantity or take any other appropriate action
            CartItem existingItem = existingCartItem.get();
            existingItem.setQuantity(existingItem.getQuantity() + cartItem.getQuantity());
            cartItemRepository.save(existingItem);
        } else {
            // If the cart does not contain the same product for the same user, add it to the cart
            cartItem.setUser(user);
            cartItemRepository.save(cartItem);
        }
    }


    @Transactional
    public void deleteFromCart(int cartItemId) {
        Optional<CartItem> toDelete = cartItemRepository.findById(cartItemId);
        CartItem cartItemToDelete = toDelete.get();
        cartItemRepository.delete(cartItemToDelete);
    }
    @Transactional
    public void deleteByUserId(int userId){
        cartItemRepository.deleteByUserId(userId);
    }
    @Transactional
    public void changeUserCartItem(int userId, int productId , Integer quantity) {
        // Fetch user
        AppUser user = userRepository.findById(userId)
                .orElseThrow(() -> {
                    System.out.println("User not found with ID: " + userId);
                    return new RuntimeException("User not found with ID: " + userId);
                });

        // Fetch cart items belonging to the specified user
        List<CartItem> cartItems = cartItemRepository.findByUser(user);

        // Find the cart item with the specified product ID
        Optional<CartItem> cartItemOptional = cartItems.stream()
                .filter(item -> item.getProduct().getId() == productId)
                .findFirst();

        if (cartItemOptional.isPresent()) {
            CartItem cartItem = cartItemOptional.get();
            cartItem.setQuantity(cartItem.getQuantity() + quantity);
            if(cartItem.getQuantity() <= 0){
                deleteFromCart(cartItem.getId());
            }else{
                cartItemRepository.save(cartItem);
            }
        } else {
            // Handle case where cart item with the specified product ID was not found
            // You may choose to create a new cart item or throw an exception
            // For demonstration, let's throw an exception
            throw new RuntimeException("Cart item not found for product ID: " + productId);
        }
    }

    @Transactional
    public void deleteCartItem(int userId, int productId) {
        cartItemRepository.deleteByUserIdAndProductId(userId, productId);
    }
}
