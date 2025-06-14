package com.moneyly.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Date;
import java.util.UUID;

@Getter
@Setter
public class Goal {

    @JsonProperty("id")
    private UUID id;

    @JsonProperty("obiettivo")
    private BigDecimal obiettivo;

    @JsonProperty("descrizione")
    private String descrizione;

    @JsonProperty("data_inizio")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dataInizio;

    @JsonProperty("data_fine")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dataFine;

    @JsonProperty("data_creazione")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dataCreazione;

    public Goal() {
        // Costruttore vuoto
    }

    public Goal(UUID id, BigDecimal obiettivo, String descrizione, LocalDate dataInizio, LocalDate dataFine, LocalDate dataCreazione) {
        this.id = id;
        this.obiettivo = obiettivo;
        this.descrizione = descrizione;
        this.dataInizio = dataInizio;
        this.dataFine = dataFine;
        this.dataCreazione = dataCreazione;
    }
}
