package com.mohamed.egHerb.dao;

import com.mohamed.egHerb.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemsRepository extends JpaRepository<OrderItem, Integer> {

}
