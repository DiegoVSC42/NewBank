package br.poo.apinewbank.controller;

import br.poo.apinewbank.dto.UserDTO;
import br.poo.apinewbank.service.CostumerService;
import br.poo.apinewbank.dto.CostumerDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class NewBankController {


    CostumerService service = new CostumerService();

    @GetMapping("/costumers")
    public List<UserDTO> getCostumers(){
        List<UserDTO> dtos = service.getCostumers();
        return dtos;
    }

    @PostMapping("/costumers")
    public ResponseEntity<String> postCostumers(@RequestBody CostumerDTO newCostumer) {
        int result = service.postCostumers(newCostumer);

        if (result == 1) {
            return ResponseEntity.badRequest().body("Nome de usuario invalido!");
        }
        return ResponseEntity.ok().build();
    }


}
