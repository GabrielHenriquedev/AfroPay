package com.AfroPay.service;

import com.AfroPay.model.entity.*;
import com.AfroPay.repository.*;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BancoService {

    public Conta findById(Long id) {
        return contaRepository.findById(id).orElseThrow(() -> new RuntimeException("Conta não encontrada"));
    }

    @Transactional
    public Usuario atualizarUsuario(Long id, Usuario usuarioAtt) {
        Usuario usuario = usuarioRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));

        if (usuarioAtt.getEmail() != null) {
            usuario.setEmail(usuarioAtt.getEmail());
        }
        if (usuarioAtt.getEndereco() != null) {
            usuario.setEndereco(usuarioAtt.getEndereco());
        }
        if (usuarioAtt.getRendaSalarial() != null) {
            usuario.setRendaSalarial(usuarioAtt.getRendaSalarial());
        }

        usuarioRepository.save(usuario);

        return usuario;
    }

    @Transactional
    public boolean sacar(Long id, Double valor) {
        Conta conta = findById(id);

        if (conta.getSaldo() >= valor) {
            conta.setSaldo(conta.getSaldo() - valor);
            conta.setSaquesRealizados(conta.getSaquesRealizados() + 1);
            if (conta.getSaquesRealizados() > conta.getSaquesGratuitos()) {
                if(conta.getSaldo() > 6.50){
                    conta.setSaldo(conta.getSaldo() - 6.50); // Taxa de saque adicional
                } else {
                throw new RuntimeException("Saldo insuficiente para a taxa de saque adicional");
            }}
            contaRepository.save(conta);

            Transacao transacao = new Transacao(conta, null, valor, "SAQUE");
            transacaoRepository.save(transacao);

            verificarEConverterParaContaPagamento(conta);

            return true;
        } else {
            return false;
        }
    }

    public boolean depositar(Long id, Double valor){
        Conta conta = findById(id);

        conta.setSaldo(conta.getSaldo() + valor);
        contaRepository.save(conta);

        Transacao transacao = new Transacao(conta, null, valor, "SAQUE");
        transacaoRepository.save(transacao);

        verificarEConverterParaContaPagamento(conta);


        return true;
    }

    @Transactional
    public boolean transferir(Long idOrigem, Long idDestino, double valor) {
        Conta origem = findById(idOrigem);
        Conta destino = findById(idDestino);

        if (origem instanceof ContaPagamento && valor > ((ContaPagamento) origem).getLimiteTransferencia()) {
            return false;
        }

        if (origem.getSaldo() >= valor) {
            origem.setSaldo(origem.getSaldo() - valor);
            destino.setSaldo(destino.getSaldo() + valor);
            contaRepository.save(origem);
            contaRepository.save(destino);

            Transacao transacao = new Transacao(origem, destino, valor, "TRANSFERÊNCIA");
            transacaoRepository.save(transacao);

            verificarEConverterParaContaPagamento(origem);
            verificarEConverterParaContaPagamento(destino);

            return true;
        } else {
            return false;
        }
    }

    public boolean realizarPagamento(Long id, double valor) {
        Conta conta = findById(id);

        if (conta instanceof ContaCorrente) {
            ContaCorrente contaCorrente = (ContaCorrente) conta;
            if (contaCorrente.getSaldo() + contaCorrente.getChequeEspecial() >= valor) {
                return sacar(id, valor, "PAGAMENTO");
            }
        } else if (conta.getSaldo() >= valor) {
            return sacar(id, valor, "PAGAMENTO");
        }
        return false;
    }

    public boolean realizarPix(Long id, double valor) {
        return realizarPagamento(id, valor);
    }

    private boolean sacar(Long id, double valor, String tipo) {
        Conta conta = findById(id);

        if (conta.getSaldo() >= valor) {
            conta.setSaldo(conta.getSaldo() - valor);
            conta.setSaquesRealizados(conta.getSaquesRealizados() + 1);
            if (conta.getSaquesRealizados() > conta.getSaquesGratuitos()) {
                if(conta.getSaldo() > 6.50){
                    conta.setSaldo(conta.getSaldo() - 6.50); // Taxa de saque adicional
                } else {
                    throw new RuntimeException("Saldo insuficiente para a taxa de saque adicional");
                }}
            contaRepository.save(conta);

            Transacao transacao = new Transacao(conta, null, valor, tipo);
            transacaoRepository.save(transacao);

            verificarEConverterParaContaPagamento(conta);

            return true;
        } else {
            return false;
        }
    }

    private void verificarEConverterParaContaPagamento(Conta conta) {
        if (!(conta instanceof ContaPagamento) && conta.getSaldo() > 2500) {
            ContaPagamento contaPagamento = new ContaPagamento();
            contaPagamento.setId(conta.getId());
            contaPagamento.setSaldo(conta.getSaldo());
            contaPagamento.setSaquesRealizados(conta.getSaquesRealizados());
            contaPagamento.setSaquesGratuitos(conta.getSaquesGratuitos());
            contaCorrenteRepository.deleteById(conta.getId());
            contaPagamentoRepository.save(contaPagamento);
        }
    }

    @Transactional
    public Usuario criarUsuarioComConta(Usuario novoUsuario) {
        // Salvar o usuário
        Usuario usuarioSalvo = usuarioRepository.save(novoUsuario);

        // Verificar a renda do usuário para decidir o tipo de conta
        if (usuarioSalvo.getRendaSalarial() < 2500) {
            // Criar e associar uma ContaCorrente
            ContaCorrente contaCorrente = new ContaCorrente();
            contaCorrente.setSaldo(usuarioSalvo.getRendaSalarial().doubleValue());
            contaCorrente.setChequeEspecial(500.00);
            contaCorrente.setUsuario(usuarioSalvo);

            // Salvar a conta corrente
            contaCorrenteRepository.save(contaCorrente);

            // Atualizar o usuário com a conta corrente associada
            usuarioSalvo.setConta(contaCorrente);
        } else {
            // Criar e associar uma ContaPagamento
            ContaPagamento contaPagamento = new ContaPagamento();
            contaPagamento.setSaldo(usuarioSalvo.getRendaSalarial().doubleValue());
            contaPagamento.setLimiteTransferencia(4999.99);
            contaPagamento.setUsuario(usuarioSalvo);

            // Salvar a conta de pagamento
            contaPagamentoRepository.save(contaPagamento);

            // Atualizar o usuário com a conta de pagamento associada
            usuarioSalvo.setConta(contaPagamento);
        }

        // Salvar o usuário novamente com a conta associada
        usuarioSalvo = usuarioRepository.save(usuarioSalvo);

        return usuarioSalvo;
    }

    @Autowired
    private ContaRepository contaRepository;

    @Autowired
    private ContaCorrenteRepository contaCorrenteRepository;

    @Autowired
    private ContaPagamentoRepository contaPagamentoRepository;

    @Autowired
    private TransacaoRepository transacaoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;
}


