package com.example.homemanager.controllers;

import com.example.homemanager.api.controllers.HouseController;
import com.example.homemanager.api.models.request.HouseRequest;
import com.example.homemanager.api.models.responses.HouseResponse;
import com.example.homemanager.api.models.responses.MemberResponse;
import com.example.homemanager.api.models.responses.ShoppingItemResponse;
import com.example.homemanager.api.models.responses.TaskResponse;
import com.example.homemanager.auth.config.JwtAuthenticationFilter;
import com.example.homemanager.domain.documents.HouseDocument;
import com.example.homemanager.domain.repositories.HouseRepository;
import com.example.homemanager.infraestructure.abstract_services.IHouseService;
import com.example.homemanager.utils.exceptions.IdNotFoundException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(HouseController.class)
@AutoConfigureMockMvc(addFilters = false)
class HouseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private JwtAuthenticationFilter jwtAuthenticationFilter;
    @MockBean
    private HouseRepository houseRepository;
    @MockBean
    private IHouseService houseService;
    @Autowired
    private ObjectMapper objectMapper;

    private HouseResponse houseResponse;

    @BeforeEach
    void setUp() {
        String userId = "userId";

        MemberResponse memberResponse = MemberResponse.builder()
                .id(userId)
                .username("userName")
                .email("userEmail")
                .build();

        houseResponse = HouseResponse.builder()
                .id("houseId")
                .name("HouseTestName")
                .address("HouseTestAddress")
                .creatorId(userId)
                .members(new HashSet<>(Collections.singletonList(memberResponse)))
                .tasks(new HashSet<>())
                .build();
    }

    @Test
    void testHouseCreation_Successful() throws Exception {
        // Arrange
        HouseRequest houseRequest = HouseRequest.builder()
                .name("HouseTestName")
                .address("HouseTestAddress")
                .build();

        // Mocking the service to return the mock token response
        Mockito.when(houseService.create(any(HouseRequest.class)))
                .thenReturn(houseResponse);

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.post("/house")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(houseRequest)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("houseId"))
                .andExpect(jsonPath("$.name").value("HouseTestName"))
                .andExpect(jsonPath("$.address").value("HouseTestAddress"))
                .andExpect(jsonPath("$.creatorId").value("userId"))
                .andExpect(jsonPath("$.members").isArray())
                .andExpect(jsonPath("$.members[0].id").value("userId"))
                .andExpect(jsonPath("$.members[0].username").value("userName"))
                .andExpect(jsonPath("$.members[0].email").value("userEmail"))
                .andExpect(jsonPath("$.tasks").isArray())
                .andExpect(jsonPath("$.tasks", hasSize(0)));
    }

    @Test
    void testHouseCreation_ValidationError() throws Exception {
        // Arrange: Crear una HouseRequest sin nombre
        HouseRequest houseRequest = HouseRequest.builder()
                .address("HouseTestAddress") // Solo la dirección
                .build();

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.post("/house")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(houseRequest)))
                .andDo(print())
                .andExpect(status().isBadRequest()); // Esperamos un error de validación
    }

    @Test
    void testGetHouseById_Successful() throws Exception {
        // Arrange
        String houseId = "houseId";

        Mockito.when(houseService.read(houseId)).thenReturn(houseResponse);

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/house/" + houseId))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(houseId));
    }

    @Test
    void testGetHouseById_NotFound() throws Exception {
        // Arrange
        String houseId = "nonExistentId";

        Mockito.when(houseService.read(houseId)).thenThrow(new IdNotFoundException(HouseDocument.class.getSimpleName(),houseId));

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/house/" + houseId))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void testGetHouseTasks_Success() throws Exception {
        // Arrange
        String houseId = "houseId";

        TaskResponse task = TaskResponse.builder()
                .id("taskId")
                .houseId(houseId)
                .name("TaskName")
                .build();

        Set<TaskResponse> tasks = new HashSet<>();
        tasks.add(task);

        Mockito.when(houseService.getHouseTasks(houseId)).thenReturn(tasks);

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/house/getTasks/" + houseId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    void testGetHouseTasks_NotFound() throws Exception {
        // Arrange
        String houseId = "nonExistentId";
        Mockito.when(houseService.getHouseTasks(houseId)).thenThrow(new IdNotFoundException(HouseDocument.class.getSimpleName(),houseId));

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/house/getTasks/" + houseId))
                .andExpect(status().isNotFound());
    }

    @Test
    void testGetHouseShoppingItems_Success() throws Exception {
        // Arrange
        String houseId = "houseId";

        ShoppingItemResponse shoppingItemResponse = ShoppingItemResponse.builder()
                .id("itemId")
                .itemName("itemName")
                .build();

        Set<ShoppingItemResponse> shoppingItems = new HashSet<>();
        shoppingItems.add(shoppingItemResponse);

        Mockito.when(houseService.getHouseShoppingItems(houseId)).thenReturn(shoppingItems);

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/house/getShoppingItems/" + houseId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    void testGetHouseShoppingItems_NotFound() throws Exception {
        // Arrange
        String houseId = "nonExistentId";
        Mockito.when(houseService.getHouseShoppingItems(houseId)).thenThrow(new IdNotFoundException(HouseDocument.class.getSimpleName(),houseId));

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/house/getShoppingItems/" + houseId))
                .andExpect(status().isNotFound());
    }

    @Test
    void testDeleteHouse_Success() throws Exception {
        // Arrange
        String houseId = "houseId";

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.delete("/house/" + houseId))
                .andExpect(status().isNoContent()); // Esperamos un código 204 No Content
    }

    @Test
    void testDeleteHouse_NotFound() throws Exception {
        // Arrange
        String houseId = "nonExistentId";
        Mockito.doThrow(new IdNotFoundException(HouseDocument.class.getSimpleName(),houseId)).when(houseService).delete(houseId);

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.delete("/house/" + houseId))
                .andExpect(status().isNotFound());
    }





}
