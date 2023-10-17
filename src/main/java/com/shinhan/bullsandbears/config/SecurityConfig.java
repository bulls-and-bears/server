package com.shinhan.bullsandbears.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

import com.shinhan.bullsandbears.auth.CustomOAuth2UserService;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

	private final CustomOAuth2UserService customOAuth2UserService;
	private final WebConfig webConfig;

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
			.csrf().disable()
			.formLogin().disable()
			.logout().disable()
			.httpBasic().disable()
			.headers().frameOptions().disable()

			.and()
			.cors().configurationSource(webConfig.corsConfigurationSource())

			.and()
			.oauth2Login()
			.defaultSuccessUrl("/api/v1/oauth/token", true)
			.userInfoEndpoint()
			.userService(customOAuth2UserService);

		return http.build();
	}
}
