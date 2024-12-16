package es.tfg.codeguard.model.repository.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import es.tfg.codeguard.model.entity.user.User;

@Repository
public interface UserRepository extends JpaRepository<User, String> { }
