package com.AfroPay.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "tipo_conta", discriminatorType = DiscriminatorType.STRING)
public abstract class Conta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "usuario_id")
    @JsonBackReference
    private Usuario usuario;

    private double saldo;
    private int saquesGratuitos = 4;
    private int saquesRealizados = 0;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public int getSaquesGratuitos() {
        return saquesGratuitos;
    }

    public void setSaquesGratuitos(int saquesGratuitos) {
        this.saquesGratuitos = saquesGratuitos;
    }

    public int getSaquesRealizados() {
        return saquesRealizados;
    }

    public void setSaquesRealizados(int saquesRealizados) {
        this.saquesRealizados = saquesRealizados;
    }
}
