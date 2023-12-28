package com.programming.techie.youtubeclone1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.core.SpringVersion;


@SpringBootApplication
@ConfigurationPropertiesScan
public class YoutubeClone1Application {

	public static void main(String[] args) {
		SpringApplication.run(YoutubeClone1Application.class, args);
		System.out.println("Spring Boot version" + SpringVersion.getVersion());
	}
}
