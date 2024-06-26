package com.AfroPay.model.entity;

import jakarta.persistence.*;

@Entity
@DiscriminatorValue("CORRENTE")
public class ContaCorrente extends Conta{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double chequeEspecial;

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public Double getChequeEspecial() {
        return chequeEspecial;
    }

    public void setChequeEspecial(Double chequeEspecial) {
        this.chequeEspecial = chequeEspecial;
    }

}
