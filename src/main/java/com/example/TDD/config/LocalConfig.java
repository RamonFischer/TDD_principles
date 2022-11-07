package com.example.TDD.config;

import com.example.TDD.entities.User;
import com.example.TDD.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.List;

@Configuration
@Profile("local")
public class LocalConfig {
    @Autowired
    UserRepository userRepository;

    @Bean
    public void startDb(){
        User u1 = new User(null,"ramon","ramon@gmail","123");
        User u2 = new User(null,"ramon","ramon@outlook","123");
        User u3 = new User(null,"ramon","ramon@hotmail","123");
        User u4 = new User(null,"ramon","ramon@yahoo","123");

        userRepository.saveAll(List.of(u1,u2,u3,u4));
    }
}
