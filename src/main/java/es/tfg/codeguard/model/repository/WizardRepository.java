package es.tfg.codeguard.model.repository;

import es.tfg.codeguard.model.entity.Wizard;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
@Qualifier("wizardsEntityManagerFactory")
public interface WizardRepository extends JpaRepository<Wizard, String> {


}
