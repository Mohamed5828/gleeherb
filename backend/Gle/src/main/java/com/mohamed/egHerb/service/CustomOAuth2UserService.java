package com.mohamed.egHerb.service;

import com.mohamed.egHerb.entity.AppUser;
import com.mohamed.egHerb.entity.AppUserRole;
import com.mohamed.egHerb.dao.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        processUserDetails(oAuth2User);
        return oAuth2User;
    }

    public void processUserDetails(OAuth2User oAuth2User) {
        Map<String, Object> attributes = oAuth2User.getAttributes();
        String email = (String) attributes.get("email");
        String firstName = (String) attributes.get("given_name");
        String lastName = (String) attributes.get("family_name");

        Optional<AppUser> existingUser = userRepository.findByEmail(email);
        if (existingUser.isPresent()) {
            AppUser user = existingUser.get();
            user.setFirstName(firstName);
            user.setLastName(lastName);
            if (user.getAppUserRole() == null) {
                user.setAppUserRole(AppUserRole.USER); // Set a default role (adjust as needed)
            }
            userRepository.save(user);
        } else {
            AppUser newUser = new AppUser();
            newUser.setEmail(email);
            newUser.setFirstName(firstName);
            newUser.setLastName(lastName);
            newUser.setAppUserRole(AppUserRole.USER);
            newUser.setPassword(passwordEncoder.encode("RpR2qwuAcwTp4VcCwE54lFXH37YNs@QVdO6IoWasdC"));
            userRepository.save(newUser);
        }
        // You may perform additional steps like authentication if needed
    }
}
