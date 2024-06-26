package com.AfroPay.repository;

import com.AfroPay.model.entity.ContaPagamento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContaPagamentoRepository  extends JpaRepository<ContaPagamento, Long> {
}
