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
        UserDocument userDocument = userRepository.findByUsername(username);
        if (userDocument == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return new CustomUserDetails(
                userDocument.getUsername(),
                userDocument.getPassword(),
                userDocument.getId(), // Asumiendo que el ID está disponible aquí
                userDocument.getEmail(),
                userDocument.getCasas(),
                new ArrayList<>()
        );
    }
}
