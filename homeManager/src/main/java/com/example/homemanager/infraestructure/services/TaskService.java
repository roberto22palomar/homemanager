package com.example.homemanager.infraestructure.services;

import com.example.homemanager.api.models.request.TaskRequest;
import com.example.homemanager.api.models.responses.MemberResponse;
import com.example.homemanager.api.models.responses.TaskResponse;
import com.example.homemanager.domain.documents.TaskDocument;
import com.example.homemanager.domain.documents.UserDocument;
import com.example.homemanager.domain.repositories.HouseRepository;
import com.example.homemanager.domain.repositories.TaskRepository;
import com.example.homemanager.domain.repositories.UserRepository;
import com.example.homemanager.infraestructure.abstract_services.ITaskService;
import com.example.homemanager.utils.TaskStatus;
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
public class TaskService implements ITaskService {

    private final TaskRepository taskRepository;
    private final HouseRepository houseRepository;
    private final UserRepository userRepository;

    private static final String HOUSE_NOT_FOUND = "House not found with that ID.";
    private static final String TASK_NOT_FOUND = "Task not found with that ID.";
    private static final String USER_NOT_FOUND = "User not found with that ID.";

    @Override
    public TaskResponse create(TaskRequest request) {

        TaskDocument taskToPersist = TaskDocument.builder()
                .name(request.getName())
                .description(request.getDescription())
                .creationDate(LocalDateTime.now())
                .expirationDate(request.getExpirationDate())
                .status(TaskStatus.NOT_STARTED)
                .periodicity(request.getPeriodicity())
                .assignedUserId(request.getAssignedMemberId())
                .houseId(request.getHouseId())
                .points(request.getPoints())
                .assignedPoints(false)
                .build();


        var taskPersisted = taskRepository.save(taskToPersist);

        var casa = houseRepository.findById(request.getHouseId()).orElseThrow(() -> new IdNotFoundException(HOUSE_NOT_FOUND));
        casa.getTasksId().add(taskPersisted.getId());
        houseRepository.save(casa);

        log.info("Task {} saved and added to house {}.", taskPersisted.getName(), taskPersisted.getHouseId());

        return entityToResponse(taskPersisted);
    }

    public TaskResponse updateStatus(String id, TaskStatus status) {

        var taskToUpdate = taskRepository.findById(id)
                .orElseThrow(() -> new IdNotFoundException(TASK_NOT_FOUND));

        var house = houseRepository.findById(taskToUpdate.getHouseId())
                .orElseThrow(() -> new IdNotFoundException(HOUSE_NOT_FOUND));

        if (status.equals(TaskStatus.COMPLETED)) {

            if (!taskToUpdate.isAssignedPoints()) {
                house.getPoints().merge(taskToUpdate.getAssignedUserId(), taskToUpdate.getPoints(), Integer::sum);
                taskToUpdate.setAssignedPoints(true);
                log.info("Points added for task {} to user {}.", taskToUpdate.getName(), taskToUpdate.getAssignedUserId());
            }

            // Verificar si la tarea estaba en estado "FINALIZADA" y se está cambiando a otro estado
        } else if (taskToUpdate.getStatus().equals(TaskStatus.COMPLETED) && taskToUpdate.isAssignedPoints()) {

            // Si los puntos ya fueron asignados, restarlos al cambiar de estado
            house.getPoints().merge(taskToUpdate.getAssignedUserId(), -taskToUpdate.getPoints(), Integer::sum);
            taskToUpdate.setAssignedPoints(false);  // Marcar que los puntos fueron removidos
            log.info("Points removed for task {} to user {}.", taskToUpdate.getName(), taskToUpdate.getAssignedUserId());
        }

        // Guardar la casa actualizada
        houseRepository.save(house);

        // Actualizar el estado de la tarea
        taskToUpdate.setStatus(status);
        taskRepository.save(taskToUpdate);

        // Retornar la tarea actualizada como respuesta
        return entityToResponse(taskToUpdate);
    }


    @Override
    public TaskResponse read(String s) {
        return null;
    }

    @Override
    public TaskResponse update(TaskRequest request, String s) {

        return null;
    }


    @Override
    public void delete(String id) {

        taskRepository.deleteById(id);
        log.info("Task {} deleted.", id);

    }


    private TaskResponse entityToResponse(TaskDocument entity) {
        var response = new TaskResponse();
        BeanUtils.copyProperties(entity, response);
        response.setAssignedMember(entityUserToMemberResponse(userRepository.findById(entity.getAssignedUserId()).orElseThrow(() -> new IdNotFoundException(USER_NOT_FOUND))));
        return response;

    }

    private MemberResponse entityUserToMemberResponse(UserDocument entity) {
        var response = new MemberResponse();
        BeanUtils.copyProperties(entity, response);
        return response;
    }
}
