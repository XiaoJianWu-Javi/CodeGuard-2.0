package es.tfg.codeguard.controller;

import es.tfg.codeguard.controller.imp.CompilationControllerImp;
import es.tfg.codeguard.model.dto.CompilerRequestDTO;
import es.tfg.codeguard.model.dto.CompilerResponseDTO;
import es.tfg.codeguard.service.CompilerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class CompilerControllerTests {

    @Autowired
    CompilerService compilerService;
    @Autowired
    CompilationControllerImp compilationController;

    //We use here an infinite token because we need the token explicitly to get the username for the folder creation
    static final String jwtToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJTYXJ1bWFuIiwiaWF0IjoxNzMyNTMwMjgwfQ.u96ApA5WdZMlD80wfCRs6YvGskOmAXTA3ASwkRuOxQQ";

    @Test
    void validCompileTest(){

        CompilerRequestDTO requestDTO = new CompilerRequestDTO(
                "plural","public class Plural{ public static boolean isPlural(float f){ return f !=1;}}"
        );

        ResponseEntity<CompilerResponseDTO> esperado = compilationController.compileCode(jwtToken, requestDTO);

        assertThat(esperado.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void serverErrorCompileTest(){

        CompilerRequestDTO requestDTO = new CompilerRequestDTO(
                "singular","public class Plural{ public static boolean isPlural(float f){ return f !=1;}}"
        );

        ResponseEntity<CompilerResponseDTO> esperado = compilationController.compileCode(jwtToken, requestDTO);

        assertThat(esperado.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Test
    void badRequestCompileTest(){

        CompilerRequestDTO requestDTO = new CompilerRequestDTO(
                "plural","public closs Plural{ public static boolean isPlural(float f){ return f !=1;}}"
        );

        ResponseEntity<CompilerResponseDTO> esperado = compilationController.compileCode(jwtToken, requestDTO);

        assertThat(esperado.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void requestTimeoutCompileTest(){

        CompilerRequestDTO requestDTO = new CompilerRequestDTO(
                "plural","public class Plural{ public static boolean isPlural(float f){ while(true){int i = 0;}}}"
        );

        ResponseEntity<CompilerResponseDTO> esperado = compilationController.compileCode(jwtToken, requestDTO);

        assertThat(esperado.getStatusCode()).isEqualTo(HttpStatus.REQUEST_TIMEOUT);
    }
}
