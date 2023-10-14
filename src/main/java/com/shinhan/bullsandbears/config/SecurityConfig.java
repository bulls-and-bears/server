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

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
			.csrf().disable()
			.formLogin().disable()
			.logout().disable()
			.httpBasic().disable()
			.headers().frameOptions().disable()

			.and()
			.authorizeRequests()
			.antMatchers("/", "/oauth/**").permitAll()
			.anyRequest().authenticated()

			.and()
			.oauth2Login()
			.defaultSuccessUrl("/oauth/info", true)
			.userInfoEndpoint()
			.userService(customOAuth2UserService);

		return http.build();
	}
}
