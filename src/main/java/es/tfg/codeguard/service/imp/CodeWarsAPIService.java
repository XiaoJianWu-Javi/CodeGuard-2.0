package es.tfg.codeguard.service.imp;

import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import es.tfg.codeguard.model.dto.ExerciseDTO;
import es.tfg.codeguard.service.ExternalAPIService;

@Service
public class CodeWarsAPIService implements ExternalAPIService {

    private static final String URL = "https://www.codewars.com/api/v1/code-challenges/";

    @Override
    public ExerciseDTO requestExerciseById(String id) {
        String uri = new StringBuilder(URL).append(id).toString();
        return convertToDTO(new RestTemplate().getForEntity(uri, String.class).getBody());
    }

    @Override
    public List<ExerciseDTO> requestNExercises(int number) {
        throw new UnsupportedOperationException("Unsuported method 'requestNExercises' for CodeWarsAPI");
    }

    private ExerciseDTO convertToDTO(String json) {
        try {
            JSONObject jsonObject = new JSONObject(json);

            return new ExerciseDTO(jsonObject.getString("slug"),
                                    jsonObject.getString("name"),
                                    jsonObject.getString("description"),
                                    null, "CodeWars API");
        } catch(JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
}
