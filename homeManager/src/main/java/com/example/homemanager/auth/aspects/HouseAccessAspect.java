package com.example.homemanager.auth.aspects;


import com.example.homemanager.domain.repositories.HouseRepository;
import com.example.homemanager.domain.repositories.UserRepository;
import com.example.homemanager.utils.exceptions.IdNotFoundException;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
public class HouseAccessAspect {

    private final HouseRepository houseRepository;
    private final UserRepository userRepository;

    // El aspecto se ejecutará antes de cualquier método de HouseService que tenga houseId como argumento
    @Before("execution(* com.example.homemanager.infraestructure.services.HouseService.*(..)) && args(houseId,..)")
    public void checkHouseAccess(String houseId) {
        // Obtener el usuario autenticado
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        // Buscar al usuario en la base de datos
        var userId = userRepository.findByUsername(username).getId();

        // Buscar la casa en la base de datos
        var house = houseRepository.findById(houseId)
                .orElseThrow(() -> new IdNotFoundException("Casa no encontrada"));

        // Verificar si el usuario es miembro de la casa
        if (!house.getMembersId().contains(userId)) {
            throw new RuntimeException("Acceso denegado: El usuario no pertenece a esta casa.");
        }
    }
}