package com.example.homemanager.controllers;

import com.example.homemanager.auth.controller.AuthController;
import com.example.homemanager.auth.models.requests.LoginRequest;
import com.example.homemanager.auth.models.requests.RegisterRequest;
import com.example.homemanager.auth.models.responses.TokenResponse;
import com.example.homemanager.auth.repository.TokenRepository;
import com.example.homemanager.auth.services.IAuthService;
import com.example.homemanager.auth.services.JwtService;
import com.example.homemanager.domain.repositories.UserRepository;
import com.example.homemanager.utils.exceptions.UserAlreadyExists;
import com.example.homemanager.utils.exceptions.UserCredentialsException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthController.class)
@AutoConfigureMockMvc(addFilters = false)
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private JwtService jwtService;
    @MockBean
    private UserDetailsService userDetailsService;
    @MockBean
    private TokenRepository tokenRepository;
    @MockBean
    private UserRepository userRepository;
    @MockBean
    private IAuthService authService;
    @Autowired
    private ObjectMapper objectMapper;
    private TokenResponse mockTokenResponse;

    @BeforeEach
    void setUp() {
        // Setup token response that will be returned by the mocked authService
        mockTokenResponse = new TokenResponse("mockAccessToken", "mockRefreshToken");
    }

    @Test
    void testRegister_Successful() throws Exception {
        // Arrange
        RegisterRequest registerRequest = RegisterRequest.builder()
                .username("username")
                .password("password")
                .email("email@email.com")
                .build();

        // Mocking the service to return the mock token response
        Mockito.when(authService.register(any(RegisterRequest.class)))
                .thenReturn(mockTokenResponse);

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registerRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.access_token").value("mockAccessToken"))
                .andExpect(jsonPath("$.refresh_token").value("mockRefreshToken"));
    }

    @Test
    void testRegister_UserAlreadyExist() throws Exception {
        // Arrange
        RegisterRequest registerRequest = RegisterRequest.builder()
                .username("username")
                .password("password")
                .email("email@email.com")
                .build();

        // Mocking the service to return the mock token response
        Mockito.when(authService.register(any(RegisterRequest.class)))
                .thenThrow(new UserAlreadyExists("User already exist."));

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registerRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testRegister_ValidationError() throws Exception {
        // Arrange
        RegisterRequest registerRequestIncorrect = RegisterRequest.builder()
                .username("username")
                .build();

        // Mocking the service to return the mock token response
        Mockito.when(authService.register(any(RegisterRequest.class)))
                .thenThrow(new UserCredentialsException("Bad Request."));

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registerRequestIncorrect)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testLogin_Successful() throws Exception {
        // Arrange
        LoginRequest loginRequest = LoginRequest.builder()
                .username("username")
                .password("password")
                .build();

        // Mocking the service to return the mock token response
        Mockito.when(authService.login(any(LoginRequest.class)))
                .thenReturn(mockTokenResponse);

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.access_token").value("mockAccessToken"))
                .andExpect(jsonPath("$.refresh_token").value("mockRefreshToken"));
    }

    @Test
    void testLogin_ValidationError() throws Exception {
        // Arrange: Crear un LoginRequest sin usuario y contraseña
        LoginRequest loginRequest = LoginRequest.builder()
                .username("") // Usuario vacío
                .password("") // Contraseña vacía
                .build();

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isForbidden()); // Esperamos un error de validación
    }

    @Test
    void testLogin_UserCredentialsException() throws Exception {
        // Arrange
        LoginRequest loginRequest = LoginRequest.builder()
                .username("username")
                .password("wrongPassword")
                .build();

        // Mocking the service to throw UserCredentialsException
        Mockito.when(authService.login(any(LoginRequest.class)))
                .thenThrow(new UserCredentialsException("Invalid credentials."));

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isForbidden());
    }

    @Test
    void testRefreshToken_Successful() throws Exception {
        // Arrange
        String authHeader = "Bearer mockAccessToken"; // Suponiendo que estás usando un esquema Bearer

        // Mocking the service to return the mock token response
        Mockito.when(authService.refresh(authHeader))
                .thenReturn(mockTokenResponse);

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.post("/auth/refresh")
                        .header(HttpHeaders.AUTHORIZATION, authHeader))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.access_token").value("mockAccessToken"))
                .andExpect(jsonPath("$.refresh_token").value("mockRefreshToken"));
    }

    @Test
    void testRefreshToken_Unauthorized() throws Exception {
        // Arrange
        String authHeader = "Bearer invalidToken"; // Token inválido

        // Mocking the service to throw an exception for invalid token
        Mockito.when(authService.refresh(authHeader))
                .thenThrow(new UserCredentialsException("Invalid token."));

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.post("/auth/refresh")
                        .header(HttpHeaders.AUTHORIZATION, authHeader))
                .andExpect(status().isBadRequest());
    }
}
