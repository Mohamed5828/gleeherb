package com.mohamed.egHerb.service;

import com.mohamed.egHerb.dao.DiscountRepository;
import com.mohamed.egHerb.dto.DiscountRequest;
import com.mohamed.egHerb.entity.Discount;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

@Service
public class DiscountService {
    DiscountRepository discountRepository;

    public DiscountService(DiscountRepository discountRepository) {
        this.discountRepository = discountRepository;
    }

    public float verifyDiscountValue(DiscountRequest discountRequest) {
        String discountCode = discountRequest.getDiscountName();
        Discount discount = discountRepository.findByDiscountCode(discountCode);
        System.out.println(discount);

        if (discount != null) {
            System.out.println(discount.getDiscountValue());
            return discount.getDiscountValue();
        } else {
            return 1;
        }
    }


    public boolean addDiscountValue(DiscountRequest discountRequest) {

        // Check for missing fields
        if (discountRequest.getDiscountName() == null || discountRequest.getDiscountValue() == 0) {
            throw new IllegalArgumentException("Discount code and value cannot be null");
        }

        Discount discount = new Discount();
        discount.setDiscountCode(discountRequest.getDiscountName());
        discount.setDiscountValue(discountRequest.getDiscountValue());
        discount.setUsageNumber(discountRequest.getDiscountUsage());

        try {
            discountRepository.save(discount);
            return true;
        } catch (DataAccessException e) {
            // Handle data access error, like logging or returning a specific error code
            System.err.println("Error adding discount to repository: " + e.getMessage());
            return false;
        }
    }

}
