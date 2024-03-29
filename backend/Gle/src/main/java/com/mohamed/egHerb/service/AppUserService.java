package com.mohamed.egHerb.service;

import com.mohamed.egHerb.dao.UserRepository;
import com.mohamed.egHerb.entity.AppUser;
import com.mohamed.egHerb.errorExceptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service

public class AppUserService implements UserDetailsService {

    private final static String USER_NOT_FOUND_MSG = "user with email %s not found";
    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final JwtService jwtService;
     AppUserService(UserRepository userRepository, JwtService jwtService){
        this.userRepository = userRepository;
         this.jwtService = jwtService;
     }
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email).orElseThrow(()->
                new UsernameNotFoundException(String.format(USER_NOT_FOUND_MSG , email)));
    }
    public AppUser getCurrentUser() throws UserNotFoundException {
        try {
            int userId = jwtService.extractUserIdFromToken();
            Optional<AppUser> appUser = userRepository.findById(userId);
            return appUser.orElseThrow(UserNotFoundException::new);
        } catch (Exception e) {
            // Log the exception or handle it according to your application's error handling strategy
            throw new UserNotFoundException("Failed to retrieve current user", e);
        }
    }
    public AppUser getCurrentUserByEmail(String email) throws UserNotFoundException {
        try {
            Optional<AppUser> appUser = userRepository.findByEmail(email);
            return appUser.orElseThrow(UserNotFoundException::new);
        } catch (Exception e) {
            // Log the exception or handle it according to your application's error handling strategy
            throw new UserNotFoundException("Failed to retrieve current user", e);
        }
    }

    public void verifyUser(String vcode) throws UserNotFoundException {
        Optional<AppUser> userOptional = userRepository.findByVcode(vcode);
        userOptional.ifPresent(user -> {
            user.setIsVerified(true);
            userRepository.save(user);
        });
    }
}
