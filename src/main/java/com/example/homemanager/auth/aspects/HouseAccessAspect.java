package com.example.homemanager.auth.aspects;


import com.example.homemanager.domain.documents.HouseDocument;
import com.example.homemanager.domain.repositories.HouseRepository;
import com.example.homemanager.domain.repositories.UserRepository;
import com.example.homemanager.utils.exceptions.IdNotFoundException;
import com.example.homemanager.utils.exceptions.UserNotMemberOfHouse;
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

    @Before("execution(* com.example.homemanager.infraestructure.services.HouseService.*(..)) && args(houseId,..) && !execution(* findUserHouses(..))")
    public void checkHouseAccess(String houseId) {
        if (houseId == null) return; // seguridad extra por si acaso

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        String userId = userRepository.findByUsername(username).getId();

        HouseDocument house = houseRepository.findById(houseId)
                .orElseThrow(() -> new IdNotFoundException(HouseDocument.class.getSimpleName(), houseId));

        if (!house.getMembersId().contains(userId)) {
            throw new UserNotMemberOfHouse(userId, houseId);
        }
    }
}