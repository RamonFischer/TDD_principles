package com.example.TDD.services.Imp;

import com.example.TDD.entities.User;
import com.example.TDD.entities.UserDTO;
import com.example.TDD.repo.UserRepository;
import com.example.TDD.services.Exceptions.CantSaveUser;
import com.example.TDD.services.Exceptions.ObjectNotFoundException;
import com.example.TDD.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImp implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper mapper;


    public User saveUser(UserDTO user){
        findByEmail(user);
            return userRepository.save(mapper.map(user,User.class));
    }

    public User findById(Integer id){
        Optional<User> user = userRepository.findById(id);
        return user.orElseThrow(() -> new ObjectNotFoundException("Object Not found!"));
    }

    public List<User> findAll(){
        return userRepository.findAll();
    }

    public void findByEmail(UserDTO user){
        Optional<User> test = userRepository.findByEmail(user.getEmail());
        if(test.isPresent() && !test.get().getId().equals(user.getId())){
            throw new CantSaveUser("Email Already Created!");
        }
    }

    @Override
    public User updateUser(UserDTO user) {
        findByEmail(user);
        return userRepository.save(mapper.map(user,User.class));
    }

    @Override
    public void deleteUser(Integer user) {
        findById(user);
        userRepository.deleteById(user);
    }
}
