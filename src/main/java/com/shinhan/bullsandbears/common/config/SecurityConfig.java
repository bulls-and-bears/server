package com.shinhan.bullsandbears.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

import com.shinhan.bullsandbears.common.security.auth.CustomOAuth2UserService;

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
                .authorizeRequests() //TODO(배포 시 제거) 개발 환경: 모든 요청 허용
                .antMatchers("/**").permitAll()   //TODO(배포 시 제거) 개발 환경: 모든 요청 허용
                .and()//TODO(배포 시 제거) 개발 환경: 모든 요청 허용
                .csrf().disable()
                .formLogin().disable()
                .logout().disable()
                .httpBasic().disable()
                .headers().frameOptions().disable()

                .and()
                .cors().configurationSource(webConfig.corsConfigurationSource())

                .and()
                .oauth2Login()
                .defaultSuccessUrl("/oauth/token", true)
                .userInfoEndpoint()
                .userService(customOAuth2UserService);

        return http.build();
    }
}
