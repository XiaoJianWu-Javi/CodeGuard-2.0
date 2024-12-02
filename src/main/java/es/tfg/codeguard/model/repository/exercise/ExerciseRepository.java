package es.tfg.codeguard.model.repository.exercise;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import es.tfg.codeguard.model.entity.exercise.Exercise;

import java.util.Optional;

@Repository
public interface ExerciseRepository extends JpaRepository<Exercise, String> {

    Optional<Exercise> findByTitle(String title);

    Page<Exercise> findByTitleContaining(String title, Pageable pageable);

}
