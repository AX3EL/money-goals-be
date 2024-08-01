package com.demoprivate.service;

import com.demoprivate.model.Chat;
import com.demoprivate.repository.ChatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChatService {

    @Autowired
    ChatRepository chatRepository;

    public Chat newChat(Chat chat){
        return chatRepository.saveAndFlush(chat);
    }

    public Chat readChat(Integer id){
        return chatRepository.findById(id).orElse(null);
    }

    public List<Chat> readAll(){
        return chatRepository.findAll();
    }

    public List<Chat> readAllByMail(String email){
        return chatRepository.findAllByEmail(email);
    }

}
