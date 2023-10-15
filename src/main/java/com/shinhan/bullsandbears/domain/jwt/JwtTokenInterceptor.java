package com.shinhan.bullsandbears.domain.jwt;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtTokenInterceptor implements HandlerInterceptor {

	private final JwtService jwtService;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
		throws Exception {
		Cookie[] cookies = request.getCookies();
		if (cookies == null) {
			throw new Exception("no cookie");
		}

		for (Cookie cookie : cookies) {
			if (cookie.getName().equals("accessToken")) {
				String accessToken = cookie.getValue();
				if (jwtService.verifyToken(accessToken)) {
					return true;
				}
			}
		}
		return false;
	}
}
