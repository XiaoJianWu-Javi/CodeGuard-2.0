package es.tfg.codeguard.service;

import java.util.List;

import org.springframework.stereotype.Service;

import es.tfg.codeguard.model.dto.ExerciseDTO;

@Service
public interface ExternalAPIService {

    public abstract ExerciseDTO requestExerciseById(String id);

    public abstract List<ExerciseDTO> requestNExercises(int number);
}
