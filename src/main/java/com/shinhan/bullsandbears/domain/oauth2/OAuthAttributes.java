package com.shinhan.bullsandbears.domain.oauth2;

import java.util.Map;
import java.util.UUID;

import com.shinhan.bullsandbears.domain.oauth2.userInfo.GoogleOAuth2UserInfo;
import com.shinhan.bullsandbears.domain.oauth2.userInfo.OAuth2UserInfo;
import com.shinhan.bullsandbears.domain.user.Role;
import com.shinhan.bullsandbears.domain.user.User;

import lombok.Builder;
import lombok.Getter;

@Getter
public class OAuthAttributes {

	private final String nameAttributeKey;
	private final OAuth2UserInfo oauth2UserInfo;

	@Builder
	public OAuthAttributes(String nameAttributeKey, OAuth2UserInfo oauth2UserInfo) {
		this.nameAttributeKey = nameAttributeKey;
		this.oauth2UserInfo = oauth2UserInfo;
	}

	public static OAuthAttributes of(String userNameAttributeName, Map<String, Object> attributes) {
		return OAuthAttributes.builder()
			.nameAttributeKey(userNameAttributeName)
			.oauth2UserInfo(new GoogleOAuth2UserInfo(attributes))
			.build();
	}

	public User toEntity(OAuth2UserInfo oAuth2UserInfo) {
		return User.builder()
			.socialId(oauth2UserInfo.getId())
			.email(UUID.randomUUID() + "@socialUser.com")
			.nickname(oauth2UserInfo.getNickname())
			.role(Role.GUEST)
			.build();
	}
}
