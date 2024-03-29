package com.mohamed.egHerb.service;

import com.mohamed.egHerb.dto.AuthenticationRequest;
import com.mohamed.egHerb.dto.AuthenticationResponse;
import com.mohamed.egHerb.dto.RegisterRequest;
import com.mohamed.egHerb.entity.AppUser;
import com.mohamed.egHerb.entity.AppUserRole;
import com.mohamed.egHerb.dao.UserRepository;
import com.mohamed.egHerb.entity.UserAddress;
import com.mohamed.egHerb.errorExceptions.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.UUID;


@Service
public class AuthenticationService {
    @Autowired
    private final UserRepository repository;
    @Autowired
    private final PasswordEncoder passwordEncoder;
    @Autowired
    private final JwtService jwtService;
    @Autowired
    private final AuthenticationManager authenticationManager;

    public AuthenticationService(UserRepository repository, PasswordEncoder passwordEncoder, JwtService jwtService, AuthenticationManager authenticationManager, EmailVerificationService emailVerificationService) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.emailVerificationService = emailVerificationService;
    }

    @Autowired
    private final EmailVerificationService emailVerificationService;
    public AuthenticationResponse register(RegisterRequest request , AppUserRole role) throws UserNotFoundException, UnsupportedEncodingException {
        if (repository.existsByEmail(request.getEmail())) {
            // Email already in use, throw an exception or return an appropriate response
            throw new RuntimeException("Email is already in use");
        }
        String vCode = UUID.randomUUID().toString();
        var user = AppUser.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .appUserRole(role)
                .vcode(vCode)
                .build();
        repository.save(user);
        var jwtToken = jwtService.generateToken(user);
        System.out.println("EMAIL:" + request.getEmail());
        emailVerificationService.sendVerificationEmail(request.getEmail());
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
    public AuthenticationResponse authenticate (AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        AppUser user = repository.findByEmail(request.getEmail())
                .orElseThrow();
        if (user.getAppUserRole() == AppUserRole.ADMIN) {
            // Additional logic for admin users, if needed
        }

        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .isVerified(user.getIsVerified())
                .build();
    }
}
