package com.example.homemanager.infraestructure.services;

import com.example.homemanager.api.models.request.HouseRequest;
import com.example.homemanager.api.models.responses.HouseResponse;
import com.example.homemanager.api.models.responses.MemberResponse;
import com.example.homemanager.api.models.responses.ShoppingItemResponse;
import com.example.homemanager.api.models.responses.TaskResponse;
import com.example.homemanager.auth.aspects.CheckHouseAccess;
import com.example.homemanager.domain.documents.HouseDocument;
import com.example.homemanager.domain.documents.ShoppingItemDocument;
import com.example.homemanager.domain.documents.TaskDocument;
import com.example.homemanager.domain.documents.UserDocument;
import com.example.homemanager.domain.repositories.HouseRepository;
import com.example.homemanager.domain.repositories.ShoppingItemRepository;
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
import java.util.stream.Collectors;

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
    private final ShoppingItemRepository shoppingItemRepository;


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

        var membersId = new HashSet<String>();
        var tasksId = new HashSet<String>();
        var shoppingItemsId = new HashSet<String>();

        membersId.add(idUserCreator);


        HouseDocument houseToPersist = HouseDocument.builder()
                .name(request.getName())
                .creatorId(idUserCreator)
                .address(request.getAddress())
                .membersId(membersId)
                .tasksId(tasksId)
                .shoppingItemsId(shoppingItemsId)
                .points(new HashMap<>())
                .build();

        var housePersisted = houseRepository.save(houseToPersist);

        // Añadir al usuario la referencia de la nueva casa
        var userToUpdate = userRepository.findById(idUserCreator).orElseThrow(() -> new IdNotFoundException(UserDocument.class.getSimpleName(),idUserCreator));
        userToUpdate.getHousesId().add(housePersisted.getId());
        userRepository.save(userToUpdate);

        log.info("House {} saved.", housePersisted.getName());

        return entityToResponse(housePersisted);
    }

    public Set<TaskResponse> getHouseTasks(String id) {

        var casa = houseRepository.findById(id).orElseThrow(() -> new IdNotFoundException(HouseDocument.class.getSimpleName(), id));

        List<TaskDocument> tasks = taskRepository.findAllById(casa.getTasksId());

        Set<TaskResponse> tasksResponse = new HashSet<>();
        tasks.forEach(task -> tasksResponse.add(entityTaskToResponse(task)));

        log.info("Tasks consultation of house: {}", id);

        return tasksResponse;

    }

    public Set<ShoppingItemResponse> getHouseShoppingItems(String id) {

        var casa = houseRepository.findById(id).orElseThrow(() -> new IdNotFoundException(HouseDocument.class.getSimpleName(),id));

        return shoppingItemRepository.findAllById(casa.getShoppingItemsId())
                .stream()
                .map(this::entityShoppingItemToResponse)
                .collect(Collectors.toSet());
    }

    @Override
    public HouseResponse read(String id) {

        var casa = houseRepository.findById(id).orElseThrow(() -> new IdNotFoundException(HouseDocument.class.getSimpleName(),id));

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
        response.setAssignedMember(entityUserToMemberResponse(userRepository.findById(entity.getAssignedUserId()).orElseThrow(() -> new IdNotFoundException(UserDocument.class.getSimpleName(), entity.getAssignedUserId()))));
        return response;

    }

    private ShoppingItemResponse entityShoppingItemToResponse(ShoppingItemDocument entity) {
        var response = new ShoppingItemResponse();
        BeanUtils.copyProperties(entity, response);
        return response;
    }

}
