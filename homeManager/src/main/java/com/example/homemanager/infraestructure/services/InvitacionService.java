package com.example.homemanager.infraestructure.services;

import com.example.homemanager.api.models.request.InvitacionRequest;
import com.example.homemanager.api.models.responses.InvitacionResponse;
import com.example.homemanager.domain.documents.InvitacionDocument;
import com.example.homemanager.domain.repositories.CasaRepository;
import com.example.homemanager.domain.repositories.InvitacionRepository;
import com.example.homemanager.domain.repositories.UserRepository;
import com.example.homemanager.infraestructure.abstract_services.IInvitacionService;
import com.example.homemanager.utils.EstadoInvitacion;
import com.example.homemanager.utils.InvitacionVigente;
import com.example.homemanager.utils.exceptions.InvitacionFinalizadaException;
import com.example.homemanager.utils.exceptions.UserAlreadyAddedInCasa;
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
public class InvitacionService implements IInvitacionService {

    private final CasaRepository casaRepository;
    private final UserRepository userRepository;
    private final InvitacionRepository invitacionRepository;
    private final CasaService casaService;

    @Override
    public InvitacionResponse create(InvitacionRequest request) {

        var casa = casaRepository.findById(request.getIdCasa()).orElseThrow();
        var user = userRepository.findByEmail(request.getEmail());

        InvitacionDocument invitacionPersisted;

        if (!casa.getIdMiembros().contains(user.getId())) {

            InvitacionDocument invitacionToPersist = InvitacionDocument.builder()
                    .email(request.getEmail())
                    .idCasa(request.getIdCasa())
                    .estado(EstadoInvitacion.PENDIENTE)
                    .vigente(InvitacionVigente.VIGENTE)
                    .build();


            invitacionPersisted = invitacionRepository.save(invitacionToPersist);

            log.info("Invitación a {} creada correctamente", invitacionPersisted.getEmail());

        } else {
            log.info("El usuario {} ya es miembro de la casa con ID {}", user.getEmail(), request.getIdCasa());

            throw new UserAlreadyAddedInCasa("El usuario ya pertenece a la casa.");
        }

        return entityToResponse(invitacionPersisted);
    }

    @Override
    public InvitacionResponse read(String s) {
        return null;
    }

    public InvitacionResponse updateEstadoInvitacion(String id, EstadoInvitacion estado) {

        var invitacionToUpdate = invitacionRepository.findById(id).orElseThrow();
        invitacionToUpdate.setEstado(estado);

        if (invitacionToUpdate.getVigente().equals(InvitacionVigente.VIGENTE)) {

            if (estado.equals(EstadoInvitacion.ACEPTADA)) {

                var casa = casaRepository.findById(invitacionToUpdate.getIdCasa()).orElseThrow();
                var user = userRepository.findByEmail(invitacionToUpdate.getEmail());

                casaService.addMember(casa.getId(), user.getId());

                log.info("Invitación aceptada y finalizada -> Miembro: {} añadido a la casa {}", user.getId(), casa.getId());

                invitacionToUpdate.setVigente(InvitacionVigente.FINALIZADA);

            } else if (estado.equals(EstadoInvitacion.RECHAZADA)) {

                invitacionToUpdate.setVigente(InvitacionVigente.FINALIZADA);

            }

            invitacionRepository.save(invitacionToUpdate);

        } else {
            log.info("La invitación tiene estado FINALIZADO y no se puede modificar.");
            throw new InvitacionFinalizadaException("La invitación ya ha sido usada y no se puede modificar.");
        }
        return entityToResponse(invitacionToUpdate);

    }

    public Set<InvitacionResponse> getInvitaciones(String email) {

        Set<InvitacionResponse> invitacionesToResponse = new HashSet<>();

        invitacionRepository
                .findByEmail(email)
                .forEach(invitacion -> invitacionesToResponse.add(entityToResponse(invitacion)));

        return invitacionesToResponse;


    }


    @Override
    public InvitacionResponse update(InvitacionRequest request, String s) {
        return null;
    }

    @Override
    public void delete(String id) {

        invitacionRepository.deleteById(id);


    }

    private InvitacionResponse entityToResponse(InvitacionDocument entity) {
        var response = new InvitacionResponse();
        BeanUtils.copyProperties(entity, response);
        return response;

    }

}
