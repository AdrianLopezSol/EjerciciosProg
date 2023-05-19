package com.fpmislata.ejercicio14_3;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cglib.core.internal.Function;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@SpringBootApplication
public class Ejercicio143Application {

	@Bean
	public Function<String, String> currentUrlWithoutParam() {
    return param ->   ServletUriComponentsBuilder.fromCurrentRequest().replaceQueryParam(param).toUriString();
}

	public static void main(String[] args) {
		SpringApplication.run(Ejercicio143Application.class, args);
	}

}
