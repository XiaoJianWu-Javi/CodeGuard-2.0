package es.tfg.codeguard.model.repository.exercisereport;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import es.tfg.codeguard.model.entity.exercisereport.ExerciseReport;
import java.util.List;


public interface ExerciseReportRepository extends JpaRepository<ExerciseReport, Long> {
	
	Optional<ExerciseReport> findByUserNameAndDescription(String userName, String description);
}

