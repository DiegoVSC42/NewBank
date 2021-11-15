package br.poo.apinewbank.entity;
import javax.persistence.*;

@Entity
@Table(name = "user")
public class UserEntity {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "accountnumber", nullable = false)
    private String accountNumber;

    @Column(name = "cpf", nullable = false)
    private String cpf;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "token", nullable = true)
    private String token;

    public UserEntity(){

    }
    public UserEntity(
                    String name,
                    String accountNumber,
                    String cpf,
                    String password){

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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

}
