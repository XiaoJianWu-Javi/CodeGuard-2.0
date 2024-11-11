package es.tfg.codeguard.model.repository;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import es.tfg.codeguard.model.entity.UserPass;

@Repository
@Qualifier("entityManagerFactoryPassword")
public interface UserPassRepository extends JpaRepository<UserPass, String> {

}
