package com.learning.securedapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

@SpringBootApplication
/**
 * <p>Application class.</p>
 *
 * @author rajakolli
 * @version $Id: $Id
 */
public class Application extends SpringBootServletInitializer {

	/** {@inheritDoc} */
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		System.setProperty("spring.profiles.active", System.getProperty("spring.profiles.active", "local"));
		return application.sources(Application.class);
	}

	/**
	 * <p>main.</p>
	 *
	 * @param args an array of {@link java.lang.String} objects.
	 */
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
