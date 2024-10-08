package com.example.homemanager.auth.aspects;


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

    @Before("execution(* com.example.homemanager.infraestructure.services.HouseService.*(..)) && args(houseId,..)")
    public void checkHouseAccess(String houseId) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        var userId = userRepository.findByUsername(username).getId();

        var house = houseRepository.findById(houseId)
                .orElseThrow(() -> new IdNotFoundException("House not found."));

        if (!house.getMembersId().contains(userId)) {
            throw new UserNotMemberOfHouse("Access Denied: User isn't member of this house.");
        }
    }
}