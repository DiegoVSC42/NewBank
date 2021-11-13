package br.poo.apinewbank.controller;

import br.poo.apinewbank.dto.UserDTO;
import br.poo.apinewbank.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

@RestController
public class AuthController {

    @Autowired
    private AuthService service;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserDTO user) {
        // Use CPF e senha para entrar
        String token = service.login(user);

        if(token == null) {
            return ResponseEntity.notFound().build();
        }

        HttpHeaders responseHeader = new HttpHeaders();
        responseHeader.add("Authorization", token);

        return ResponseEntity.ok().headers(responseHeader).build();
    }

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody UserDTO user) {

        int result = service.signup(user);

        if(result == 1) {
            return ResponseEntity.badRequest().body("Nome de usuario invalido.");
        }
        else if (result == 2) {
            return ResponseEntity.badRequest().body("CPF invalido.");
        }
        else if (result == 3) {
            return ResponseEntity.badRequest().body("Senha não pode ser menor que 6 caracteres.");
        }

        String s = "Conta criada! Seu numero de conta eh " + user.getAccountNumber();
        //return ResponseEntity.ok().build();
        return ResponseEntity.ok(s);
    }

}
