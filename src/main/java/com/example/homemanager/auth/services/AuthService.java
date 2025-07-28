package com.example.homemanager.auth.services;

import com.example.homemanager.auth.models.TokenDocument;
import com.example.homemanager.auth.models.requests.LoginRequest;
import com.example.homemanager.auth.models.requests.RegisterRequest;
import com.example.homemanager.auth.models.responses.TokenResponse;
import com.example.homemanager.auth.repository.TokenRepository;
import com.example.homemanager.domain.documents.UserDocument;
import com.example.homemanager.domain.repositories.UserRepository;
import com.example.homemanager.utils.TokenType;
import com.example.homemanager.utils.exceptions.UserAlreadyExistsException;
import com.example.homemanager.utils.exceptions.UserCredentialsException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;

@Transactional
@Service
@Slf4j
@AllArgsConstructor
public class AuthService implements IAuthService {

    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public TokenResponse register(RegisterRequest request) {

        log.info("Register attempt | username='{}'", request.getUsername());

        if (userRepository.existsByUsername(request.getUsername())) {
            log.info("Register - Username already in use: {}", request.getUsername());
            throw new UserAlreadyExistsException(request.getUsername());
        } else {
            UserDocument userToPersist = UserDocument.builder()
                    .username(request.getUsername())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .email(request.getEmail())
                    .housesId(new HashSet<>())
                    .build();

            var userPersisted = userRepository.save(userToPersist);
            var jwtToken = jwtService.generateToken(userPersisted);
            var refreshToken = jwtService.generateRefreshToken(userPersisted);
            saveUserToken(userPersisted, jwtToken);

            log.info("Register successful | username='{}'", userPersisted.getUsername());

            return new TokenResponse(jwtToken, refreshToken);
        }
    }

    public TokenResponse login(LoginRequest request) {

        log.info("Login attempt | username='{}'", request.getUsername());

        var authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        if (!authentication.isAuthenticated()) {
            log.warn("Login failed (not authenticated) | username='{}'", request.getUsername());
            System.out.println(">>> LANZANDO UserCredentialsException");
            throw new UserCredentialsException();
        }

        var user = userRepository.findByUsername(request.getUsername());

        if (user == null) {
            log.warn("Login failed (user not found after authentication) | username='{}'", request.getUsername());
            throw new UserCredentialsException();
        }

        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        revokeAllUserTokens(user);
        saveUserToken(user, jwtToken);

        log.info("Login successful | username='{}'", user.getUsername());

        return new TokenResponse(jwtToken, refreshToken);
    }


    public void revokeAllUserTokens(UserDocument user) {
        List<TokenDocument> validUserTokens = tokenRepository.findAllValidTokensByUsername(user.getUsername());
        if (!validUserTokens.isEmpty()) {
            for (TokenDocument token : validUserTokens) {
                token.setExpired(true);
                token.setRevoked(true);
            }
            log.info("Tokens revoked | username='{}'", user.getUsername());
            tokenRepository.saveAll(validUserTokens);
        } else {
            log.info("No Tokens to revoke | username='{}'", user.getUsername());
        }
    }

    @Override
    public TokenResponse refresh(String authHeader) {

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new IllegalArgumentException("Invalid Bearer token");
        }

        String refreshToken = authHeader.substring(7);
        String userName = jwtService.extractUsername(refreshToken);

        if (userName == null) {
            throw new IllegalArgumentException("Invalid Bearer token");
        }

        UserDocument user = userRepository.findByUsername(userName);

        if (!jwtService.isTokenValid(refreshToken, user)) {
            throw new IllegalArgumentException("Invalid Refresh token");
        }

        String accessToken = jwtService.generateToken(user);
        revokeAllUserTokens(user);
        saveUserToken(user, accessToken);
        return new TokenResponse(accessToken, refreshToken);

    }


    private void saveUserToken(UserDocument user, String jwtToken) {
        var token = TokenDocument.builder()
                .user(user.getUsername())
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();

        tokenRepository.save(token);
        log.info("New token saved for user: {}", user.getUsername());


    }
}
