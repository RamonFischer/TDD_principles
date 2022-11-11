package com.example.TDD.resources;

import com.example.TDD.entities.User;
import com.example.TDD.entities.UserDTO;
import com.example.TDD.services.Imp.UserServiceImp;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@SpringBootTest
class UserResourceTest {

    @InjectMocks
    private UserResource controller;
    @Mock
    private ModelMapper mapper;
    @Mock
    private UserServiceImp serviceImp;

    public static final int ID = 1;
    public static final String NAME = "ramon";
    public static final String EMAIL = "ramon@hotmaillll";
    public static final String PASSWORD = "aa";

    public static final int INDEX = 0;

    private User user;

    private UserDTO userDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        startUser();
    }

    @Test
    void whenFindByIdThenReturnSuccess() {
        when(serviceImp.findById(anyInt())).thenReturn(user);
        when(mapper.map(any(),any())).thenReturn(userDTO);
        ResponseEntity<UserDTO> response = controller.findById(ID);

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(ResponseEntity.class,response.getClass());
        assertEquals(UserDTO.class,response.getBody().getClass());
        assertEquals(ID,response.getBody().getId());
        assertEquals(EMAIL,response.getBody().getEmail());
        assertEquals(NAME,response.getBody().getName());
        assertEquals(PASSWORD,response.getBody().getPassword());

    }

    @Test
    void whenFindAllThenReturnSuccess() {
        when(serviceImp.findAll()).thenReturn(List.of(user));
        when(mapper.map(any(), any())).thenReturn(userDTO);

        ResponseEntity<List<UserDTO>> response = controller.findAll();

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(ArrayList.class, response.getBody().getClass());
        assertEquals(EMAIL,response.getBody().get(0).getEmail());
        assertEquals(UserDTO.class,response.getBody().get(INDEX).getClass());
    }

    @Test
    void whenSaveUserThenReturnSuccess() {
        when(serviceImp.saveUser(userDTO)).thenReturn(user);
        when(mapper.map(any(),any())).thenReturn(userDTO);

        ResponseEntity<UserDTO> response = controller.saveUser(userDTO);

        assertNotNull(response);
        assertNull(response.getBody());
        assertEquals(ResponseEntity.class,response.getClass());
        assertEquals(HttpStatus.CREATED,response.getStatusCode());
        assertNotNull(response.getHeaders().get("Location"));
    }

    @Test
    void updateUsers() {
    }

    @Test
    void deleteUser() {
    }

    private void startUser(){
        user = new User(ID, NAME, EMAIL, PASSWORD);
        userDTO = new UserDTO(ID, NAME, EMAIL, PASSWORD);
    }
}