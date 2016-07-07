package com.learning.securedapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

@SpringBootApplication
public class Application extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        System.setProperty( "spring.profiles.active",
                System.getProperty( "spring.profiles.active", "local" ) );
        return application.sources(Application.class);
    }

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
