package com.mohamed.egHerb.controllers;

import com.mohamed.egHerb.entity.AppUserRole;
import com.mohamed.egHerb.dto.AuthenticationRequest;
import com.mohamed.egHerb.dto.AuthenticationResponse;
import com.mohamed.egHerb.errorExceptions.UserNotFoundException;
import com.mohamed.egHerb.service.AppUserService;
import com.mohamed.egHerb.service.AuthenticationService;
import com.mohamed.egHerb.dto.RegisterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;

@RestController
@RequestMapping("/api/v1/auth")

public class AuthenticationController {
    @Autowired
    private final AuthenticationService authenticationService;
    @Autowired
    private final AppUserService appUserService;

    public AuthenticationController(AuthenticationService authenticationService, AppUserService appUserService) {
        this.authenticationService = authenticationService;
        this.appUserService = appUserService;
    }

    @PostMapping("/register")
    public ResponseEntity <AuthenticationResponse> register(
            @RequestBody RegisterRequest request
    ) throws UserNotFoundException, UnsupportedEncodingException {
        AppUserRole userRole = request.getAppUserRole();
        if (userRole == null) {
            // Set a default role of USER if getAppUserRole() returns null
            userRole = AppUserRole.USER;
        }
        return ResponseEntity.ok(authenticationService.register(request ,userRole));
    }

    @PostMapping("/authenticate")
    public ResponseEntity <AuthenticationResponse> register(
            @RequestBody AuthenticationRequest request
    ){
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }
    @GetMapping("/verify")
    public ResponseEntity<String> verifyUser(@RequestParam("vcode") String vCode) throws UserNotFoundException {
        appUserService.verifyUser(vCode);
        return ResponseEntity.ok("User verified successfully");
    }
}
