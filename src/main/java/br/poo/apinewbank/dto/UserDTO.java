package br.poo.apinewbank.dto;

public class UserDTO {

    private Long id;
    private String name;
    private String accountNumber;
    private String cpf;
    private String password;

    public UserDTO(Long id, String name, String accountNumber, String cpf, String password){
        this.id = id;
        this.name = name;
        this.accountNumber = accountNumber;
        this.cpf = cpf;
        this.password = password;

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
