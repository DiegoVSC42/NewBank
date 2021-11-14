package br.poo.apinewbank.entity;

public class CostumerBuilder {

    private String name;
    private String accountNumber;
    private String cpf;
    private float balance;
    //private String creationDate;

    private static final CostumerBuilder objetoSingleton = new CostumerBuilder();

    private CostumerBuilder() {
    }

    public static CostumerBuilder getSingleton() {
        return objetoSingleton;
    }

    public CostumerBuilder withName(String name) {
        this.name = name;
        return this;
    }
    public CostumerBuilder withAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
        return this;
    }
    public CostumerBuilder withCpf(String cpf) {
        this.cpf = cpf;
        return this;
    }
    public CostumerBuilder withBalance(Float balance) {
        this.balance = balance;
        return this;
    }
    /*public CostumerBuilder withcreationDate(String creationDate) {
        this.creationDate = creationDate;
        return this;
    }*/

    public CostumerEntity build() {
        return new CostumerEntity(name, accountNumber,cpf,balance);
    }
}
