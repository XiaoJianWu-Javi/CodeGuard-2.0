package es.tfg.codeguard.model.repository.deleteduser;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import es.tfg.codeguard.model.entity.deleteduser.DeletedUser;

@Repository
public interface DeletedUserRepository extends JpaRepository<DeletedUser, String> {

}
