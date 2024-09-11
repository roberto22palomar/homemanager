package com.example.homemanager.infraestructure.services;

import com.example.homemanager.api.models.request.UserRequest;
import com.example.homemanager.api.models.responses.UserResponse;
import com.example.homemanager.domain.documents.CasaDocument;
import com.example.homemanager.domain.documents.UserDocument;
import com.example.homemanager.domain.repositories.CasaRepository;
import com.example.homemanager.domain.repositories.UserRepository;
import com.example.homemanager.infraestructure.abstract_services.IUserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
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
                .password(request.getPassword())
                .email(request.getEmail())
                .casas(request.getIdCasas())
                .build();


        var userPersisted = userRepository.save(userToPersist);

        log.info("Usuario {} creado correctamente", userPersisted.getUsername());

        return entityToResponse(userPersisted);
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
