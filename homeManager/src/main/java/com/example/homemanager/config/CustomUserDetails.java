package com.example.homemanager.config;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;

public class CustomUserDetails implements UserDetails {

    @Getter
    private String id; // Agrega el campo ID
    private String username;
    private String password;
    @Getter
    private String email;
    @Getter
    private Set<String> casas;
    private Collection<? extends GrantedAuthority> authorities;

    public CustomUserDetails(String username, String password, String id, String email, Set<String> casas, Collection<? extends GrantedAuthority> authorities) {
        this.username = username;
        this.password = password;
        this.id = id;
        this.email = email;
        this.casas = casas;
        this.authorities = authorities;
    }

    // Métodos implementados de UserDetails
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }


    @Override
    public String getUsername() {
        return username;
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

}
