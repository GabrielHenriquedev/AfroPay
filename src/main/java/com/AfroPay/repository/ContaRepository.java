package com.AfroPay.repository;

import com.AfroPay.model.entity.Conta;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ContaRepository extends JpaRepository<Conta, Long> {
}
