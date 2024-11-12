package es.tfg.codeguard.model.repository.userpass;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import es.tfg.codeguard.model.entity.userpass.UserPass;

@Repository
public interface UserPassRepository extends JpaRepository<UserPass, String> {

}
