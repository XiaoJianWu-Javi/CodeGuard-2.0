package es.tfg.codeguard.model.repository.exercisereport;

import org.springframework.data.jpa.repository.JpaRepository;
import es.tfg.codeguard.model.entity.exercisereport.ExerciseReport;

public interface ExerciseReportRepository extends JpaRepository<ExerciseReport, Long> {}
