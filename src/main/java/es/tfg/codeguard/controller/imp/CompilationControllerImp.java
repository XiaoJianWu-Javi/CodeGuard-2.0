package es.tfg.codeguard.controller.imp;

import es.tfg.codeguard.controller.CompilationController;
import es.tfg.codeguard.model.dto.CompilerRequestDTO;
import es.tfg.codeguard.model.dto.CompilerResponseDTO;
import es.tfg.codeguard.model.dto.CompilerTestRequestDTO;
import es.tfg.codeguard.service.CompilerService;
import es.tfg.codeguard.util.CompilationErrorException;
import es.tfg.codeguard.util.NotAllowedUserException;
import es.tfg.codeguard.util.PlaceholderNotFoundException;
import es.tfg.codeguard.util.TestCasesNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

@RestController
public class CompilationControllerImp implements CompilationController {

    @Autowired
    private CompilerService compilerService;

    @Override
    public ResponseEntity<CompilerResponseDTO> compileCode(@RequestHeader("Authorization") String userToken, @RequestBody CompilerRequestDTO compilerRequestDTO) {
        try {
            //TODO: crear respuestas con codigos y mensajes personalizados dependiendo de lo que falle
            return  new ResponseEntity<CompilerResponseDTO>(compilerService.compileSolution(userToken, compilerRequestDTO), HttpStatus.OK);
        } catch (TestCasesNotFoundException e) {
            //Si no se encuentran los test para el ejercicio que quieres compilar
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (ClassNotFoundException e){
            //Si no se encuentra el nombre de la clase en el código enviado o en los test
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (CompilationErrorException e) {
            //Si no se puede crear la carpeta para realizar la compilacion
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (IOException e) {
            //Si hay algun problema relacionado con la lectura o escritura en los archivos que se van a compilar
            //También si hay problemas con la ejecucion de procesos
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (TimeoutException e) {
            //Si la ejecución del ejercicio se va de tiempo o hay bucles infinitos
            return new ResponseEntity<>(HttpStatus.REQUEST_TIMEOUT);
        } catch (InterruptedException e) {
            //Si durante los 15 segundos de ejecucion del ejercicio algo mata el proceso que lo esta ejecutando
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<CompilerResponseDTO> compileTestCode(@RequestHeader("Authorization") String userToken, @RequestBody CompilerTestRequestDTO compilerTestRequestDTO) {
        try {
            //TODO: no se controla aqui que mande un ID incorrecto en el compileCode si
            //TODO: crear respuestas con codigos y mensajes personalizados dependiendo de lo que falle
            return  new ResponseEntity<CompilerResponseDTO>(compilerService.compileTest(userToken, compilerTestRequestDTO), HttpStatus.OK);
        } catch (TestCasesNotFoundException e) {
            //Si no mandas tests para que compilen
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (PlaceholderNotFoundException e){
            //Si no se proporciona ningun placeholder
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (ClassNotFoundException e){
            //Si no se encuentra el nombre de la clase en el código enviado o en los test
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch(NotAllowedUserException e) {
        	//Si el usuario no tiene los permisos necesarios para compilar tests
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        } catch (CompilationErrorException e) {
            //Si no se puede crear la carpeta para realizar la compilacion
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (IOException e) {
            //Si hay algun problema relacionado con la lectura o escritura en los archivos que se van a compilar
            //También si hay problemas con la ejecucion de procesos
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (TimeoutException e) {
            //Si la ejecución del ejercicio se va de tiempo o hay bucles infinitos
            return new ResponseEntity<>(HttpStatus.REQUEST_TIMEOUT);
        } catch (InterruptedException e) {
            //Si durante los 15 segundos de ejecucion del ejercicio algo mata el proceso que lo esta ejecutando
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
