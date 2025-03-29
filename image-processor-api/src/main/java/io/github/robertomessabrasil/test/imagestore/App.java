package io.github.robertomessabrasil.test.imagestore;

import io.github.robertomessabrasil.test.imagestore.service.s3.ConfigProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(ConfigProperties.class)
public class App {

	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
	}

}
