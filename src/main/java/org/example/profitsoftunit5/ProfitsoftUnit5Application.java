package org.example.profitsoftunit5;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
public class ProfitsoftUnit5Application {

	public static void main(String[] args) {
		SpringApplication.run(ProfitsoftUnit5Application.class, args);
	}

}
