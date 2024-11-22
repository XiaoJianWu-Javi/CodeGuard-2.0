package es.tfg.codeguard.service.imp;

import java.util.List;
import java.util.stream.IntStream;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import es.tfg.codeguard.model.dto.ExerciseDTO;
import es.tfg.codeguard.service.ExternalAPIService;

@Service
public class ProjectEulerAPIService implements ExternalAPIService {

    private static final String URL = "https://projecteuler.net/minimal=";

    @Override
    public ExerciseDTO requestExerciseById(String id) {
        String uri = new StringBuilder(URL).append(id).toString();
        return convertToDTO(new RestTemplate().getForEntity(uri, String.class).getBody(), id);
    }

    @Override
    public List<ExerciseDTO> requestNExercises(int number) {
        return IntStream.range(1, number+1)
                        .mapToObj(i -> requestExerciseById(String.valueOf(i)))
                        .toList();
    }

    private ExerciseDTO convertToDTO(String html, String id) {
        return new ExerciseDTO("project-euler-".concat(id),
                                "Project Euler ".concat(id),
                                html,
                                null, "ProjectEuler API");
    }

}
