package br.poo.apinewbank.service;

import br.poo.apinewbank.dto.UserDTO;
import br.poo.apinewbank.entity.UserEntity;
import br.poo.apinewbank.repository.AuthRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Scope("singleton") // So exite uma instancia dessa classe
public class AuthService {

    AuthValidators Validator = new AuthValidators();
    @Autowired
    private AuthRepository repo;

    public String login(UserDTO user){

        String cpf = user.getCpf();
        String password = user.getPassword();

        for(UserEntity u : repo.findAll()){
            if(u.getPassword().equals(password) && u.getCpf().equals(cpf)) {
                return u.getToken();
            }
        }
        return null;
    }
    public int signup(UserDTO user) {

        // Explicacao das regras estao na AuthController
        if (user.getName().trim().equals("") || user.getName().trim().split(" ").length < 2) {
            return 1;
        }

        if(!Validator.cpfVerificator(user.getCpf())){

            return 2;
        }
        if(!Validator.isValidPassword(user.getPassword())){
            return 3;
        }


        UserEntity entity = new UserEntity(
                user.getName(),
                user.getAccountNumber(),
                user.getCpf(),
                user.getPassword());

        /*entity.setName(user.getName());

        entity.setCpf(user.getCpf());
        entity.setPassword(user.getPassword());*/
        String token = UUID.randomUUID().toString();
        entity.setToken(token);

        entity.setAccountNumber(Validator.generateAccountNumber());
        user.setAccountNumber(entity.getAccountNumber());
        repo.save(entity);
        return 0;
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
    public int deleteUsers(long id) {
        Optional <UserEntity> result = repo.findById(id);

        if (result.isPresent()) {
            repo.deleteById(id);
            return 1;
        }
        return 0;
    }
    public int putUsers(long id, UserDTO user) {

        Optional <UserEntity> result = repo.findById(id);

        if (result.isPresent()) {
            user.setPassword(result.get().getPassword());
            user.setCpf(result.get().getCpf());
            user.setAccountNumber(result.get().getAccountNumber());
            int execute = deleteUsers(user.getId());


            return signup(user);
        }


        return 1;
    }





}
