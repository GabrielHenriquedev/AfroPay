package com.AfroPay.model.entity;


import jakarta.persistence.*;

@Entity
@DiscriminatorValue("PAGAMENTO")
public class ContaPagamento extends Conta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double limiteTransferencia = 4999.99;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public double getLimiteTransferencia() {
        return limiteTransferencia;
    }


    public void setLimiteTransferencia(Double limiteTransferencia) {
        this.limiteTransferencia = limiteTransferencia;
    }
}
