package com.example.homeManager.infraestructure.services;

import com.example.homeManager.api.models.request.TareaRequest;
import com.example.homeManager.api.models.responses.MiembroResponse;
import com.example.homeManager.api.models.responses.TareaResponse;
import com.example.homeManager.domain.documents.TareaDocument;
import com.example.homeManager.domain.documents.UserDocument;
import com.example.homeManager.domain.repositories.TareaRepository;
import com.example.homeManager.domain.repositories.UserRepository;
import com.example.homeManager.infraestructure.abstract_services.ITareaService;
import com.example.homeManager.utils.EstadoTarea;
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
                .build();


        var tareaPersisted = tareasRepository.save(tareaToPersist);

        log.info("Tarea {} creada correctamente", tareaPersisted.getNombre());

        return entityToResponse(tareaPersisted);
    }

    public TareaResponse updateEstado(String id, EstadoTarea estado) {

        var tareaToUpdate = tareasRepository.findById(id).orElseThrow();
        tareaToUpdate.setEstado(estado);
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
