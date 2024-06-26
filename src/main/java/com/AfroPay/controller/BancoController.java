package com.AfroPay.controller;

import com.AfroPay.service.BancoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BancoController {

    @PostMapping("/banco/{id}/sacar")
    public ResponseEntity<Void> sacar(@PathVariable Long id, @RequestParam(name = "valor") Double valor) {
        boolean sucesso = bancoService.sacar(id, valor);
        if (sucesso) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/banco/{id}/depositar")
    public ResponseEntity<Void> depositar(@PathVariable Long id, @RequestParam(name = "valor",defaultValue = "") Double valor) {
        boolean sucesso = bancoService.depositar(id, valor);
        if (sucesso) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/banco/{idOrigem}/transferir/{idDestino}")
    public ResponseEntity<Void> transferir(@PathVariable Long idOrigem, @PathVariable Long idDestino, @RequestParam(name = "valor") Double valor) {
        boolean sucesso = bancoService.transferir(idOrigem, idDestino, valor);
        if (sucesso) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/banco/{id}/pagamento")
    public ResponseEntity<Void> realizarPagamento(@PathVariable Long id, @RequestParam(name = "valor") Double valor) {
        boolean sucesso = bancoService.realizarPagamento(id, valor);
        if (sucesso) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/banco/{id}/pix")
    public ResponseEntity<Void> realizarPix(@PathVariable Long id, @RequestParam(name = "valor") Double valor) {
        boolean sucesso = bancoService.realizarPix(id, valor);
        if (sucesso) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @Autowired
    private BancoService bancoService;
}
