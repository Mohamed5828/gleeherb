package com.mohamed.egHerb.dao;

import com.mohamed.egHerb.entity.OrderItem;
import com.mohamed.egHerb.entity.UserAddress;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserAddressRepository extends JpaRepository<UserAddress, Integer> {

    Optional findByUserId(int userId);
}


