package com.shinhan.bullsandbears.domain.oauth2.userInfo;

import java.util.Map;

public abstract class OAuth2UserInfo {

	protected Map<String, Object> attributes;

	public OAuth2UserInfo(Map<String, Object> attributes) {
		this.attributes = attributes;
	}

	// 소셜 식별 값: 구글 "sub"
	public abstract String getId();

	public abstract String getNickname();
}
