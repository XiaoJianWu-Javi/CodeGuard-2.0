package es.tfg.codeguard.service;

import es.tfg.codeguard.model.dto.CompilerRequestDTO;
import es.tfg.codeguard.model.dto.CompilerResponseDTO;
import es.tfg.codeguard.util.CompilationErrorException;
import es.tfg.codeguard.util.TestCasesNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class CompilerServiceTests {

    //TODO: Don't know how to test CompilationErrorException, IOException and InterruptedException
    @Autowired
    CompilerService compilerService;

    //We use here an infinite token because we need the token explicitly to get the username for the folder creation
    static final String jwtToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJTYXJ1bWFuIiwiaWF0IjoxNzMyNTMwMjgwfQ.u96ApA5WdZMlD80wfCRs6YvGskOmAXTA3ASwkRuOxQQ";

    @Test
    void testingValidSolution(){

        CompilerRequestDTO requestDTO = new CompilerRequestDTO(
            "plural","public class Plural{ public static boolean isPlural(float f){ return f !=1;}}"
        );

        try {
            CompilerResponseDTO responseDTO = compilerService.compileSolution(jwtToken, requestDTO);
            assertThat(responseDTO.executionCode()).isEqualTo(0);
            assertThat(responseDTO.exerciseCompilationCode()).isEqualTo(0);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (CompilationErrorException e) {
            throw new RuntimeException(e);
        } catch (TimeoutException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (TestCasesNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void testingCompilationError(){

        CompilerRequestDTO requestDTO = new CompilerRequestDTO(
                "plural","public class Plural{ public static boolean isPlural(float f){ return f !=1}}"
        );

        try {
            CompilerResponseDTO responseDTO = compilerService.compileSolution(jwtToken, requestDTO);
            assertThat(responseDTO.executionCode()).isNull();
            assertThat(responseDTO.exerciseCompilationCode()).isEqualTo(1);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (CompilationErrorException e) {
            throw new RuntimeException(e);
        } catch (TimeoutException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (TestCasesNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void testingTestNotPass(){

        CompilerRequestDTO requestDTO = new CompilerRequestDTO(
                "plural","public class Plural{ public static boolean isPlural(float f){ return f ==1;}}"
        );

        try {
            CompilerResponseDTO responseDTO = compilerService.compileSolution(jwtToken, requestDTO);
            assertThat(responseDTO.exerciseCompilationCode()).isEqualTo(0);
            assertThat(responseDTO.executionCode()).isEqualTo(1);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (CompilationErrorException e) {
            throw new RuntimeException(e);
        } catch (TimeoutException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (TestCasesNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void testNotFoundExceptionTest(){

        CompilerRequestDTO requestDTO = new CompilerRequestDTO(
                "singular","public class Plural{ public static boolean isPlural(float f){ return f ==1;}}"
        );

        assertThrows(TestCasesNotFoundException.class, () -> compilerService.compileSolution(jwtToken, requestDTO));

    }

    @Test
    void classNotFoundExceptionTest(){

        CompilerRequestDTO requestDTO = new CompilerRequestDTO(
                "plural","public closs Plural{ public static boolean isPlural(float f){ return f ==1;}}"
        );

        assertThrows(ClassNotFoundException.class, () -> compilerService.compileSolution(jwtToken, requestDTO));

    }

    @Test
    void timeoutExceptionTest(){

        CompilerRequestDTO requestDTO = new CompilerRequestDTO(
                "plural","public class Plural{ public static boolean isPlural(float f){ while(true){int i = 0;}}}"
        );

        assertThrows(TimeoutException.class, () -> compilerService.compileSolution(jwtToken, requestDTO));
    }
}
