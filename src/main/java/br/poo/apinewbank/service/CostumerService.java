package br.poo.apinewbank.service;

import br.poo.apinewbank.dto.CostumerDTO;
import br.poo.apinewbank.dto.UserDTO;
import br.poo.apinewbank.entity.CostumerEntity;
import br.poo.apinewbank.entity.UserEntity;
import br.poo.apinewbank.repository.CostumerRepository;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CostumerService {

    // Numero de digitos da conta pode ter até 8 digitos
    private int MAX_ACCOUNT_NUMBER = (int)Math.pow(10, 8);
    private List<CostumerEntity> users = new ArrayList<>();
    Random rand = new Random();
    CostumerRepository repository = new CostumerRepository();

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

    CostumerRepository repo = new CostumerRepository();

   public List<UserDTO> getCostumers() {

       List<UserDTO> result = new ArrayList<>();


       /*for(UserEntity u : repository.getUsers()){

           UserDTO aux = new UserDTO();
           aux.setName(u.getName());
           aux.setAccountNumber(u.getAccountNumber());
           aux.setCpf(u.getCpf());
           result.add(aux);
       }*/
       return result;

   }
    public int postCostumers(CostumerDTO newCostumer){

        if (newCostumer.getName().trim().equals("") || newCostumer.getName().trim().split(" ").length < 2) {
            return 1;
        }

        CostumerEntity entity = new CostumerEntity();

        entity.setName(newCostumer.getName());
        entity.setAccountNumber(newCostumer.getAccountNumber());
        entity.setBalance(newCostumer.getBalance());
        entity.setCpf(newCostumer.getCpf());
        users.add(entity);

        return 0;
    }

}
