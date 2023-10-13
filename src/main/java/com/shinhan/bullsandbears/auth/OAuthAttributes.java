package com.shinhan.bullsandbears.auth;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;

public enum OAuthAttributes {

	GOOGLE("google", (attributes) -> {
		UserProfile userProfile = new UserProfile();
		userProfile.setName((String)attributes.get("name"));
		userProfile.setEmail((String)attributes.get("email"));
		return userProfile;
	});

	private final String registrationId;
	private final Function<Map<String, Object>, UserProfile> of;

	OAuthAttributes(String registrationId, Function<Map<String, Object>, UserProfile> of) {
		this.registrationId = registrationId;
		this.of = of;
	}

	public static UserProfile extract(String registrationId, Map<String, Object> attributes) {
		return Arrays.stream(values())
			.filter(provider -> registrationId.equals(provider.registrationId))
			.findFirst()
			.orElseThrow(IllegalArgumentException::new)
			.of.apply(attributes);
	}
}
