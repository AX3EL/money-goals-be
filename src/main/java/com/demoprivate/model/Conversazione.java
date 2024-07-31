package com.demoprivate.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "conversazione_chat")
public class Conversazione {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne
    @JoinColumn(name = "chat_id", nullable = false)
    private Chat chat;

    @OneToOne
    @JoinColumn(name = "email_utente_1", nullable = false)
    private User mittente;

    @OneToOne
    @JoinColumn(name = "email_utente_2", nullable = false)
    private User destinatario;

    @Column(name = "contenuto_chat")
    @Lob
    private String contenutoChat;

    @Column(name = "data_ultimo_messaggio")
    private String dataUltimoMsg;

    public Conversazione(){}

    public Conversazione(Integer id, Chat chat, User mittente, User destinatario, String contenutoChat, String dataUltimoMsg) {
        this.id = id;
        this.chat = chat;
        this.mittente = mittente;
        this.destinatario = destinatario;
        this.contenutoChat = contenutoChat;
        this.dataUltimoMsg = dataUltimoMsg;
    }
}
