package com.demoprivate.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Setter
@Getter
@Table(name = "chat")
public class Chat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "email_utente_1", nullable = false)
    private User user_1;

    @ManyToOne
    @JoinColumn(name = "email_utente_2", nullable = false)
    private User user_2;

    @Column(name = "conversazione")
    @Lob
    private String conversazione;

    public Chat(){}

    public Chat(Integer id, User user_1, User user_2, String conversazione) {
        this.id = id;
        this.user_1 = user_1;
        this.user_2 = user_2;
        this.conversazione = conversazione;
    }

    @Override
    public String toString() {
        return "Chat{" +
                "id=" + id +
                ", user_1=" + user_1 +
                ", user_2=" + user_2 +
                ", conversazione='" + conversazione + '\'' +
                '}';
    }
}
