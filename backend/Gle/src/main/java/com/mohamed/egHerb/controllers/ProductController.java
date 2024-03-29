package com.mohamed.egHerb.controllers;

import com.mohamed.egHerb.entity.Product;
import com.mohamed.egHerb.entity.ProductRequest;
import com.mohamed.egHerb.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    @Autowired
    private final ProductService productService;
    ProductController(ProductService productService){
        this.productService = productService;
    }
    //this is for the main product scrapping post
    @PostMapping("/updateOrSave")
    public ResponseEntity<String> updateOrSaveProduct(@RequestBody Product updatedProduct){
        productService.updateProduct(updatedProduct);
        return ResponseEntity.ok("Product Updated or Saved Successfully");
    }


    @PostMapping("/add")
    public ResponseEntity<String> addProduct(@RequestBody Product product) {
        try {
            productService.addProduct(product);
            return ResponseEntity.ok("product added");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to add product: " + e.getMessage());
        }
    }

    @GetMapping("")
    public List<Product> getAllProduct(){
        return productService.getProducts();
    }


}
