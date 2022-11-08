package com.example.TDD.services.Imp;

import com.example.TDD.entities.User;
import com.example.TDD.entities.UserDTO;
import com.example.TDD.repo.UserRepository;
import com.example.TDD.services.Exceptions.CantSaveUser;
import com.example.TDD.services.Exceptions.ObjectNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@SpringBootTest
class UserServiceImpTest {

    public static final int ID = 1;
    public static final String NAME = "ramon";
    public static final String EMAIL = "ramon@hotmaillll";
    public static final String PASSWORD = "aa";
    @InjectMocks
    private UserServiceImp serviceImp;
    @Mock
    private UserRepository userRepository;
    @Mock
    private ModelMapper mapper;

    private User user;

    private UserDTO userDTO;

    private Optional<User> optionalUser;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        startUser();
    }

    @Test
    void saveUser() {
    }

    @Test
    void whenFindByIdThenReturnUserInstance() {
       when(userRepository.findById(Mockito.anyInt())).thenReturn(optionalUser);

        User response = serviceImp.findById(ID);

        assertNotNull(response);

        Assertions.assertEquals(User.class,response.getClass());
    }

    @Test
    void whenFindByIdThenReturnObjectNotFound() {
    when(userRepository.findById(Mockito.anyInt())).thenThrow(new ObjectNotFoundException("Object not found!"));
        try{
            User response = serviceImp.findById(ID);
        }
        catch (Exception e){
            assertEquals(e.getMessage(),"Object not found!");
            assertEquals(ObjectNotFoundException.class,e.getClass());
        }
    }

    @Test
    void whenCreateReturnNewUser() {
        when(userRepository.save(any())).thenReturn(user);

        User response = serviceImp.saveUser(userDTO);

        assertEquals(User.class,response.getClass());
    }

    @Test
    void whenCreateReturnError() {
        when(userRepository.findByEmail(anyString())).thenReturn(optionalUser);

        try{
            optionalUser.get().setId(2);
            serviceImp.saveUser(userDTO);
        }catch (Exception e){
            assertEquals(CantSaveUser.class,e.getClass());
            assertEquals("Email Already Created!",e.getMessage());
        }
        }

    @Test
    void whenFindAllReturnList() {
        when(userRepository.findAll()).thenReturn(List.of(user));
        List<User> response = serviceImp.findAll();
        assertNotNull(response);
        assertEquals(1,response.get(0).getId());
        assertEquals(userRepository.findAll(),response);
    }

    @Test
    void whenUpdateUserReturnSuccess() {
        when(userRepository.findByEmail(anyString())).thenReturn(optionalUser); //o certo seria usando o metodo save, mas eu por preferencia usei o findByEmail

        serviceImp.updateUser(userDTO);
        assertEquals("ramon@hotmaillll",user.getEmail());
    }

    @Test
    void whenUpdateReturnError() {
        when(userRepository.findByEmail(anyString())).thenThrow(new CantSaveUser("Email Already Created!"));;

        try{
            optionalUser.get().setId(2);
            serviceImp.saveUser(userDTO);
        }catch (Exception e){
            assertEquals(CantSaveUser.class,e.getClass());
            assertEquals("Email Already Created!",e.getMessage());
        }
    }

    @Test
    void whenDeleteReturnSuccess() {
        when(userRepository.findById(anyInt())).thenReturn(optionalUser);
        doNothing().when(userRepository).deleteById(anyInt());
        serviceImp.deleteUser(ID);
        verify(userRepository,times(1)).deleteById(any());
    }

    @Test
    void whenDeleteReturnError() {
        when(userRepository.findById(anyInt())).thenThrow(new ObjectNotFoundException("Object Not found!"));
     try {
         serviceImp.deleteUser(ID);
     }catch (Exception e){
         assertEquals(ObjectNotFoundException.class,e.getClass());
         assertEquals("Object Not found!",e.getMessage());
     }
    }



    private void startUser(){
       user = new User(ID, NAME, EMAIL, PASSWORD);
       userDTO = new UserDTO(ID, NAME, EMAIL, PASSWORD);
       optionalUser = Optional.of(new User(ID, NAME, EMAIL, PASSWORD));
    }
}