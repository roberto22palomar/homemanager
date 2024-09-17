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

        response.setCasas(entity.getCasas());

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

        // Verifica si el usuario existe
        if (user == null) {
            log.info("Usuario no encontrado: {}", request.getUsername());
            throw new UserCredentialsException("Usuario no encontrado");
        }

        // Compara la contraseña proporcionada con la contraseña almacenada
        if (securityConfig.passwordEncoder().matches(request.getPassword(), user.getPassword())) {
            log.info("Credenciales correctas del usuario {}", request.getUsername());
            return entityToResponse(user);
        } else {
            log.info("Credenciales incorrectas del usuario {}", request.getUsername());
            throw new UserCredentialsException("Las credenciales son incorrectas");
        }
    }

    @Override
    public UserResponse read(String id) {

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
