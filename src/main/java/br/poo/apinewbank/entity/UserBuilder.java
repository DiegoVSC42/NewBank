package br.poo.apinewbank.entity;

public class UserBuilder {

    private String name;
    private String accountNumber;
    private String cpf;
    private String password;

    private static final UserBuilder objetoSingleton = new UserBuilder();

    private UserBuilder() {
    }

    public static UserBuilder getSingleton() {
        return objetoSingleton;
    }
    /*public UserBuilder withId(long id) {
        this.id = id;
        return this;
    }*/
    public UserBuilder withName(String name) {
        this.name = name;
        return this;
    }
    public UserBuilder withAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
        return this;
    }
    public UserBuilder withCpf(String cpf) {
        this.cpf = cpf;
        return this;
    }
    public UserBuilder withPassword(String password) {
        this.password = password;
        return this;
    }

    public UserEntity build() {
        return new UserEntity(
                name,
                accountNumber,
                cpf,
                password);
    }

}
