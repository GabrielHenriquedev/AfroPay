package com.AfroPay.controller;

import com.AfroPay.model.entity.Usuario;
import com.AfroPay.repository.UsuarioRepository;
import com.AfroPay.service.BancoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class UsuarioController {

    // http://localhost:8080/hello
    @GetMapping("/hello")
    public String hello(){
        return "Salve, mundão!";
    }

    @GetMapping(path = "/usuario/{id}")
    public Usuario procuraPorId(@PathVariable Long id) {
        return usuarioRepository.findById(id).orElseThrow(() -> new RuntimeException("Usuario não encontrado"));
    }

    @GetMapping(path = "/usuario")
    public List<Usuario> getSerie(){

        return usuarioRepository.findAll();
    }

    @PostMapping(path = "/usuario")
    public ResponseEntity<Usuario> criarUsuario(@RequestBody Usuario novoUsuario) {
        Usuario usuarioCriado = bancoService.criarUsuarioComConta(novoUsuario);
        return new ResponseEntity<>(usuarioCriado, HttpStatus.CREATED);
    }

    @PutMapping("/usuario/{id}")
    public Usuario atualizarUsuario(@PathVariable Long id, @RequestBody Usuario usuario){
       return bancoService.atualizarUsuario(id, usuario);
    }

    @DeleteMapping("/{id}/delete")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id){
        usuarioRepository.deleteById(id);
    }

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private BancoService bancoService;
}
