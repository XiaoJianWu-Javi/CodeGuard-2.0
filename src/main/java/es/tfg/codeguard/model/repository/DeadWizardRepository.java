package es.tfg.codeguard.model.repository;

import es.tfg.codeguard.model.entity.DeadWizard;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Qualifier("deadWizardsEntityManagerFactory")
public interface DeadWizardRepository extends JpaRepository<DeadWizard, String>{
}
