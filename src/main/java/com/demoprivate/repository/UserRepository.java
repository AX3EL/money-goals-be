package com.demoprivate.repository;

import com.demoprivate.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {// la chiave primaria per l'entità Utente è di tipo String
}
