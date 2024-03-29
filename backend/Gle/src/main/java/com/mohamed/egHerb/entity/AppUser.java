package com.mohamed.egHerb.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user")
public class AppUser extends AbstractEntityModel implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;
    @Column(name = "mobile")
    private long mobile;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "vcode")
    private String vcode;

    @Column(name = "is_verified")
    private Boolean isVerified ;

    @Enumerated(EnumType.STRING)
    private AppUserRole appUserRole;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Review> reviews;

    @JsonManagedReference
    @OneToMany(mappedBy = "user" , cascade = CascadeType.ALL , orphanRemoval = true)
    private List<CartItem> cartItems;

    @JsonManagedReference
    @OneToMany(mappedBy = "user" , cascade = CascadeType.ALL ,fetch = FetchType.LAZY, orphanRemoval = true)
    private List<UserAddress> userAddresses;

    @OneToMany(mappedBy = "user")
    private List<OrderDetail> orderDetails;

    @PrePersist
    public void prePersist() {
        if (isVerified == null) {
            isVerified = false;
        }
        if (appUserRole == null) {
            appUserRole = AppUserRole.USER;
        }
    }

    public AppUser(int userId) {
        id= userId;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(appUserRole.name());
        return Collections.singletonList(authority);
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String toString() {
        return "AppUser{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", mobile=" + mobile +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", appUserRole=" + appUserRole +
                ", cartItems=" + cartItems +
                ", userAddresses=" + userAddresses +
                ", orderDetails=" + orderDetails +
                '}';
    }
}

