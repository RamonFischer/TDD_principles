package com.example.TDD.resources;

import com.example.TDD.entities.UserDTO;
import com.example.TDD.services.Imp.UserServiceImp;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/user")
public class UserResource {

    @Autowired
    private ModelMapper mapper;
    @Autowired
    private  UserServiceImp userService;

    private static final String ID = "/{id}";

    @GetMapping(value = "/{id}")
    ResponseEntity<UserDTO> findById(@PathVariable Integer id){
        return ResponseEntity.ok().body(mapper.map(userService.findById(id),UserDTO.class));
    }

    @PostMapping(value = "/saveUser")
    ResponseEntity<UserDTO> saveUser(@RequestBody UserDTO user){
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest().path(ID).buildAndExpand(userService.saveUser(user).getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @GetMapping(value = "/findAll")
    public ResponseEntity<List<UserDTO>> findAll() {
        return ResponseEntity.ok().body(userService.findAll()
                .stream().map(x -> mapper.map(x, UserDTO.class)).collect(Collectors.toList()));
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
