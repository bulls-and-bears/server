package com.shinhan.bullsandbears.domain.user.service;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.shinhan.bullsandbears.domain.user.Role;
import com.shinhan.bullsandbears.domain.user.User;
import com.shinhan.bullsandbears.domain.user.dto.UserSignUpDto;
import com.shinhan.bullsandbears.domain.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

	private final UserRepository userRepository;

	public void signUp(UserSignUpDto dto) throws Exception {

		if (userRepository.findByEmail(dto.getEmail()).isPresent()) {
			throw new Exception("이미 존재하는 이메일입니다.");
		}

		if (userRepository.findByNickname(dto.getNickname()).isPresent()) {
			throw new Exception("이미 존재하는 닉네임입니다.");
		}

		User user = User.builder()
			.email(dto.getEmail())
			.nickname(dto.getNickname())
			.role(Role.USER)
			.build();

		userRepository.save(user);
	}
}
