package es.tfg.codeguard.model.repository.userpass;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import es.tfg.codeguard.model.dto.UserPassDTO;
import es.tfg.codeguard.model.entity.userpass.UserPass;
import java.util.Optional;



@Repository
public interface UserPassRepository extends JpaRepository<UserPass, String> {

    Optional<UserPass> findByUsername(String username);

    Optional<UserPass> findByHashedPass(String hashedPass);

}
