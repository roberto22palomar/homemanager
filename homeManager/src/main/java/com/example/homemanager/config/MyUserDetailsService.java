package com.example.homemanager.config;

import com.example.homemanager.api.models.responses.UserResponse;
import com.example.homemanager.domain.documents.UserDocument;
import com.example.homemanager.domain.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository; // Repositorio para acceder a los datos del usuario
 //

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDocument userResponse = userRepository.findByUsername(username);
        if (userResponse == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return new org.springframework.security.core.userdetails.User(
                userResponse.getUsername(),
                userResponse.getPassword(),
                new ArrayList<>() // Agrega roles o permisos si es necesario
        );
    }
}
