package es.tfg.codeguard.compilerTest;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TestCompiler {

    public static void main(String[] args) throws IOException, InterruptedException {
        File jsonFile = new File("src/main/java/es/tfg/codeguard/compilerTest/test.json");
        JsonObject jsonObject;
        try(FileReader fr = new FileReader(jsonFile)){
            jsonObject = JsonParser.parseReader(fr).getAsJsonObject();
        }

        Pattern javaClassPattern = Pattern.compile("(?:public)(?:\\s+)(?:class)(?:\\s+)(\\w+)");
        String javaCode = jsonObject.get("javaCode").getAsString();
        Matcher classNameMatcher = javaClassPattern.matcher(javaCode);
        String className = "";
        if(classNameMatcher.find()){
            className = classNameMatcher.group(1);
        }else{
            throw new IllegalArgumentException();
        }

        String javaFile = className + ".java";

        try(BufferedWriter bw = new BufferedWriter(new FileWriter(javaFile))){
            bw.write(javaCode);
        }

        //Compilation
        ProcessBuilder compilator = new ProcessBuilder("javac", javaFile);
        //Hay que redirecionar el stream de error para que se muestren los mensajes de erro
        compilator.redirectErrorStream(true);
        Process compilatorProcess = compilator.start();

        try(BufferedReader br = new BufferedReader(new InputStreamReader(compilatorProcess.getInputStream()))){
            String line;
            while((line=br.readLine())!=null){
                System.out.println(line);
            }
        }

        //Si ha salido bien el programa devuelve 0
        int exitCode = compilatorProcess.waitFor();
        System.out.println("----------------------");
        System.out.println("Compiling exit code: " + exitCode);
        System.out.println("----------------------");

        //Execution
        ProcessBuilder executioner = new ProcessBuilder("java", className);
        //Hay que redirecionar el stream de error para que se muestren los mensajes de erro
        executioner.redirectErrorStream(true);
        Process executionerProcess = executioner.start();

        try(BufferedReader br = new BufferedReader(new InputStreamReader(executionerProcess.getInputStream()))){
            System.out.println("Resultado de la ejecucion:");
            String line;
            while((line=br.readLine())!=null){
                System.out.println(line);
            }
        }

        //Si ha salido bien el programa devuelve 0
        exitCode = executionerProcess.waitFor();
        System.out.println("----------------------");
        System.out.println("Execution exit code: " + exitCode);
        System.out.println("----------------------");

        File javaClassFile = new File(className + ".class");
        File javaJavaFile = new File(javaFile);

        javaClassFile.delete();
        javaJavaFile.delete();

    }

}
