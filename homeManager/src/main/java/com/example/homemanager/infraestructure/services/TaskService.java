package com.example.homemanager.infraestructure.services;

import com.example.homemanager.api.models.request.TaskRequest;
import com.example.homemanager.api.models.responses.MemberResponse;
import com.example.homemanager.api.models.responses.TaskResponse;
import com.example.homemanager.auth.aspects.CheckHouseAccess;
import com.example.homemanager.domain.documents.HouseDocument;
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
@CheckHouseAccess
public class TaskService implements ITaskService {

    private final TaskRepository taskRepository;
    private final HouseRepository houseRepository;
    private final UserRepository userRepository;

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

        var casa = houseRepository.findById(request.getHouseId()).orElseThrow(() -> new IdNotFoundException(HouseDocument.class.getSimpleName(), request.getHouseId()));
        casa.getTasksId().add(taskPersisted.getId());
        houseRepository.save(casa);

        log.info("Task {} saved and added to house {}.", taskPersisted.getName(), taskPersisted.getHouseId());

        return entityToResponse(taskPersisted);
    }

    public TaskResponse updateStatus(String id, TaskStatus status) {

        var taskToUpdate = taskRepository.findById(id)
                .orElseThrow(() -> new IdNotFoundException(TaskDocument.class.getSimpleName(), id));

        var house = houseRepository.findById(taskToUpdate.getHouseId())
                .orElseThrow(() -> new IdNotFoundException(HouseDocument.class.getSimpleName(), taskToUpdate.getHouseId()));

        if (status.equals(TaskStatus.COMPLETED)) {

            if (!taskToUpdate.isAssignedPoints()) {
                house.getPoints().merge(taskToUpdate.getAssignedUserId(), taskToUpdate.getPoints(), Integer::sum);
                taskToUpdate.setAssignedPoints(true);
                log.info("Points added for task {} to user {}.", taskToUpdate.getName(), taskToUpdate.getAssignedUserId());
            }

        } else if (taskToUpdate.getStatus().equals(TaskStatus.COMPLETED) && taskToUpdate.isAssignedPoints()) {

            house.getPoints().merge(taskToUpdate.getAssignedUserId(), -taskToUpdate.getPoints(), Integer::sum);
            taskToUpdate.setAssignedPoints(false);
            log.info("Points removed for task {} to user {}.", taskToUpdate.getName(), taskToUpdate.getAssignedUserId());
        }

        houseRepository.save(house);

        taskToUpdate.setStatus(status);
        taskRepository.save(taskToUpdate);

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

        var task = taskRepository.findById(id).orElseThrow(() -> new IdNotFoundException(TaskDocument.class.getSimpleName(), id));
        var house = houseRepository.findById(task.getHouseId()).orElseThrow(() -> new IdNotFoundException(HouseDocument.class.getSimpleName(), task.getHouseId()));

        //Quitar referencia de la tarea en la casa
        house.getTasksId().remove(task.getId());
        houseRepository.save(house);

        taskRepository.deleteById(id);
        log.info("Task {} deleted.", id);

    }


    private TaskResponse entityToResponse(TaskDocument entity) {
        var response = new TaskResponse();
        BeanUtils.copyProperties(entity, response);
        response.setAssignedMember(entityUserToMemberResponse(userRepository.findById(entity.getAssignedUserId())
                .orElseThrow(() -> new IdNotFoundException(UserDocument.class.getSimpleName(), entity.getAssignedUserId()))));
        return response;

    }

    private MemberResponse entityUserToMemberResponse(UserDocument entity) {
        var response = new MemberResponse();
        BeanUtils.copyProperties(entity, response);
        return response;
    }
}
