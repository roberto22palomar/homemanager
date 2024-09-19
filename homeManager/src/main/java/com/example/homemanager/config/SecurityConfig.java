package com.example.homemanager.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private static final String LOGIN_RESOURCE = "/login";
    private static final String[] USER_RESOURCE = {"/dashboard/**", "/casa/**", "/invitacion/**", "/tarea/**"};

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                // Configuración de autorización de solicitudes
                .authorizeHttpRequests(authorizeRequests ->
                        authorizeRequests
                                // Permitir acceso sin autenticación a la página de login y recursos estáticos
                                .requestMatchers("/login", "/css/**", "/js/**").permitAll()
                                .requestMatchers("/user").permitAll()
                                // Proteger las rutas de usuario
                                .requestMatchers(USER_RESOURCE).authenticated()
                                .anyRequest().authenticated()
                )
                // Configuración del login
                .formLogin(form -> form
                        .loginProcessingUrl("/login") // URL para procesar la solicitud de login
                        .defaultSuccessUrl("/dashboard.html", true)
                        .permitAll()  // Permitir acceso sin autenticación a la página de login
                )
                // Configuración del logout
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/")
                        .invalidateHttpSession(true)
                ).userDetailsService(userDetailsService());

        return http.build();
    }


    @Bean
    public UserDetailsService userDetailsService() {
        return new MyUserDetailsService(); // Tu implementación de UserDetailsService
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // Utiliza BCrypt para codificar contraseñas
    }

}
