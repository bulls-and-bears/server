package com.shinhan.bullsandbears;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
public class BullsAndBearsApplication {

    public static void main(String[] args) {
        SpringApplication.run(BullsAndBearsApplication.class, args);
    }

}
