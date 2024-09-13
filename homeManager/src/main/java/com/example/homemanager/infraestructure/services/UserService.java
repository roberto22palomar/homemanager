package com.example.homemanager.infraestructure.services;

import com.example.homemanager.api.models.request.LoginRequest;
import com.example.homemanager.api.models.request.UserRequest;
import com.example.homemanager.api.models.responses.UserResponse;
import com.example.homemanager.config.SecurityConfig;
import com.example.homemanager.domain.documents.CasaDocument;
import com.example.homemanager.domain.documents.UserDocument;
import com.example.homemanager.domain.repositories.CasaRepository;
import com.example.homemanager.domain.repositories.UserRepository;
import com.example.homemanager.infraestructure.abstract_services.IUserService;
import com.example.homemanager.utils.exceptions.UserCredentialsException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.User;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Transactional
@Service
@Slf4j
@AllArgsConstructor
public class UserService implements IUserService {

    private final UserRepository userRepository;
    private final CasaRepository casaRepository;
    private final SecurityConfig securityConfig;

    private UserResponse entityToResponse(UserDocument entity) {
        var response = new UserResponse();
        BeanUtils.copyProperties(entity, response);
        var userResponse = new UserResponse();

        Set<CasaDocument> casasUser = new HashSet<>(casaRepository.findAllById(entity.getCasas()));

        BeanUtils.copyProperties(entity, userResponse);

        response.setCasas(casasUser);

        return response;

    }

    @Override
    public UserResponse create(UserRequest request) {

        UserDocument userToPersist = UserDocument.builder()
                .username(request.getUsername())
                .password(securityConfig.passwordEncoder().encode(request.getPassword()))
                .email(request.getEmail())
                .casas(new HashSet<>())
                .build();


        var userPersisted = userRepository.save(userToPersist);

        log.info("Usuario {} creado correctamente", userPersisted.getUsername());

        return entityToResponse(userPersisted);
    }

    public UserResponse checkLogin(LoginRequest request) {

        var user = userRepository.findByUsername(request.getUsername());

        if (user.getPassword().equals(securityConfig.passwordEncoder().encode(request.getPassword()))) {
            log.info("Creedenciales correctas del usuario {}", request.getUsername());

        } else {
            log.info("Creedenciales incorrectas del usuario {}", request.getUsername());
            throw new UserCredentialsException("Las creedenciales son incorrectas");

        }


        return entityToResponse(user);

    }

    @Override
    public UserResponse read(String s) {
        return null;
    }


    @Override
    public UserResponse update(UserRequest request, String s) {
        return null;
    }

    @Override
    public void delete(String s) {

    }
}
