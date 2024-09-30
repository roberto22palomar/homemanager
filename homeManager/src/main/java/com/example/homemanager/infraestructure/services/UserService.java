package com.example.homemanager.infraestructure.services;

import com.example.homemanager.api.models.responses.UserResponse;
import com.example.homemanager.auth.models.requests.RegisterRequest;
import com.example.homemanager.domain.documents.UserDocument;
import com.example.homemanager.domain.repositories.HouseRepository;
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
    private final HouseRepository houseRepository;
    private final PasswordEncoder passwordEncoder;

    private UserResponse entityToResponse(UserDocument entity) {
        var response = new UserResponse();
        BeanUtils.copyProperties(entity, response);
        var userResponse = new UserResponse();

        BeanUtils.copyProperties(entity, userResponse);

        response.setHousesId(entity.getHousesId());

        return response;

    }

    @Override
    public UserResponse create(RegisterRequest request) {

        UserDocument userToPersist = UserDocument.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .email(request.getEmail())
                .housesId(new HashSet<>())
                .build();


        var userPersisted = userRepository.save(userToPersist);

        log.info("User {} saved.", userPersisted.getUsername());

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



}
