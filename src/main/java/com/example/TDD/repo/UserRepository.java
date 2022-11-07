package com.example.TDD.repo;

import com.example.TDD.entities.User;
import com.example.TDD.entities.UserDTO;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User,Integer> {
    List<User> findAll();

    Optional<User> findByEmail(String email);
}
