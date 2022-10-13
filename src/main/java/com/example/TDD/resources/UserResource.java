package com.example.TDD.resources;

import com.example.TDD.entities.User;
import com.example.TDD.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/user")
public class UserResource {


    private final UserService userService;

    @GetMapping(value = "/{id}")
    ResponseEntity<Optional<User>>findById(@PathVariable Integer id){
        return new ResponseEntity<>(userService.findById(id), HttpStatus.ACCEPTED);
    }

    @PostMapping(value = "/saveUser")
    ResponseEntity<User> saveUser(@RequestBody User user){
        return ResponseEntity.ok().body(userService.saveUser(user));
    }
}
