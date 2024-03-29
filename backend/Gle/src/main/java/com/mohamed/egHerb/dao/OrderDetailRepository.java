package com.mohamed.egHerb.dao;

import com.mohamed.egHerb.entity.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, Integer> {
    public List<OrderDetail> findAllByUserId(int userId);

}
