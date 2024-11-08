package es.tfg.codeguard.compilerTest;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.*;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TestAdvancedCompiler {

    public static void main(String[] args) throws IOException, InterruptedException, TimeoutException, CompilationErrorException, ClassNotFoundException {
        File jsonFile = new File("src/main/java/es/tfg/codeguard/compilerTest/json/advanced_test.json");
        JsonObject jsonObject;
        try(FileReader fr = new FileReader(jsonFile)){
            jsonObject = JsonParser.parseReader(fr).getAsJsonObject();
        }

        Pattern javaClassPattern = Pattern.compile("(?:public)(?:\\s+)(?:class)(?:\\s+)(\\w+)");
        String javaCode = jsonObject.get("javaCode").getAsString();
        Matcher appClassNameMatcher = javaClassPattern.matcher(javaCode);
        String javaClassName = "";
        if(appClassNameMatcher.find()){
            javaClassName = appClassNameMatcher.group(1);
        }else{
            throw new ClassNotFoundException();
        }

        Pattern testClassPattern = Pattern.compile("(?:class)(?:\\s+)(\\w+)");
        String testCode = jsonObject.get("testCode").getAsString();
        Matcher testClassNameMatcher = testClassPattern.matcher(testCode);
        String testClassName = "";
        if(testClassNameMatcher.find()){
            testClassName = testClassNameMatcher.group(1);
        }else{
            throw new ClassNotFoundException();
        }

        String javaFile = javaClassName + ".java";
        String testFile = testClassName + ".java";

        try(BufferedWriter bw = new BufferedWriter(new FileWriter(javaFile))){
            bw.write(javaCode);
        }
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(testFile))){
            bw.write(testCode);
        }

        //Compilation
        ProcessBuilder appCompilator = new ProcessBuilder("javac", javaFile);
        appCompilator.redirectErrorStream(true);
        Process appCompilatorProcess = appCompilator.start();

        StringBuilder appErrorMsg = new StringBuilder();
        try(BufferedReader br = new BufferedReader(new InputStreamReader(appCompilatorProcess.getInputStream()))){
            String line;
            while((line=br.readLine())!=null){
                appErrorMsg.append("\n" + line);
            }
        }

        //Si ha compilado bien el programa devuelve 0
        int exitCode = appCompilatorProcess.waitFor();
        System.out.println("----------------------");
        System.out.println("Compiling APP exit code: " + exitCode);
        System.out.println("----------------------");
        if(exitCode!=0){
            throw new CompilationErrorException(appErrorMsg.toString());
        }

        ProcessBuilder testCompilator = new ProcessBuilder("javac", javaFile, testFile, "-cp", "lib/junit-jupiter-api-5.11.0.jar");
        testCompilator.redirectErrorStream(true);
        Process testCompilatorProcess = testCompilator.start();

        StringBuilder testErrorMsg = new StringBuilder();
        try(BufferedReader br = new BufferedReader(new InputStreamReader(testCompilatorProcess.getInputStream()))){
            String line;
            while((line=br.readLine())!=null){
                testErrorMsg.append("\n" + line);
            }
        }

        //Si han compilado bien los test devuelve 0
        exitCode = testCompilatorProcess.waitFor();
        System.out.println("----------------------");
        System.out.println("Compiling TEST exit code: " + exitCode);
        System.out.println("----------------------");
        if(exitCode!=0){
            throw new CompilationErrorException(appErrorMsg.toString());
        }

        //JUnit 5 console execution
        ProcessBuilder testExecutor = new ProcessBuilder("java",
                "-jar",
                "lib/junit-platform-console-standalone-1.11.3.jar",
                "execute", //Si no se usa "execute" la consola en la version 1.11.3 da un warning
                "-cp", //--class-path -cp
                ".",
                "-c", //--select-class -c
                testClassName,
                "--details=tree", //Modes: [flat, verbose, tree]
                "--disable-banner");
        testExecutor.redirectErrorStream(true);
        Process testExecutorProcess = testExecutor.start();

        //Se esperan 15 segundos antes de destruir el proceso para evitar bucles infinitos
        if(!testExecutorProcess.waitFor(15, TimeUnit.SECONDS)){
            testExecutorProcess.destroy();
            throw new TimeoutException("Exceeded the 15 seconds time limit");
        }else{
            try(BufferedReader br = new BufferedReader(new InputStreamReader(testExecutorProcess.getInputStream()))){
                System.out.println("Resultado de la ejecucion:");
                String line;
                while((line=br.readLine())!=null){
                    System.out.println(line);
                }
            }
        }

        File javaClassFile = new File(javaClassName + ".class");
        File javaJavaFile = new File(javaFile);

        javaClassFile.delete();
        javaJavaFile.delete();

        File testClassFile = new File(testClassName + ".class");
        File testJavaFile = new File(testFile);

        testClassFile.delete();
        testJavaFile.delete();
    }
}
