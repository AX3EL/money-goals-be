package com.demoprivate.repository;

import com.demoprivate.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, String> {// la chiave primaria per l'entità Utente è di tipo String

    @Query("SELECT u FROM User u WHERE u.email LIKE :n%")
    List<User> findUsersByParam(String n);

}
