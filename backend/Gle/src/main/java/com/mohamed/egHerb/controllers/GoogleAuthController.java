package com.mohamed.egHerb.controllers;

import com.mohamed.egHerb.service.AppUserService;
import com.mohamed.egHerb.dto.AuthenticationResponse;
import com.mohamed.egHerb.service.CustomOAuth2UserService;
import com.mohamed.egHerb.service.JwtService;
import com.nimbusds.jose.JWSObject;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class GoogleAuthController {

    private final JwtService jwtService;
    private final CustomOAuth2UserService customOAuth2UserService;
    private final AppUserService appUserService;

    @PostMapping("/google")
    public ResponseEntity<String> authenticateWithGoogle(@RequestBody AuthenticationResponse googleTokenDTO) {
        try {
            // Validate the Google JWT token and extract user information
            OAuth2User oAuth2User = validateGoogleToken(googleTokenDTO.getToken());

            // Process user details and create or update user in your system
            customOAuth2UserService.processUserDetails(oAuth2User);

            // Load the user by email and generate a JWT token
            UserDetails userDetails = appUserService.loadUserByUsername(oAuth2User.getAttribute("email"));
            String jwtToken = jwtService.generateToken(userDetails);

            return ResponseEntity.ok(jwtToken);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    private OAuth2User validateGoogleToken(String googleToken) throws ParseException {
        // Parse the Google JWT token
        SignedJWT signedJWT = SignedJWT.parse(googleToken);
        JWSObject jwsObject = JWSObject.parse(googleToken);
        JWTClaimsSet jwtClaimsSet = signedJWT.getJWTClaimsSet();

        // Extract user information
        Map<String, Object> attributes = new HashMap<>();
        attributes.put("email", jwtClaimsSet.getStringClaim("email"));
        attributes.put("given_name", jwtClaimsSet.getStringClaim("given_name"));
        attributes.put("family_name", jwtClaimsSet.getStringClaim("family_name"));

        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority("USER")),
                attributes,
                "email"
        );
    }

}
