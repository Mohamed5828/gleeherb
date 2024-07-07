package com.mohamed.egHerb.dao;

import com.mohamed.egHerb.entity.Discount;
import org.springframework.data.jpa.repository.JpaRepository;


public interface DiscountRepository extends JpaRepository<Discount , Integer> {
    Discount findByDiscountCode(String discountCode);

}
