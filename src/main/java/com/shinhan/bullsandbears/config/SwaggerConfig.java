package com.shinhan.bullsandbears.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class SwaggerConfig {

	@Bean
	public OpenAPI swagger() {
		Info info = new Info();
		info.title("Bulls & Bears")
			.version("1.0.0")
			.description("");
		return new OpenAPI().info(info);
	}
}