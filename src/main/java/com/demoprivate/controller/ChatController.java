package com.demoprivate.controller;

import com.demoprivate.model.Chat;
import com.demoprivate.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
public class ChatController {

    @Autowired
    ChatService chatService;

    @PostMapping("/aggiungi")
    public ResponseEntity<?> nuovaChat(@RequestBody Chat chat){
        List<Chat> listaChat = chatService.readAll();

        boolean isPresent = false;

        if(listaChat.size() > 0){
            for(Chat c : listaChat){
                if(c.getUser_1().equals(chat.getUser_1()) && c.getUser_2().equals(chat.getUser_2())){
                    isPresent = true;
                }
            }
        }

        if(isPresent){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(Collections.singletonMap("error", "Attenzione: chat già presente"));
        }else{
            Chat addedChat = chatService.newChat(chat);
            Map<String, Object> res = new HashMap<>();
            res.put("success", "Nuova chat aggiunta con successo");
            res.put("chat", addedChat);
            return ResponseEntity.ok().body(res);
        }
    }

    @GetMapping("/chat-list")
    public ResponseEntity<?> leggiChat(@RequestParam("email") String email){
        List<Chat> chatList = chatService.readAllByMail(email);

        if(chatList.size() < 1){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.singletonMap("error", "Non ci sono chat avviate"));
        }else{
            return ResponseEntity.ok().body(Collections.singletonMap("success" , chatList));
        }
    }
}
