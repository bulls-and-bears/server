package com.shinhan.bullsandbears.domain.user.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.shinhan.bullsandbears.domain.user.dto.UserSignUpDto;
import com.shinhan.bullsandbears.domain.user.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class UserController {

	private final UserService userService;

	@PostMapping("/sign-up")
	public String signUp(@RequestBody UserSignUpDto dto) throws Exception {
		userService.signUp(dto);
		return "회원가입 성공";
	}

	@GetMapping("/jwt-test")
	public String jwtTest() {
		return "jwt 테스트 성공";
	}
}
