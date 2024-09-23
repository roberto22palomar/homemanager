package com.example.homemanager.infraestructure.services;

import com.example.homemanager.api.models.request.LoginRequest;
import com.example.homemanager.api.models.responses.UserResponse;
import com.example.homemanager.auth.models.requests.RegisterRequest;
import com.example.homemanager.domain.documents.UserDocument;
import com.example.homemanager.domain.repositories.CasaRepository;
import com.example.homemanager.domain.repositories.UserRepository;
import com.example.homemanager.infraestructure.abstract_services.IUserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;

@Transactional
@Service
@Slf4j
@AllArgsConstructor
public class UserService implements IUserService {

    private final UserRepository userRepository;
    private final CasaRepository casaRepository;
    private final PasswordEncoder passwordEncoder;

    private UserResponse entityToResponse(UserDocument entity) {
        var response = new UserResponse();
        BeanUtils.copyProperties(entity, response);
        var userResponse = new UserResponse();

        BeanUtils.copyProperties(entity, userResponse);

        response.setCasas(entity.getCasas());

        return response;

    }

    @Override
    public UserResponse create(RegisterRequest request) {

        UserDocument userToPersist = UserDocument.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .email(request.getEmail())
                .casas(new HashSet<>())
                .build();


        var userPersisted = userRepository.save(userToPersist);

        log.info("Usuario {} creado correctamente", userPersisted.getUsername());

        return entityToResponse(userPersisted);
    }


    @Override
    public UserResponse read(String id) {

        return null;
    }


    @Override
    public UserResponse update(RegisterRequest request, String s) {
        return null;
    }

    @Override
    public void delete(String s) {
// DE MOMENTO NO HAY DELETE DE USUARIOS
    }

    @Override
    public UserResponse checkLogin(LoginRequest request) {
        return null;
    }


}
