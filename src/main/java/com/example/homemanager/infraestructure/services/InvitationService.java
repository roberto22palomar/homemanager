package com.example.homemanager.infraestructure.services;

import com.example.homemanager.api.models.request.InvitationRequest;
import com.example.homemanager.api.models.responses.InvitationResponse;
import com.example.homemanager.domain.documents.HouseDocument;
import com.example.homemanager.domain.documents.InvitationDocument;
import com.example.homemanager.domain.repositories.HouseRepository;
import com.example.homemanager.domain.repositories.InvitationRepository;
import com.example.homemanager.domain.repositories.UserRepository;
import com.example.homemanager.infraestructure.abstract_services.IInvitationService;
import com.example.homemanager.utils.InvitationStatus;
import com.example.homemanager.utils.exceptions.IdNotFoundException;
import com.example.homemanager.utils.exceptions.RevokedInvitationException;
import com.example.homemanager.utils.exceptions.UserAlreadyMemberExepction;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Transactional
@Service
@Slf4j
@AllArgsConstructor
public class InvitationService implements IInvitationService {

    private final HouseRepository houseRepository;
    private final UserRepository userRepository;
    private final InvitationRepository invitationRepository;
    private final HouseService casaService;

    @Override
    public InvitationResponse create(InvitationRequest request) {

        var house = houseRepository.findById(request.getHouseId()).orElseThrow(() -> new IdNotFoundException(HouseDocument.class.getSimpleName(),request.getHouseId()));
        var userInvited = userRepository.findByUsername(request.getInvitedUser());

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userInviting = authentication.getName();

        if (!house.getMembersId().contains(userInvited.getUsername())) {

            InvitationDocument invitationToPersist = InvitationDocument.builder()
                    .invitedUser(userInvited.getUsername())
                    .invitingUser(userInviting)
                    .houseId(request.getHouseId())
                    .status(InvitationStatus.PENDING)
                    .revoked(false)
                    .build();

            InvitationDocument invitationPersisted = invitationRepository.save(invitationToPersist);

            log.info("Invitation was sent to user {} from user {} to house {}.", userInvited.getUsername(), userInviting, house.getName());
            return entityToResponse(invitationPersisted);
        } else {
            log.info("User {} is already member of house {}", userInvited.getUsername(), request.getHouseId());
            throw new UserAlreadyMemberExepction("The user is already a member of that house.");
        }


    }

    @Override
    public InvitationResponse read(String s) {
        return null;
    }

    public InvitationResponse updateInvitationStatus(String id, InvitationStatus status) {

        var invitationToUpdate = invitationRepository.findById(id).orElseThrow(() -> new IdNotFoundException(InvitationDocument.class.getSimpleName(), id));
        invitationToUpdate.setStatus(status);

        if (!invitationToUpdate.isRevoked()) {

            if (status.equals(InvitationStatus.ACCEPTED)) {

                var house = houseRepository.findById(invitationToUpdate.getHouseId()).orElseThrow(() -> new IdNotFoundException(HouseDocument.class.getSimpleName(), invitationToUpdate.getHouseId()));
                var userInvited = userRepository.findByUsername(invitationToUpdate.getInvitedUser());

                //Añadir referencia de casa al usuario
                userInvited.getHousesId().add(house.getId());
                userRepository.save(userInvited);

                //Añadir referencia del usuario a la casa
                house.getMembersId().add(userInvited.getId());
                houseRepository.save(house);

                invitationToUpdate.setRevoked(true);
                log.info("Invitation accepted and finished -> Member: {} added to house {}", userInvited.getId(), house.getId());

            } else if (status.equals(InvitationStatus.REJECTED)) {
                invitationToUpdate.setRevoked(true);
                log.info("Invitation: {} rejected and finished. ", invitationToUpdate.getId());
            }

            invitationRepository.save(invitationToUpdate);

        } else {
            log.info("Invitation with id: {} is revoked and cannot be modified.", invitationToUpdate.getId());
            throw new RevokedInvitationException("The invitation is revoked and cannot be modified.");
        }
        return entityToResponse(invitationToUpdate);

    }

    public Set<InvitationResponse> getInvitations() {

        Set<InvitationResponse> invitationsToResponse = new HashSet<>();

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        invitationRepository
                .findByUsername(username)
                .forEach(invitation -> invitationsToResponse.add(entityToResponse(invitation)));

        return invitationsToResponse;


    }


    @Override
    public InvitationResponse update(InvitationRequest request, String s) {
        return null;
    }

    @Override
    public void delete(String id) {

        invitationRepository.deleteById(id);


    }

    private InvitationResponse entityToResponse(InvitationDocument entity) {
        var response = new InvitationResponse();
        BeanUtils.copyProperties(entity, response);
        return response;

    }

}
