package com.mohamed.egHerb.controllers;

import com.mohamed.egHerb.entity.CartItem;
import com.mohamed.egHerb.entity.Product;
import com.mohamed.egHerb.service.CartItemService;
import com.mohamed.egHerb.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
public class CartItemController {

    @Autowired
    private final CartItemService cartItemService;
    @Autowired
    private final JwtService jwtService;
    public CartItemController(CartItemService cartItemService, JwtService jwtService){
        this.cartItemService = cartItemService;
        this.jwtService = jwtService;
    }
    @GetMapping("")
    public List<Product> getCart(){
        return cartItemService.getCartProducts();
    }
    @PostMapping("/add")
    public void addToCart(@RequestBody CartItem cartItem){
        cartItemService.addToCart(cartItem);
    }

    @PostMapping("/delete")
    public void deleteFromCart(@RequestParam int cartItemId) {
        cartItemService.deleteFromCart(cartItemId);
    }

    @DeleteMapping("/delete")
    public void deleteUserCart(@RequestParam int productId){
        int userId = jwtService.extractUserIdFromToken();
        cartItemService.deleteCartItem(userId, productId);
    }
    @PutMapping("/item/{productId}")
    public void changeQuantity(@PathVariable int productId ,@RequestBody Integer quantity){
        int userId = jwtService.extractUserIdFromToken();
        cartItemService.changeUserCartItem(userId , productId , quantity);
    }
}
