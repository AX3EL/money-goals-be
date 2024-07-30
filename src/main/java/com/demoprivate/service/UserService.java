package com.demoprivate.service;

import com.demoprivate.dto.UserImg;
import com.demoprivate.dto.UserPssw;
import com.demoprivate.model.User;
import com.demoprivate.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User addUser(User user) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Random random = new Random();
        String prog = String.valueOf(random.nextInt(90000000) + 10000000);
        user.setProgressivo(prog);
        return userRepository.saveAndFlush(user);
    }

    public void updateUserPssw(UserPssw userPssw){
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        User user = getUserByEmail(userPssw.getEmail());
        user.setPassword(passwordEncoder.encode(userPssw.getPassword()));
        userRepository.saveAndFlush(user);
    }

    public List<User> readAll(){
        return userRepository.findAll();
    }

    public void updateProgressivo(String email, String progressivo) {
        Optional<User> optionalUser = userRepository.findById(email);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            if(progressivo == null){
                user.setProgressivo(progressivo);
            }else{
                Random random = new Random();
                String prog = String.valueOf(random.nextInt(90000000) + 10000000);
                user.setProgressivo(prog);
            }
            userRepository.save(user);
        }
    }

    public User getUserByEmail(String email) {
        return userRepository.findById(email).orElse(null);
    }

    public void updateImg(UserImg userImg){
        User user = userRepository.findById(userImg.getEmail()).orElse(null);
        if (user != null) {
            user.setFotoProfilo(userImg.getImg());
            userRepository.saveAndFlush(user);
        }
    }

    public void updateUsername(String email, String username){
        User user = userRepository.findById(email).orElse(null);
        if (user != null) {
            user.setUsername(username);
            userRepository.saveAndFlush(user);
        }
    }
}