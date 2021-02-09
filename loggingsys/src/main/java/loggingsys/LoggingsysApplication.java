package loggingsys;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(basePackages = {"message"})
public class LoggingsysApplication {
	public static void main(String[] args) {
		SpringApplication.run(LoggingsysApplication.class, args);


	}
}
