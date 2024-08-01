package com.demoprivate.repository;

import com.demoprivate.model.Chat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatRepository extends JpaRepository<Chat, Integer> {

    @Query("SELECT c from Chat c where c.user_1.email = :email")
    List<Chat> findAllByEmail(String email);

}
