package com.shinhan.bullsandbears.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shinhan.bullsandbears.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User> findByEmailAndProvider(String email, String provider);

	Optional<User> findByName(String name);
}
