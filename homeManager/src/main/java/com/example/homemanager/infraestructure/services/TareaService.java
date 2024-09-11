package com.example.homemanager.infraestructure.services;

import com.example.homemanager.api.models.request.TareaRequest;
import com.example.homemanager.api.models.responses.MiembroResponse;
import com.example.homemanager.api.models.responses.TareaResponse;
import com.example.homemanager.domain.documents.TareaDocument;
import com.example.homemanager.domain.documents.UserDocument;
import com.example.homemanager.domain.repositories.CasaRepository;
import com.example.homemanager.domain.repositories.TareaRepository;
import com.example.homemanager.domain.repositories.UserRepository;
import com.example.homemanager.infraestructure.abstract_services.ITareaService;
import com.example.homemanager.utils.EstadoTarea;
import com.example.homemanager.utils.exceptions.IdNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Transactional
@Service
@Slf4j
@AllArgsConstructor
public class TareaService implements ITareaService {

    private final TareaRepository tareasRepository;
    private final CasaRepository casaRepository;
    private final UserRepository userRepository;

    @Override
    public TareaResponse create(TareaRequest request) {

        TareaDocument tareaToPersist = TareaDocument.builder()
                .nombre(request.getNombre())
                .descripcion(request.getDescripcion())
                .fechaCreacion(LocalDateTime.now())
                .fechaVencimiento(request.getFechaVencimiento())
                .estado(EstadoTarea.SIN_COMENZAR)
                .periodicidad(request.getPeriodicidad())
                .idUsuarioAsignado(request.getIdUsuarioAsignado())
                .idCasa(request.getIdCasa())
                .puntos(request.getPuntos())
                .puntosAsignados(false)
                .build();


        var tareaPersisted = tareasRepository.save(tareaToPersist);

        var casa = casaRepository.findById(request.getIdCasa()).orElseThrow(() -> new IdNotFoundException("Casa no encontrada con ese ID."));
        casa.getIdTareas().add(tareaPersisted.getId());
        casaRepository.save(casa);

        log.info("Tarea {} creada correctamente y agregada a la casa {}.", tareaPersisted.getNombre(), tareaPersisted.getIdCasa());

        return entityToResponse(tareaPersisted);
    }

    public TareaResponse updateEstado(String id, EstadoTarea nuevoEstado) {

        var tareaToUpdate = tareasRepository.findById(id)
                .orElseThrow(() -> new IdNotFoundException("Tarea no encontrada con ese ID."));

        var casa = casaRepository.findById(tareaToUpdate.getIdCasa())
                .orElseThrow(() -> new IdNotFoundException("Casa no encontrada con ese ID."));

        if (nuevoEstado.equals(EstadoTarea.FINALIZADA)) {

            if (!tareaToUpdate.isPuntosAsignados()) {
                casa.getPuntos().merge(tareaToUpdate.getIdUsuarioAsignado(), tareaToUpdate.getPuntos(), Integer::sum);
                tareaToUpdate.setPuntosAsignados(true);
                log.info("Puntos asignados para la tarea {} al usuario {}", tareaToUpdate.getNombre(), tareaToUpdate.getIdUsuarioAsignado());
            }


        } else if (tareaToUpdate.getEstado().equals(EstadoTarea.FINALIZADA) && !nuevoEstado.equals(EstadoTarea.FINALIZADA)) {

            if (tareaToUpdate.isPuntosAsignados()) {
                casa.getPuntos().merge(tareaToUpdate.getIdUsuarioAsignado(), -tareaToUpdate.getPuntos(), Integer::sum);
                tareaToUpdate.setPuntosAsignados(false);  // Marcar que los puntos fueron removidos
                log.info("Puntos removidos para la tarea {} del usuario {}", tareaToUpdate.getNombre(), tareaToUpdate.getIdUsuarioAsignado());
            }
        }


        casaRepository.save(casa);
        tareaToUpdate.setEstado(nuevoEstado);
        tareasRepository.save(tareaToUpdate);
        
        return entityToResponse(tareaToUpdate);
    }


    @Override
    public TareaResponse read(String s) {
        return null;
    }

    @Override
    public TareaResponse update(TareaRequest request, String s) {

        return null;
    }


    @Override
    public void delete(String id) {

        tareasRepository.deleteById(id);


    }


    private TareaResponse entityToResponse(TareaDocument entity) {
        var response = new TareaResponse();
        BeanUtils.copyProperties(entity, response);
        var tareaResponse = new TareaResponse();
        BeanUtils.copyProperties(entity, tareaResponse);
        response.setUsuarioAsignado(entityUserToMiembroResponse(userRepository.findById(entity.getIdUsuarioAsignado()).orElseThrow()));
        return response;

    }

    private MiembroResponse entityUserToMiembroResponse(UserDocument entity) {
        var response = new MiembroResponse();
        BeanUtils.copyProperties(entity, response);
        return response;
    }
}
