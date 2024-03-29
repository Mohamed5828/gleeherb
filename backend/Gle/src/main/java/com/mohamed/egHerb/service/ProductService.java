package com.mohamed.egHerb.service;

import com.mohamed.egHerb.dao.ProductRepository;
import com.mohamed.egHerb.entity.Product;
import com.mohamed.egHerb.entity.ProductRequest;
import com.mohamed.egHerb.errorExceptions.ProductNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.beans.PropertyDescriptor;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Service
public class ProductService {
    private ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository ){
        this.productRepository = productRepository;

    }

        @Transactional
        public Product updateProduct(Product updatedProduct) {
            String title = updatedProduct.getTitle();

            return productRepository.findByTitle(title)
                    .map(existingProduct -> {
                        // Use reflection to efficiently update non-null fields
                        BeanUtils.copyProperties(updatedProduct, existingProduct, getNullPropertyNames(updatedProduct));
                        return productRepository.save(existingProduct);
                    })
                    .orElseGet(() -> {
                        updatedProduct.setId(null); // Ensure a new ID is generated
                        return productRepository.save(updatedProduct);
                    });
        }

        private String[] getNullPropertyNames(Object source) {
            final BeanWrapper src = new BeanWrapperImpl(source);
            return Stream.of(src.getPropertyDescriptors())
                    .map(PropertyDescriptor::getName)
                    .filter(name -> src.getPropertyValue((String) name) == null)
                    .toArray(String[]::new);
        }

    public void addProduct(Product product) {
        Optional<Product> existingProductOptional = productRepository.findByTitle(product.getTitle());
        if (existingProductOptional.isPresent()) {
            Product existingProduct = existingProductOptional.get();
            existingProduct.setPriceEg(product.getPriceEg());
            productRepository.save(existingProduct);
        }else{
            productRepository.save(product);
        }
    }

    public List<Product> getProducts() {
        return productRepository.findAll();

    }
}
