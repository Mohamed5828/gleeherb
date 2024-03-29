package com.mohamed.egHerb.dao;

import com.mohamed.egHerb.entity.AppUser;
import com.mohamed.egHerb.entity.CartItem;
import com.mohamed.egHerb.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CartItemRepository extends JpaRepository<CartItem, Integer> {
    List<CartItem> findByUserId(Integer userId);
    void deleteByUserId(int userId);

    Optional<CartItem> findByUserAndProduct(AppUser user, Product product);

    Optional<CartItem> findByUserAndId(AppUser user, int itemId);

    List<CartItem> findByUser(AppUser user);

    void deleteByUserIdAndProductId(int userId, int productId);
}
