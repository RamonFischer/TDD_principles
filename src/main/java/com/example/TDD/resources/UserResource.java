package com.example.TDD.resources;

import com.example.TDD.entities.User;
import com.example.TDD.entities.UserDTO;
import com.example.TDD.services.Imp.UserServiceImp;
import com.example.TDD.services.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/user")
public class UserResource {

    @Autowired
    private ModelMapper mapper;
    private final UserServiceImp userService;

    @GetMapping(value = "/{id}")
    ResponseEntity<UserDTO> findById(@PathVariable Integer id){
        return ResponseEntity.ok().body(mapper.map(userService.findById(id),UserDTO.class));
    }

    @PostMapping(value = "/saveUser")
    ResponseEntity<UserDTO> saveUser(@RequestBody UserDTO user){
        return ResponseEntity.ok().body(mapper.map(userService.saveUser(user), UserDTO.class));
    }

    @GetMapping(value = "/findAll")
    ResponseEntity<List<UserDTO>> findAll(){
        return ResponseEntity.ok().body(userService.findAll().stream().map(__ -> mapper.map(__,UserDTO.class)).toList());
    }

    @PutMapping(value = "/updateUser/{id}")
    ResponseEntity<UserDTO> updateUsers(@PathVariable Integer id, @RequestBody UserDTO user){
        user.setId(id);
        return ResponseEntity.ok().body(mapper.map(userService.updateUser(user), UserDTO.class));
    }

    @DeleteMapping(value = "/deleteUser/{id}")
    ResponseEntity<UserDTO> deleteUser(@PathVariable Integer id){
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
