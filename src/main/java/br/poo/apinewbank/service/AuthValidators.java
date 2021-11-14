package br.poo.apinewbank.service;

import br.poo.apinewbank.repository.CostumerRepository;

import java.time.format.DateTimeFormatter;
import java.util.Random;

public class AuthValidators {

    public boolean cpfVerificator(String cpf){

        char[] cpfConv = cpf.toCharArray();
        int vefEquals = 0;
        int contador = 0;
        for(int i = 0; i < 10;i++){
            if(cpfConv[i] == cpfConv[i+1]){
                vefEquals++;
            }
        }
        if(vefEquals == 10){
            return false;
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
                return false;
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
                    return false;

                }
            }
        }
        if(cpf.length() == 11 && cpf.matches("[0-9]+")){
            return true;
        }
        return false;
    }
    private String convertIntToAccountNumber(int accountNumber) {
        int account = accountNumber/10; // Primeiros digitos
        int digit = accountNumber - account*10; // Ultimo digito
        return String.format("%07d-%d", account, digit);
    }

    public String generateAccountNumber() {
        int MAX_ACCOUNT_NUMBER = (int)Math.pow(10, 8);
        Random rand = new Random();
        CostumerRepository repository = new CostumerRepository();

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
    public boolean isValidPassword(String password) {

        int vefLetter = 0;
        int vefSymbol = 0;
        int vefDigit = 0;
        if(password.length() < 12){
            return false;
        }
        for(char tester : password.toCharArray()){
            if(!Character.isLetterOrDigit(tester)){
                vefSymbol++;
            }
            if(Character.isUpperCase(tester)){
                vefLetter++;
            }
            if(Character.isDigit(tester)){
                vefDigit++;
            }

        }
        if(vefLetter > 0 && vefSymbol > 0 && vefDigit > 0){
            return true;
        }
        return false;
    }
}
