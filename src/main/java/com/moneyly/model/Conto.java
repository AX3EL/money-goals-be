package com.moneyly.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.lang.Nullable;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
public class Conto {

    @JsonProperty("id")
    private UUID id;

    @JsonProperty("nome_conto")
    private String nomeConto;

    @JsonProperty("tipo_conto")
    private String tipoConto;

    @JsonProperty("budget_iniziale")
    private BigDecimal budgetIniziale;

    @JsonProperty("saldo")
    private BigDecimal saldo;

    @JsonProperty("utente_id")
    private UUID utenteId;

    @JsonProperty("conto_primario")
    private boolean primario;

    public Conto() {
        // Costruttore vuoto
    }

    public Conto(UUID id, String nomeConto, String tipoConto, BigDecimal budgetIniziale, BigDecimal saldo, UUID utenteId, boolean primario) {
        this.id = id;
        this.nomeConto = nomeConto;
        this.tipoConto = tipoConto;
        this.budgetIniziale = budgetIniziale;
        this.saldo = saldo;
        this.utenteId = utenteId;
        this.primario = primario;
    }
}
