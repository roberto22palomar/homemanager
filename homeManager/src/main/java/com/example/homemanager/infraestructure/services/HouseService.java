package com.example.homemanager.infraestructure.services;

import com.example.homemanager.api.models.request.HouseRequest;
import com.example.homemanager.api.models.responses.HouseResponse;
import com.example.homemanager.api.models.responses.MemberResponse;
import com.example.homemanager.api.models.responses.TaskResponse;
import com.example.homemanager.auth.aspects.CheckHouseAccess;
import com.example.homemanager.domain.documents.HouseDocument;
import com.example.homemanager.domain.documents.TaskDocument;
import com.example.homemanager.domain.documents.UserDocument;
import com.example.homemanager.domain.repositories.HouseRepository;
import com.example.homemanager.domain.repositories.TaskRepository;
import com.example.homemanager.domain.repositories.UserRepository;
import com.example.homemanager.infraestructure.abstract_services.IHouseService;
import com.example.homemanager.utils.exceptions.IdNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Transactional
@Service
@Slf4j
@AllArgsConstructor
@CheckHouseAccess
public class HouseService implements IHouseService {

    private static final String HOUSE_NOT_FOUND = "House not found with that ID.";
    private static final String USER_NOT_FOUND = "User not found with that ID.";

    private final HouseRepository houseRepository;
    private final UserRepository userRepository;
    private final TaskRepository taskRepository;


    private HouseResponse entityToResponse(HouseDocument entity) {
        var response = new HouseResponse();
        BeanUtils.copyProperties(entity, response);

        var users = new HashSet<>(userRepository.findAllById(entity.getMembersId()));
        Set<MemberResponse> members = new HashSet<>();
        users.forEach(user -> members.add(entityUserToMemberResponse(user)));

        response.setMembers(members);
        response.setTasks(new HashSet<>(taskRepository.findAllById(entity.getTasksId())));

        return response;

    }

    private MemberResponse entityUserToMemberResponse(UserDocument entity) {
        var response = new MemberResponse();
        BeanUtils.copyProperties(entity, response);
        return response;
    }

    @Override
    public HouseResponse create(HouseRequest request) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userCreator = authentication.getName();

        var idUserCreator = userRepository.findByUsername(userCreator).getId();

        request.getMembersId().add(idUserCreator);

        HouseDocument houseToPersist = HouseDocument.builder()
                .name(request.getName())
                .creatorId(idUserCreator)
                .membersId(request.getMembersId())
                .tasksId(request.getTasksId())
                .points(new HashMap<>())
                .build();

        var housePersisted = houseRepository.save(houseToPersist);

        log.info("House {} saved.", housePersisted.getName());

        return entityToResponse(housePersisted);
    }

    public Set<TaskResponse> getHouseTasks(String id) {

        var casa = houseRepository.findById(id).orElseThrow(() -> new IdNotFoundException(HOUSE_NOT_FOUND));

        List<TaskDocument> tasks = taskRepository.findAllById(casa.getTasksId());
        Set<TaskResponse> tasksResponse = new HashSet<>();

        tasks.forEach(task -> tasksResponse.add(entityTaskToResponse(task)));

        log.info("Tasks consultation of house: {}", id);

        return tasksResponse;


    }

    public HouseResponse addMember(String houseId, String idUser) {

        var houseToUpdate = houseRepository.findById(houseId).orElseThrow(() -> new IdNotFoundException(HOUSE_NOT_FOUND));
        var user = userRepository.findById(idUser).orElseThrow(() -> new IdNotFoundException(USER_NOT_FOUND));

        houseToUpdate.getMembersId().add(user.getId());

        houseToUpdate.getPoints().put(user.getId(), 0);

        var houseUpdated = houseRepository.save(houseToUpdate);

        return entityToResponse(houseUpdated);
    }


    @Override
    public HouseResponse read(String id) {

        var casa = houseRepository.findById(id).orElseThrow(() -> new IdNotFoundException(HOUSE_NOT_FOUND));

        return entityToResponse(casa);
    }

    @Override
    public HouseResponse update(HouseRequest request, String s) {
        return null;
    }

    @Override
    public void delete(String id) {

        taskRepository.deleteAll(taskRepository.findHouseTasks(id));
        log.info("Tasks from house {} have been deleted.", id);

        houseRepository.deleteById(id);
        log.info("House {} has been deleted.", id);

    }


    private TaskResponse entityTaskToResponse(TaskDocument entity) {
        var response = new TaskResponse();
        BeanUtils.copyProperties(entity, response);
        response.setAssignedMember(entityUserToMemberResponse(userRepository.findById(entity.getAssignedUserId()).orElseThrow(() -> new IdNotFoundException(USER_NOT_FOUND))));
        return response;

    }

}
