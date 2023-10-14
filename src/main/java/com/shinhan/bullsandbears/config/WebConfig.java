package com.shinhan.bullsandbears.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.shinhan.bullsandbears.domain.jwt.JwtTokenInterceptor;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

	private final JwtTokenInterceptor jwtTokenInterceptor;

	@Bean
	public FilterRegistrationBean<CorsFilter> corsFilter() {
		CorsConfiguration config = new CorsConfiguration();
		config.addAllowedOrigin("http://localhost:3000");
		config.addAllowedMethod("*");
		config.addAllowedHeader("*");
		config.setAllowCredentials(true);

		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", config);

		FilterRegistrationBean<CorsFilter> bean = new FilterRegistrationBean<>(new CorsFilter(source));
		bean.setOrder(Ordered.HIGHEST_PRECEDENCE);
		return bean;
	}

	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(jwtTokenInterceptor)
			.addPathPatterns("/api/v1/**")
			.excludePathPatterns("/api/v1/users/login",
				"/api/v1/users/user",
				"/api/v1/users/token",
				"/api/v1/users/id/{id}",
				"/api/v1/users/email/{email}",
				"/api/v1/users/nickname/{id}/{nickname}",
				"/api/v1/users/email/auth/{email}"
			);
	}
}
