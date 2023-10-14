package com.shinhan.bullsandbears.controller;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shinhan.bullsandbears.domain.jwt.Jwt;
import com.shinhan.bullsandbears.domain.jwt.JwtService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/oauth")
public class UserController {

	private final JwtService jwtService;

	@GetMapping("/loginInfo")
	public String oauthLoginInfo(Authentication authentication) {
		OAuth2User oAuth2User = (OAuth2User)authentication.getPrincipal();
		Map<String, Object> attributes = oAuth2User.getAttributes();
		return attributes.toString();
	}

	@GetMapping("/jwt")
	public ResponseEntity<Jwt> oauthJwt(Authentication authentication) {
		OAuth2User oAuth2User = (OAuth2User)authentication.getPrincipal();
		Map<String, Object> attributes = oAuth2User.getAttributes();
		Jwt jwt = jwtService.generateToken((String)attributes.get("email"), "google", (String)attributes.get("name"));
		return new ResponseEntity<>(jwt, HttpStatus.OK);
	}
}
