package com.example.graphql.service;

import com.example.graphql.model.User;
import com.example.graphql.model.UserRequest;
import com.example.graphql.repo.UserRepository;
import org.springframework.data.jpa.repository.Query;
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

    public User getUserByID(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException("userNotFound"));
    }

    public User getUser(String userName) {
        return userRepository.findUserByUserName(userName);
    }

    public User createUser(UserRequest userRequest) {
        User user = User.builder().userName(userRequest.getUserName())
                .id(userRequest.getId())
                .mail(userRequest.getMail())
                .role(userRequest.getRole())
                .build();
        return userRepository.save(user);
    }

    public User updateUser(UserRequest userRequest) {
        User existing = getUserByID(userRequest.getId());
        existing.setRole(userRequest.getRole());
        existing.setUserName(userRequest.getUserName());
        existing.setMail(userRequest.getMail());
        return userRepository.save(existing);
    }


    public void deleteUser(Long id) {
        User existing = getUserByID(id);
        userRepository.delete(existing);
    }
}
