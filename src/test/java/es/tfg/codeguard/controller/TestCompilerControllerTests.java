package es.tfg.codeguard.controller;

import es.tfg.codeguard.controller.imp.CompilationControllerImp;
import es.tfg.codeguard.model.dto.CompilerResponseDTO;
import es.tfg.codeguard.model.dto.CompilerTestRequestDTO;
import es.tfg.codeguard.service.CompilerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class TestCompilerControllerTests {

    @Autowired
    CompilerService compilerService;
    @Autowired
    CompilationControllerImp compilationController;

    //We use here an infinite token because we need the token explicitly to get the username for the folder creation
    static final String jwtToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJTYXJ1bWFuIiwiaWF0IjoxNzMyNTMwMjgwfQ.u96ApA5WdZMlD80wfCRs6YvGskOmAXTA3ASwkRuOxQQ";

    @Test
    void validCompileTest(){

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

        ResponseEntity<CompilerResponseDTO> esperado = compilationController.compileTestCode(jwtToken, requestDTO);

        assertThat(esperado.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void noTestGivenCompileTest(){

        CompilerTestRequestDTO requestDTO = new CompilerTestRequestDTO(
                "plural",
                "public class Plural{ public static boolean isPlural(float f){ return f !=1;}}",
                "",
                "public class Plural{ public static boolean isPlural(float f){ }}"
        );

        ResponseEntity<CompilerResponseDTO> esperado = compilationController.compileTestCode(jwtToken, requestDTO);

        assertThat(esperado.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void noPlaceholderGivenCompileTest(){
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

        ResponseEntity<CompilerResponseDTO> esperado = compilationController.compileTestCode(jwtToken, requestDTO);

        assertThat(esperado.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void badClassNameCompileTest(){

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

        ResponseEntity<CompilerResponseDTO> esperado = compilationController.compileTestCode(jwtToken, requestDTO);

        assertThat(esperado.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);

        CompilerTestRequestDTO requestDTOSecond = new CompilerTestRequestDTO(
                "plural",
                "public class Plural{ public static boolean isPlural(float f){ return f !=1;}}",
                "import org.junit.jupiter.api.Test;\n" +
                        "import static org.junit.jupiter.api.Assertions.assertEquals;\n" +
                        "\n" +
                        "\n" +
                        "public PluralTest {\n" +
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

        ResponseEntity<CompilerResponseDTO> esperadoSecond = compilationController.compileTestCode(jwtToken, requestDTOSecond);

        assertThat(esperadoSecond.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void requestTimeoutCompileTest(){

        CompilerTestRequestDTO requestDTO = new CompilerTestRequestDTO(
                "plural",
                "public class Plural{ public static boolean isPlural(float f){ while(true){int i = 0;}}}",
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
                "public class Plural{ public static boolean isPlural(float f){  }}"
        );

        ResponseEntity<CompilerResponseDTO> esperado = compilationController.compileTestCode(jwtToken, requestDTO);

        assertThat(esperado.getStatusCode()).isEqualTo(HttpStatus.REQUEST_TIMEOUT);
    }

}
