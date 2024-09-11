package com.example.homeManager.infraestructure.services;

import com.example.homeManager.api.models.request.CasaRequest;
import com.example.homeManager.api.models.responses.CasaResponse;
import com.example.homeManager.api.models.responses.MiembroResponse;
import com.example.homeManager.api.models.responses.TareaResponse;
import com.example.homeManager.domain.documents.CasaDocument;
import com.example.homeManager.domain.documents.TareaDocument;
import com.example.homeManager.domain.documents.UserDocument;
import com.example.homeManager.domain.repositories.CasaRepository;
import com.example.homeManager.domain.repositories.TareaRepository;
import com.example.homeManager.domain.repositories.UserRepository;
import com.example.homeManager.infraestructure.abstract_services.ICasaService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Transactional
@Service
@Slf4j
@AllArgsConstructor
public class CasaService implements ICasaService {

    private final CasaRepository casaRepository;
    private final UserRepository userRepository;
    private final TareaRepository tareasRepository;

    private CasaResponse entityToResponse(CasaDocument entity) {
        var response = new CasaResponse();
        BeanUtils.copyProperties(entity, response);
        var casaResponse = new CasaResponse();
        BeanUtils.copyProperties(entity, casaResponse);

        var users = new HashSet<>(userRepository.findAllById(entity.getIdMiembros()));
        Set<MiembroResponse> miembros = new HashSet<>();
        users.forEach(user -> miembros.add(entityUserToMiembroResponse(user)));

        response.setMiembros(miembros);
        response.setTareas(new HashSet<>(tareasRepository.findAllById(entity.getIdTareas())));

        return response;

    }

    private MiembroResponse entityUserToMiembroResponse(UserDocument entity) {
        var response = new MiembroResponse();
        BeanUtils.copyProperties(entity, response);
        return response;
    }

    @Override
    public CasaResponse create(CasaRequest request) {

        CasaDocument casaToPersist = CasaDocument.builder()
                .nombre(request.getNombre())
                .idMiembros(request.getIdMiembros())
                .idTareas(request.getIdTareas())
                .build();

        var casaPersisted = casaRepository.save(casaToPersist);

        log.info("Casa {} creada correctamente", casaPersisted.getNombre());

        return entityToResponse(casaPersisted);
    }

    public Set<TareaResponse> getTareasCasa(String id) {

        List<TareaDocument> tareas = tareasRepository.findTareasCasa(id);

        Set<TareaResponse> tareasResponse = new HashSet<>();

        tareas.forEach(tarea -> {
            tareasResponse.add(entityTareaToResponse(tarea));
        });

        log.info("Consulta de las tareas de la casa con id: {}", id);

        return tareasResponse;


    }

    ;

    @Override
    public CasaResponse read(String s) {
        return null;
    }

    @Override
    public CasaResponse update(CasaRequest request, String s) {
        return null;
    }

    @Override
    public void delete(String id) {

        tareasRepository.deleteAll(tareasRepository.findTareasCasa(id));
        log.info("Tareas de la casa {} han sido borradas correctamente.", id);

        casaRepository.deleteById(id);
        log.info("Casa {} ha sido borrado correctamente.", id);

    }


    private TareaResponse entityTareaToResponse(TareaDocument entity) {
        var response = new TareaResponse();
        BeanUtils.copyProperties(entity, response);
        var tareaResponse = new TareaResponse();
        BeanUtils.copyProperties(entity, tareaResponse);
        response.setUsuarioAsignado(entityUserToMiembroResponse(userRepository.findById(entity.getIdUsuarioAsignado()).orElseThrow()));
        return response;

    }

}
