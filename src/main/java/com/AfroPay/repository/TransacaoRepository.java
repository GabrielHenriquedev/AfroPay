package com.AfroPay.repository;

import com.AfroPay.model.entity.Transacao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransacaoRepository  extends JpaRepository<Transacao, Long> {
}
