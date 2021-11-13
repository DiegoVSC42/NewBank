package br.poo.apinewbank.service;

import br.poo.apinewbank.dto.UserDTO;
import br.poo.apinewbank.entity.UserEntity;
import br.poo.apinewbank.repository.AuthRepository;
import br.poo.apinewbank.repository.CostumerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
@Scope("singleton") // So exite uma instancia dessa classe
public class AuthService {

    @Autowired
    private AuthRepository repo;
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
    public List<UserDTO> getUsers(String name) {
        String filter = name != null ? name : "";
        Optional<List<UserEntity>> result = repo.findByNameContaining(filter);

        List<UserDTO> lst = new ArrayList<>();

        if (result.isPresent()) {
            List<UserEntity> users = result.get();
            for (UserEntity u : users) {
                UserDTO dto = new UserDTO(u.getId(), u.getName(), u.getAccountNumber(),u.getCpf(), u.getPassword());
                lst.add(dto);
            }
        }

        return lst;
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
    public boolean isValidPassword(String password) {

        char teste[] = password.toCharArray();

        for(int i = 0; i < password.length();i++){
            if(Character.isUpperCase(teste[i])){
                return true;
            }
        }
        return false;
    }
    public int cpfVerificator(String cpf){
        char[] cpfConv = cpf.toCharArray();
        int vefEquals = 0;
        int contador = 0;
        for(int i = 0; i < 10;i++){
            if(cpfConv[i] == cpfConv[i+1]){
                vefEquals++;
            }
        }
        if(vefEquals == 10){
            return 0;
        }else {
            int j = 10;
            for (int i = 0; i < 9; i++) {
                int num = Character.getNumericValue(cpfConv[i]);
                num = num * j;
                contador = contador + num;
                j--;
            }
            int resto1 = (contador * 10) % 11;
            if (resto1 != Character.getNumericValue(cpfConv[9])) {
                return 0;
            }else {
                int k = 11;
                contador = 0;
                for (int i = 0; i < 10; i++) {
                    int num = Character.getNumericValue(cpfConv[i]);
                    num = num * k;
                    contador = contador + num;
                    k--;
                }
                int resto2 = (contador * 10) % 11;
                if (resto2 != Character.getNumericValue(cpfConv[10])) {
                    return 0;

                } else {
                    return 1;
                }
            }
        }
    }
    public int signup(UserDTO user) {

        // Regra 1: Validar o nome do usuario: Precisa ter pelo menos duas palavras
        if (user.getName().trim().equals("") ) {
            return 1;
        }
        if(user.getName().trim().split(" ").length < 2){
            return 2;
        }

        // Regra 2: Validar o CPF
        if (!isValidCPF(user.getCpf().trim())) {

            return 3;
        }
        if(cpfVerificator(user.getCpf()) != 1){
            System.out.println("O valor é: " + cpfVerificator(user.getCpf()));
            return 4;
        }
        // Regra 3: Validar a senha: Minimo 14 caracteres
        if (user.getPassword().length() < 14) {
            return 5;
        }
        if(!isValidPassword(user.getPassword().trim())){
            return 6;
        }


        UserEntity entity = new UserEntity();

        entity.setName(user.getName());
        entity.setAccountNumber(generateAccountNumber());
        entity.setCpf(user.getCpf());
        entity.setPassword(user.getPassword());
        String token = UUID.randomUUID().toString();
        entity.setToken(token);


        user.setAccountNumber(entity.getAccountNumber());
        repo.save(entity); //NA VERSAO FINAL DO PROJETO DEVE GRAVAR NO BANCO DE DADOS
        // DENTRO DA REPOSITORY
        return 0;
    }

}
