package org.springdoc.demo.reactive;

import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.GroupedOpenApi;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	public GroupedOpenApi employeesOpenApi(@Value("${springdoc.version}") String appVersion) {
		String[] paths = { "/employees/**" };
		return GroupedOpenApi.builder().group("employees")
				.addOpenApiCustomiser(openApi -> openApi.info(new Info().title("Employees API").version(appVersion)))
				.pathsToMatch(paths)
				.build();
	}

	@Bean
	public GroupedOpenApi userOpenApi(@Value("${springdoc.version}") String appVersion) {
		String[] paths = { "/api/user/**" };
		return GroupedOpenApi.builder().group("users")
				.addOpenApiCustomiser(openApi -> openApi.info(new Info().title("Users API").version(appVersion)))
				.pathsToMatch(paths)
				.build();
	}

	@Bean
	public GroupedOpenApi coffeeOpenApi(@Value("${springdoc.version}") String appVersion) {
		String[] paths = { "/coffees/**" };
		return GroupedOpenApi.builder().group("coffees")
				.addOpenApiCustomiser(openApi -> openApi.info(new Info().title("Coffees API").version(appVersion)))
				.pathsToMatch(paths)
				.build();
	}

	@Bean
	public GroupedOpenApi weatherOpenApi(@Value("${springdoc.version}") String appVersion) {
		String[] paths = { "/api/weather/**" };
		return GroupedOpenApi.builder().group("weather")
				.addOpenApiCustomiser(openApi -> openApi.info(new Info().title("Weather API").version(appVersion)))
				.pathsToMatch(paths)
				.build();
	}



	@Bean
	public GroupedOpenApi geoOpenApi(@Value("${springdoc.version}") String appVersion) {
		String[] paths = { "/api/geo/**" };
		return GroupedOpenApi.builder().group("geo")
				.addOpenApiCustomiser(openApi -> openApi.info(new Info().title("Geo API").version(appVersion)))
				.pathsToMatch(paths)
				.build();
	}
}
