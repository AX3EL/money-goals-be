package com.demoprivate.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "utente")
public class User {

    @Id
    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "user_password", nullable = false)
    private String password;

    @Column(name = "username", unique = true)
    private String username;

    @Column(name = "nome" , nullable = false)
    private String nome;

    @Column(name = "cognome" ,nullable = false)
    private String cognome;

    @Column(name = "data_nascita", nullable = false)
    private String dataNascita;

    @Column(name = "sesso", nullable = false)
    private String sesso;

    @Column(name = "progressivo")
    private String progressivo;

    @Column(name = "foto_profilo")
    private String fotoProfilo;

    public User(){}

    public User(String email, String password, String username, String nome, String cognome, String dataNascita, String sesso, String progressivo, String fotoProfilo) {
        this.email = email;
        this.password = password;
        this.username = username;
        this.nome = nome;
        this.cognome = cognome;
        this.dataNascita = dataNascita;
        this.sesso = sesso;
        this.progressivo = progressivo;
        this.fotoProfilo = fotoProfilo;
    }

    @Override
    public String toString() {
        return "User{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", username='" + username + '\'' +
                ", nome='" + nome + '\'' +
                ", cognome='" + cognome + '\'' +
                ", dataNascita='" + dataNascita + '\'' +
                ", sesso='" + sesso + '\'' +
                '}';
    }
}
