package com.shinhan.bullsandbears.domain.real.user;

import org.springframework.stereotype.Service;

@Service
public class UserUseCase {

    private final UserRepository userRepository;

    public UserUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User login(String email, String password) {
        return new User(); // TODO(채윤): 로그인 기능 구현
    }

    // TODO(채윤) 포트폴리오 검색 기록 조회 or 장바구니 담기?
}
