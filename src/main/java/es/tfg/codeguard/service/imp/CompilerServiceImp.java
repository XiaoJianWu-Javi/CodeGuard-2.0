package es.tfg.codeguard.service.imp;

import es.tfg.codeguard.model.dto.CompilerResponseDTO;
import es.tfg.codeguard.model.dto.CompilerTestRequestDTO;
import es.tfg.codeguard.model.entity.exercise.Exercise;
import es.tfg.codeguard.model.entity.user.User;
import es.tfg.codeguard.model.repository.user.UserRepository;
import es.tfg.codeguard.service.ExerciseService;
import es.tfg.codeguard.service.JWTService;
import es.tfg.codeguard.util.CompilationErrorException;
import es.tfg.codeguard.model.dto.CompilerRequestDTO;
import es.tfg.codeguard.model.repository.exercise.ExerciseRepository;
import es.tfg.codeguard.service.CompilerService;
import es.tfg.codeguard.util.TestCasesNotFoundException;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class CompilerServiceImp implements CompilerService {

    @Autowired
    private JWTService jwtService;
    @Autowired
    private ExerciseService exerciseService;
    @Autowired
    private ExerciseRepository exerciseRepository;
    @Autowired
    private UserRepository userRepository;
    Logger logger = LoggerFactory.getLogger(CompilerServiceImp.class);

    @Override
    public Optional<CompilerResponseDTO> compileSolution(String userToken, CompilerRequestDTO compileInfo) throws ClassNotFoundException, IOException, CompilationErrorException, TimeoutException, InterruptedException, TestCasesNotFoundException {
        Optional<String> tests = exerciseService.getTestFromExercise(compileInfo.exerciseId());
        if(tests.isEmpty()){
            throw new TestCasesNotFoundException("You can't compile an spell without tests");
        }
        String javaCode = compileInfo.exerciseSolution();
        String testCode = tests.get();

        CompilerResponseDTO compilerResponse = compilator(userToken, javaCode, testCode);

        if(compilerResponse.exerciseCompilationCode() == 0 && compilerResponse.executionCode() == 0){
            //Se guarda la solucion del usuario
            //TODO: cambiar la implementacion del guardado de la solucion utilizando un servicio de userUpdate y exerciseUpdate
            User user = userRepository.findById(jwtService.extractUserPass(userToken).getUsername()).get();
            user.addExercise(compileInfo.exerciseId());
            Exercise exercise = exerciseRepository.findById(compileInfo.exerciseId()).get();
            exercise.addSolution(user.getUsername(), javaCode);
            userRepository.save(user);
            exerciseRepository.save(exercise);
        }
        return Optional.of(compilerResponse);
    }

    public Optional<CompilerResponseDTO> compileTest(String userToken, CompilerTestRequestDTO compileInfo) throws ClassNotFoundException, IOException, CompilationErrorException, TimeoutException, InterruptedException{
        String javaCode = compileInfo.exerciseSolution();
        String testCode = compileInfo.exerciseTests();

        CompilerResponseDTO compilerResponse = compilator(userToken, javaCode, testCode);

        if(compilerResponse.exerciseCompilationCode() == 0 && compilerResponse.executionCode() == 0){
            //Se guardan los tests del ejercicio
            //TODO: cambiar la implementacion del guardado de los test utilizando un servicio

        }
        return Optional.of(compilerResponse);
    }

    private CompilerResponseDTO compilator(String userToken, String javaCode, String testCode) throws ClassNotFoundException, IOException, CompilationErrorException, TimeoutException, InterruptedException{
        Pattern javaClassPattern = Pattern.compile("(?:public)(?:\\s+)(?:class)(?:\\s+)(\\w+)");
        Matcher appClassNameMatcher = javaClassPattern.matcher(javaCode);
        String javaClassName = "";
        if(appClassNameMatcher.find()){
            javaClassName = appClassNameMatcher.group(1);
        }else{
            throw new ClassNotFoundException("Could not find the class name inside the Java Code");
        }

        Pattern testClassPattern = Pattern.compile("(?:class)(?:\\s+)(\\w+)");
        Matcher testClassNameMatcher = testClassPattern.matcher(testCode);
        String testClassName = "";
        if(testClassNameMatcher.find()){
            testClassName = testClassNameMatcher.group(1);
        }else{
            throw new ClassNotFoundException("Could not find the class name inside the Test Code");
        }

        String folderRoute = "src/main/resources/compilation/" + jwtService.extractUserPass(userToken).getUsername();

        File userFolder = new File(folderRoute);
        if(!userFolder.exists()){
            if(userFolder.mkdirs()){
                logger.info("User folder " + userFolder.toString() +  " created");
            }else{
                throw new CompilationErrorException("Could not create the folder for compilation");
            }
        }

        String javaFile = folderRoute + "/" + javaClassName + ".java";
        String testFile = folderRoute + "/" + testClassName + ".java";

        File javaJavaFile = new File(javaFile);
        File testJavaFile = new File(testFile);

        try(BufferedWriter bw = new BufferedWriter(new FileWriter(javaJavaFile))){
            bw.write(javaCode);
        }
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(testJavaFile))){
            bw.write(testCode);
        }

        //Compilation
        ProcessBuilder compilation = new ProcessBuilder("javac", "-Xlint:none", javaFile, testFile, "-cp", "lib/junit-jupiter-api-5.11.0.jar");
        compilation.redirectErrorStream(true);
        Process compilationProcess = compilation.start();

        StringBuilder compilationErrorMessage = new StringBuilder();
        try(BufferedReader br = new BufferedReader(new InputStreamReader(compilationProcess.getInputStream()))){
            String line;
            while((line=br.readLine())!=null){
                compilationErrorMessage.append("\n" + line);
            }
        }

        String compilationExitMessage = "Correct program compilation";
        int compilationExitCode = compilationProcess.waitFor();
        if(compilationExitCode!=0){
            compilationExitMessage = "Compilation Error: " + compilationErrorMessage.toString();
            FileUtils.deleteDirectory(userFolder);
            return new CompilerResponseDTO(compilationExitCode, compilationExitMessage, null, null);
        }

        //JUnit 5 console execution
        ProcessBuilder testExecutor = new ProcessBuilder("java",
                "-jar",
                "../../../../../lib/junit-platform-console-standalone-1.11.3.jar",
                "execute", //Si no se usa "execute" la consola en la version 1.11.3 da un warning
                "-cp", //--class-path -cp
                ".",
                "-c", //--select-class -c
                testClassName,
                "--details=tree", //Modes: [flat, verbose, tree]
                "--disable-banner",
                "--disable-ansi-colors");
        testExecutor.redirectErrorStream(true);
        testExecutor.directory(userFolder);
        Process testExecutorProcess = testExecutor.start();


        StringBuilder executionMessage = new StringBuilder();
        //Se esperan 15 segundos antes de destruir el proceso para evitar bucles infinitos
        if(!testExecutorProcess.waitFor(15, TimeUnit.SECONDS)){
            testExecutorProcess.destroy();
            throw new TimeoutException("Exceeded the 15 seconds time limit");
        }else{
            try(BufferedReader br = new BufferedReader(new InputStreamReader(testExecutorProcess.getInputStream()))){
                String line;
                while((line=br.readLine())!=null){
                    executionMessage.append(line);
                    executionMessage.append("\n");
                }
            }
        }

        int executionExitCode = testExecutorProcess.exitValue();
        String executionExitMessage = filterConsoleOutput(executionMessage.toString());

        FileUtils.deleteDirectory(userFolder);
        return new CompilerResponseDTO(compilationExitCode, compilationExitMessage, executionExitCode, executionExitMessage);
    }

    private String filterConsoleOutput(String consoleOutput){
        String[] lines = consoleOutput.split("\n");
        StringBuilder output = new StringBuilder();
        for(int i = 0; i< lines.length; i++){
            if(i!=0 && i!=1 && i!=5 && i!=6){
                if(lines[i].startsWith("[")&&lines[i].contains("containers")){}
                else{
                    output.append(lines[i]);
                    output.append("\n");
                }
            }
        }
        return output.toString().replace("?", "");
    }
}
