package com.example.homeManager.infraestructure.services;

import com.example.homeManager.api.models.request.InvitacionRequest;
import com.example.homeManager.api.models.responses.InvitacionResponse;
import com.example.homeManager.domain.documents.InvitacionDocument;
import com.example.homeManager.domain.repositories.CasaRepository;
import com.example.homeManager.domain.repositories.InvitacionRepository;
import com.example.homeManager.domain.repositories.UserRepository;
import com.example.homeManager.infraestructure.abstract_services.IInvitacionService;
import com.example.homeManager.utils.EstadoInvitacion;
import com.example.homeManager.utils.InvitacionVigente;
import com.example.homeManager.utils.exceptions.UserAlreadyAddedInCasa;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Transactional
@Service
@Slf4j
@AllArgsConstructor
public class InvitacionService implements IInvitacionService {

    private final CasaRepository casaRepository;
    private final UserRepository userRepository;
    private final InvitacionRepository invitacionRepository;

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
                casa.getIdMiembros().add(user.getId());
                casaRepository.save(casa);

                log.info("Invitación aceptada y finalizada -> Miembro: {} añadido a la casa {}", user.getId(), casa.getId());

                invitacionToUpdate.setVigente(InvitacionVigente.FINALIZADA);

            } else if (estado.equals(EstadoInvitacion.RECHAZADA)) {

                invitacionToUpdate.setVigente(InvitacionVigente.FINALIZADA);

            }

            invitacionRepository.save(invitacionToUpdate);

        } else {


        }
        return entityToResponse(invitacionToUpdate);

    }

    //TODO HACER UN GET MIS INVITACIONES


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
