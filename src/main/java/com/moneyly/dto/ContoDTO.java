package com.moneyly.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
public class ContoDTO {

    private UUID id;
    private String nomeConto;
    private String tipoConto;
    private BigDecimal budgetIniziale;
    private BigDecimal saldo;
    private UUID utenteId;
    private boolean primario;

    public ContoDTO(){}

    public ContoDTO(UUID id, String nomeConto, String tipoConto, BigDecimal budgetIniziale, BigDecimal saldo, UUID utenteId, boolean primario) {
        this.id = id;
        this.nomeConto = nomeConto;
        this.tipoConto = tipoConto;
        this.budgetIniziale = budgetIniziale;
        this.saldo = saldo;
        this.utenteId = utenteId;
        this.primario = primario;
    }
}
