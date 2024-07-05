package com.demoprivate.model;

import javax.persistence.*;

@Entity
@Table(name = "utente")
public class User {

    @Id
    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "user_password", nullable = false)
    private String password;

    @Column(name = "username")
    private String username;

    @Column(name = "nome" , nullable = false)
    private String nome;

    @Column(name = "cognome" ,nullable = false)
    private String cognome;

    @Column(name = "data_nascita", nullable = false)
    private String dataNascita;

    @Column(name = "sesso", nullable = false)
    private String sesso;

    public User(){}

    public User(String email, String password, String username, String nome, String cognome, String dataNascita, String sesso) {
        this.email = email;
        this.password = password;
        this.username = username;
        this.nome = nome;
        this.cognome = cognome;
        this.dataNascita = dataNascita;
        this.sesso = sesso;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getDataNascita() {
        return dataNascita;
    }

    public void setDataNascita(String dataNascita) {
        this.dataNascita = dataNascita;
    }

    public String getSesso() {
        return sesso;
    }

    public void setSesso(String sesso) {
        this.sesso = sesso;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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
