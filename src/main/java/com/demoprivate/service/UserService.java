package com.demoprivate.service;

import com.demoprivate.model.User;
import com.demoprivate.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User addUser(User user) {
        return userRepository.saveAndFlush(user);
    }

    public User getUserByEmail(String email) {
        return userRepository.findById(email).orElse(null);
    }

}