package es.tfg.codeguard.service;

import es.tfg.codeguard.model.dto.CompilerResponseDTO;
import es.tfg.codeguard.model.dto.CompilerTestRequestDTO;
import es.tfg.codeguard.util.CompilationErrorException;
import es.tfg.codeguard.util.PlaceholderNotFoundException;
import es.tfg.codeguard.util.TestCasesNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class TestCompilerServiceTests {
    //TODO: Don't know how to test CompilationErrorException, IOException and InterruptedException
    @Autowired
    CompilerService compilerService;

    //We use here an infinite token because we need the token explicitly to get the username for the folder creation
    static final String jwtToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJTYXJ1bWFuIiwiaWF0IjoxNzMyNTMwMjgwfQ.u96ApA5WdZMlD80wfCRs6YvGskOmAXTA3ASwkRuOxQQ";

    //TODO: mirar que todo lo que se persista este bien persistido en el test
    @Test
    void validSolutionTest() {

        CompilerTestRequestDTO requestDTO = new CompilerTestRequestDTO(
                "plural",
                "public class Plural{ public static boolean isPlural(float f){ return f !=1;}}",
                "import org.junit.jupiter.api.Test;\n" +
                        "import static org.junit.jupiter.api.Assertions.assertEquals;\n" +
                        "\n" +
                        "\n" +
                        "public class PluralTest {\n" +
                        "    @Test\n" +
                        "    public void BasicTest() {\n" +
                        "      assertEquals(true,Plural.isPlural(0f));\n" +
                        "      assertEquals(true,Plural.isPlural(0.5f));\n" +
                        "      assertEquals(false,Plural.isPlural(1f));\n" +
                        "      assertEquals(true,Plural.isPlural(100f));\n" +
                        "    }\n" +
                        "}",
                "public class Plural{ public static boolean isPlural(float f){ }}"
        );

        try {
            CompilerResponseDTO responseDTO = compilerService.compileTest(jwtToken, requestDTO);
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
    void exerciseCompilationErrorTest() {

        CompilerTestRequestDTO requestDTO = new CompilerTestRequestDTO(
                "plural",
                "public class Plural{ public static boolean isPlural(float f){ return f !=1}}",
                "import org.junit.jupiter.api.Test;\n" +
                        "import static org.junit.jupiter.api.Assertions.assertEquals;\n" +
                        "\n" +
                        "\n" +
                        "public class PluralTest {\n" +
                        "    @Test\n" +
                        "    public void BasicTest() {\n" +
                        "      assertEquals(true,Plural.isPlural(0f));\n" +
                        "      assertEquals(true,Plural.isPlural(0.5f));\n" +
                        "      assertEquals(false,Plural.isPlural(1f));\n" +
                        "      assertEquals(true,Plural.isPlural(100f));\n" +
                        "    }\n" +
                        "}",
                "public class Plural{ public static boolean isPlural(float f){ }}"
        );

        try {
            CompilerResponseDTO responseDTO = compilerService.compileTest(jwtToken, requestDTO);
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
    void testCompilationError() {

        CompilerTestRequestDTO requestDTO = new CompilerTestRequestDTO(
                "plural",
                "public class Plural{ public static boolean isPlural(float f){ return f !=1;}}",
                "import org.junit.jupiter.api.Test;\n" +
                        "import static org.junit.jupiter.api.Assertions.assertEquals;\n" +
                        "\n" +
                        "\n" +
                        "public class PluralTest {\n" +
                        "    @Test\n" +
                        "    public void BasicTest() {\n" +
                        "      assertEquals(true,Plural.isPlural(0f))\n" +
                        "      assertEquals(true,Plural.isPlural(0.5f));\n" +
                        "      assertEquals(false,Plural.isPlural(1f));\n" +
                        "      assertEquals(true,Plural.isPlural(100f));\n" +
                        "    }\n" +
                        "}",
                "public class Plural{ public static boolean isPlural(float f){ }}"
        );

        try {
            CompilerResponseDTO responseDTO = compilerService.compileTest(jwtToken, requestDTO);
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
    void testingTestNotPass() {

        CompilerTestRequestDTO requestDTO = new CompilerTestRequestDTO(
                "plural",
                "public class Plural{ public static boolean isPlural(float f){ return f ==1;}}",
                "import org.junit.jupiter.api.Test;\n" +
                        "import static org.junit.jupiter.api.Assertions.assertEquals;\n" +
                        "\n" +
                        "\n" +
                        "public class PluralTest {\n" +
                        "    @Test\n" +
                        "    public void BasicTest() {\n" +
                        "      assertEquals(true,Plural.isPlural(0f));\n" +
                        "      assertEquals(true,Plural.isPlural(0.5f));\n" +
                        "      assertEquals(false,Plural.isPlural(1f));\n" +
                        "      assertEquals(true,Plural.isPlural(100f));\n" +
                        "    }\n" +
                        "}",
                "public class Plural{ public static boolean isPlural(float f){ }}"
        );

        try {
            CompilerResponseDTO responseDTO = compilerService.compileTest(jwtToken, requestDTO);
            assertThat(responseDTO.executionCode()).isEqualTo(1);
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
    void testNotFoundExceptionTest() {

        CompilerTestRequestDTO requestDTO = new CompilerTestRequestDTO(
                "plural",
                "public class Plural{ public static boolean isPlural(float f){ return f !=1;}}",
                "",
                "public class Plural{ public static boolean isPlural(float f){ }}"
        );

        assertThrows(TestCasesNotFoundException.class, () -> compilerService.compileTest(jwtToken, requestDTO));

    }

    @Test
    void placeholderNotFoundExceptionTest() {

        CompilerTestRequestDTO requestDTO = new CompilerTestRequestDTO(
                "plural",
                "public class Plural{ public static boolean isPlural(float f){ return f !=1;}}",
                "import org.junit.jupiter.api.Test;\n" +
                        "import static org.junit.jupiter.api.Assertions.assertEquals;\n" +
                        "\n" +
                        "\n" +
                        "public class PluralTest {\n" +
                        "    @Test\n" +
                        "    public void BasicTest() {\n" +
                        "      assertEquals(true,Plural.isPlural(0f));\n" +
                        "      assertEquals(true,Plural.isPlural(0.5f));\n" +
                        "      assertEquals(false,Plural.isPlural(1f));\n" +
                        "      assertEquals(true,Plural.isPlural(100f));\n" +
                        "    }\n" +
                        "}",
                ""
        );

        assertThrows(PlaceholderNotFoundException.class, () -> compilerService.compileTest(jwtToken, requestDTO));

    }

    @Test
    void classNotFoundExceptionTest() {

        CompilerTestRequestDTO requestDTO = new CompilerTestRequestDTO(
                "plural",
                "public closs Plural{ public static boolean isPlural(float f){ return f !=1;}}",
                "import org.junit.jupiter.api.Test;\n" +
                        "import static org.junit.jupiter.api.Assertions.assertEquals;\n" +
                        "\n" +
                        "\n" +
                        "public class PluralTest {\n" +
                        "    @Test\n" +
                        "    public void BasicTest() {\n" +
                        "      assertEquals(true,Plural.isPlural(0f));\n" +
                        "      assertEquals(true,Plural.isPlural(0.5f));\n" +
                        "      assertEquals(false,Plural.isPlural(1f));\n" +
                        "      assertEquals(true,Plural.isPlural(100f));\n" +
                        "    }\n" +
                        "}",
                "public class Plural{ public static boolean isPlural(float f){ }}"
        );

        assertThrows(ClassNotFoundException.class, () -> compilerService.compileTest(jwtToken, requestDTO));

        CompilerTestRequestDTO secondRequest = new CompilerTestRequestDTO(
                "plural",
                "public class Plural{ public static boolean isPlural(float f){ return f !=1;}}",
                "import org.junit.jupiter.api.Test;\n" +
                        "import static org.junit.jupiter.api.Assertions.assertEquals;\n" +
                        "\n" +
                        "\n" +
                        "class public PluralTest {\n" +
                        "    @Test\n" +
                        "    public void BasicTest() {\n" +
                        "      assertEquals(true,Plural.isPlural(0f));\n" +
                        "      assertEquals(true,Plural.isPlural(0.5f));\n" +
                        "      assertEquals(false,Plural.isPlural(1f));\n" +
                        "      assertEquals(true,Plural.isPlural(100f));\n" +
                        "    }\n" +
                        "}",
                "public class Plural{ public static boolean isPlural(float f){ }}"
        );

        assertThrows(ClassNotFoundException.class, () -> compilerService.compileTest(jwtToken, secondRequest));

    }

    @Test
    void timeoutExceptionTest() {

        CompilerTestRequestDTO requestDTO = new CompilerTestRequestDTO(
                "plural",
                "public class Plural{ public static boolean isPlural(float f){ while(true){int i = 0;} }}",
                "import org.junit.jupiter.api.Test;\n" +
                        "import static org.junit.jupiter.api.Assertions.assertEquals;\n" +
                        "\n" +
                        "\n" +
                        "public class PluralTest {\n" +
                        "    @Test\n" +
                        "    public void BasicTest() {\n" +
                        "      assertEquals(true,Plural.isPlural(0f));\n" +
                        "      assertEquals(true,Plural.isPlural(0.5f));\n" +
                        "      assertEquals(false,Plural.isPlural(1f));\n" +
                        "      assertEquals(true,Plural.isPlural(100f));\n" +
                        "    }\n" +
                        "}",
                "public class Plural{ public static boolean isPlural(float f){ }}"
        );

        assertThrows(TimeoutException.class, () -> compilerService.compileTest(jwtToken, requestDTO));
    }
}