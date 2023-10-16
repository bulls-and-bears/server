package com.shinhan.bullsandbears.web;

import com.shinhan.bullsandbears.domain.real.user.User;
import com.shinhan.bullsandbears.domain.real.user.UserRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthController {

    private final UserRepository userRepository;

    public HealthController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * DB 커넥션 확인을 위한 엔드포인트입니다.
     * 테스트 유저를 생성하고 데이터베이스에 저장하여 데이터베이스 연결이 올바르게 작동하는지 확인합니다.
     *
     * @return 성공적으로 데이터베이스가 연결되면 OK를 반환합니다.
     */
    @GetMapping("/health")
    public String health() {
        User testUser = new User();
        testUser.setEmail("PrimaryKey");
        userRepository.save(testUser);
        return "ok";
    }
}
