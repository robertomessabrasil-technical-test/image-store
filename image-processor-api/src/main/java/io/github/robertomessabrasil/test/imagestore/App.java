package io.github.robertomessabrasil.test.imagestore;

import io.github.robertomessabrasil.test.imagestore.security.AwsConfigProperties;
import io.github.robertomessabrasil.test.imagestore.security.LocalConfigProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableConfigurationProperties({AwsConfigProperties.class, LocalConfigProperties.class})
@EnableScheduling
public class App {
	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
	}
}
