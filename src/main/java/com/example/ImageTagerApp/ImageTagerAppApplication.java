package com.example.ImageTagerApp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ImageTagerAppApplication {
	public static final String APPLICATION_LOCATIONS = "spring.config.location="
			+ "classpath:application.properties";

	public static void main(String[] args) {
		SpringApplication.run(ImageTagerAppApplication.class, args);
	}

}
