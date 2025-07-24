package com.example.sacBack.services;

import com.example.sacBack.repositories.UserRepository;
import com.example.sacBack.security.models.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(long id) {return userRepository.getReferenceById(id);}

    public void deleteUser(long id){
        userRepository.deleteById(id);
    }
}
