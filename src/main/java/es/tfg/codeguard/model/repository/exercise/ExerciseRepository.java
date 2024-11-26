package es.tfg.codeguard.model.repository.exercise;

import es.tfg.codeguard.model.entity.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import es.tfg.codeguard.model.entity.exercise.Exercise;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;
import java.util.Optional;

@Repository
public interface ExerciseRepository extends JpaRepository<Exercise, String> {

    //TODO: POSIBLE FUTURA IMPLEMENTACIÓN
    Optional<Exercise> findByTitle(String title);

    Page<Exercise> findByTitleContaining(String title, Pageable pageable);

}
