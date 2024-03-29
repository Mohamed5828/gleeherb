package com.mohamed.egHerb.controllers;

import com.mohamed.egHerb.entity.UserAddress;
import com.mohamed.egHerb.service.UserAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user-address")
public class UserAddressController {
    @Autowired
    private UserAddressService userAddressService;

    @PostMapping("/add")
    public ResponseEntity<String> addUserAddress(@RequestHeader("Authorization") String jwt,
                                                 @RequestBody UserAddress userAddress) {
        userAddressService.addUserAddress(userAddress);
        return ResponseEntity.ok("User address added successfully");
    }
    @GetMapping("")
    public UserAddress getAddress(){
        return userAddressService.getAddress();
    }

    @DeleteMapping("")
    public  void deleteAddress(){
        userAddressService.deleteAddress();
    }

}