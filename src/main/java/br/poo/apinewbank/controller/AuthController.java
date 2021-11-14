package br.poo.apinewbank.controller;

import br.poo.apinewbank.dto.UserDTO;
import br.poo.apinewbank.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

import java.util.List;

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

    @GetMapping("/users")
    public ResponseEntity<List<UserDTO>> getUsers(@RequestParam(required = false) String name) {
        List<UserDTO> lst = service.getUsers(name);
        if (lst.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(lst);
    }


    @DeleteMapping("/users")
    public ResponseEntity<String> deleteUsers(@RequestParam long id) {

        int result = service.deleteUsers(id);
        if(result == 0){
            ResponseEntity.badRequest().body("ID inexistente");
        }
        String s = "Usuario deletado com sucesso";
        return ResponseEntity.ok(s);
    }

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody UserDTO user) {

        int result = service.signup(user);

        if(result == 1) {
            return ResponseEntity.badRequest().body("Nome de usuario não pode estar vazio." +
                    "\nNome de usuário precisa ter duas palavras");
        }
        else if (result == 2) {
            return ResponseEntity.badRequest().body("CPF inválido" +
                    "\nCPF precisa conter 11 digitos " +
                    "\nCPF nao pode conter traços");

        }else if (result == 3) {
            return ResponseEntity.badRequest().body("Senha não pode ser menor que 14 caracteres." +
                    "\nPrecisa conter letras maiúsculas" +
                    "\nPrecisa conter numeros" +
                    "\nprecisa conter símbolos");
        }

        String s = "Conta criada! Seu numero de conta eh " + user.getAccountNumber();
        //return ResponseEntity.ok().build();
        return ResponseEntity.ok(s);
    }
}