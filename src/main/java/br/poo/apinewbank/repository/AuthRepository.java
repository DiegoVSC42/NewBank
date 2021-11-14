package br.poo.apinewbank.repository;
import br.poo.apinewbank.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface AuthRepository extends CrudRepository<UserEntity, Long> {

    Optional<List<UserEntity>> findByNameContaining(String name);

}
