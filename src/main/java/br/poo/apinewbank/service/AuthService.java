package br.poo.apinewbank.service;

import br.poo.apinewbank.dto.UserDTO;
import br.poo.apinewbank.entity.UserEntity;
import br.poo.apinewbank.repository.CostumerRepository;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@Service
@Scope("singleton") // So exite uma instancia dessa classe
public class AuthService {

    // Numero de digitos da conta pode ter até 8 digitos
    private final int MAX_ACCOUNT_NUMBER = (int)Math.pow(10, 8);
    Random rand = new Random();
    CostumerRepository repository = new CostumerRepository();

    public String login(UserDTO user){

        String cpf = user.getCpf();
        String password = user.getPassword();

        for(UserEntity u : repository.getUsers()){
            if(u.getPassword().equals(password) && u.getCpf().equals(cpf)) {
                return u.getToken();
            }
        }
        return null;
    }


    private String convertIntToAccountNumber(int accountNumber) {
        int account = accountNumber/10; // Primeiros digitos
        int digit = accountNumber - account*10; // Ultimo digito
        return String.format("%07d-%d", account, digit);
    }

    public String generateAccountNumber() {
        int accountNumber = rand.nextInt(MAX_ACCOUNT_NUMBER);

        while (repository.accountAlreadyExists(accountNumber)) {
            accountNumber = rand.nextInt(MAX_ACCOUNT_NUMBER);
        }
        repository.includeNewAccountNumber(accountNumber); // Registra novo numero de conta

        return convertIntToAccountNumber(accountNumber);
    }

    public String getCurrentDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return java.time.LocalDateTime.now().format(formatter);
    }

    public boolean isValidCPF(String CPF) {
        return CPF.length() == 11 && CPF.matches("[0-9]+");
    }

    public int signup(UserDTO user) {

        // Regra 1: Validar o nome do usuario: Precisa ter pelo menos duas palavras
        if (user.getName().trim().equals("") || user.getName().trim().split(" ").length < 2) {
            return 1;
        }

        // Regra 2: Validar o CPF
        if (!isValidCPF(user.getCpf().trim())) {
            return 2;
        }
        // Regra 3: Validar a senha: Minimo 6 caracteres (No trabalho fazer mais firula)
        if (user.getPassword().length() < 6) {
            return 3;
        }

        UserEntity entity = new UserEntity();

        entity.setName(user.getName());
        entity.setAccountNumber(generateAccountNumber());
        entity.setCpf(user.getCpf());
        entity.setPassword(user.getPassword());
        String token = UUID.randomUUID().toString();
        entity.setToken(token);
        repository.addUser(entity); //NA VERSAO FINAL DO PROJETO DEVE GRAVAR NO BANCO DE DADOS
        // DENTRO DA REPOSITORY

        user.setAccountNumber(entity.getAccountNumber());

        return 0;
    }

}
