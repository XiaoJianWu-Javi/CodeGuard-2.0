package es.tfg.codeguard.model.repository.exercise;

import es.tfg.codeguard.model.entity.Exercise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExerciseRepository extends JpaRepository<Exercise, String> {



}
