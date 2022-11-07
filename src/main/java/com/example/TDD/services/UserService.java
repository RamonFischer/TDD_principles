package com.example.TDD.services;

import com.example.TDD.entities.User;
import com.example.TDD.entities.UserDTO;
import com.example.TDD.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface UserService {
    User findById(Integer id);
    User saveUser(UserDTO user);

    List<User> findAll();

    User updateUser(UserDTO user);

    void deleteUser(Integer user) ;
}
