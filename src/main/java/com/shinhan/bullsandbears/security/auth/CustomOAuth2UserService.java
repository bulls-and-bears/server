package com.shinhan.bullsandbears.security.auth;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.shinhan.bullsandbears.domain.user.User;
import com.shinhan.bullsandbears.domain.user.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

	private final UserRepository userRepository;

	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
		OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
		OAuth2User oAuth2User = delegate.loadUser(userRequest);

		String registrationId = userRequest.getClientRegistration()
			.getRegistrationId();

		String userNameAttributeName = userRequest.getClientRegistration()
			.getProviderDetails()
			.getUserInfoEndpoint()
			.getUserNameAttributeName();

		Map<String, Object> attributes = oAuth2User.getAttributes();

		UserProfile userProfile = OAuthAttributes.extract(registrationId, attributes);
		userProfile.setProvider(registrationId);
		saveOrUpdate(userProfile);

		Map<String, Object> customAttribute = getCustomAttribute(registrationId, userNameAttributeName, attributes,
			userProfile);

		return new DefaultOAuth2User(
			Collections.singleton(new SimpleGrantedAuthority("USER")),
			customAttribute,
			userNameAttributeName);
	}

	private void saveOrUpdate(UserProfile userProfile) {
		User user = userRepository
			.findByEmailAndProvider(userProfile.getEmail(), userProfile.getProvider())
			.map(value -> value.update(userProfile.getName()))
			.orElse(userProfile.toEntity());
		userRepository.save(user);
	}

	private Map<String, Object> getCustomAttribute(String registrationId,
		String userNameAttributeName,
		Map<String, Object> attributes,
		UserProfile userProfile) {
		Map<String, Object> customAttribute = new ConcurrentHashMap<>();
		customAttribute.put(userNameAttributeName, attributes.get(userNameAttributeName));
		customAttribute.put("email", userProfile.getEmail());
		customAttribute.put("name", userProfile.getName());
		customAttribute.put("provider", registrationId);
		return customAttribute;
	}
}
