package com.shinhan.bullsandbears.auth;

import com.shinhan.bullsandbears.domain.User;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserProfile {

	private String email;
	private String name;
	private String provider;

	public User toEntity() {
		return User.builder()
			.email(this.email)
			.name(this.name)
			.provider(this.provider)
			.build();
	}
}
