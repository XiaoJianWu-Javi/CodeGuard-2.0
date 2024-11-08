package es.tfg.codeguard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
public class CodeGuardApplication {

	public static void main(String[] args) {
		SpringApplication.run(CodeGuardApplication.class, args);
	}

}
