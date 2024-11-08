package es.tfg.codeguard.model.repository;

import es.tfg.codeguard.model.entity.PasswordWizardEncript;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Qualifier("passwordEntityManagerFactory")
public interface PasswordWizardRepository extends JpaRepository<PasswordWizardEncript, String> {
}
