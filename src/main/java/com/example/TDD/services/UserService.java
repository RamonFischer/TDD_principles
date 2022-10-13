package com.example.TDD.services;

import com.example.TDD.entities.User;
import com.example.TDD.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
     private UserRepository userRepository;

     public User saveUser(User user){
         return userRepository.save(user);
    }

    public Optional<User> findById(Integer id){
        return userRepository.findById(id);
    }
}
