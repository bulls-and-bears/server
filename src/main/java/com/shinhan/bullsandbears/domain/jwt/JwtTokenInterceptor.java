package com.shinhan.bullsandbears.domain.jwt;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.shinhan.bullsandbears.common.CustomException;
import com.shinhan.bullsandbears.common.CustomExceptionList;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtTokenInterceptor implements HandlerInterceptor {

	private final JwtService jwtService;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals("accessToken")) {
					String accessToken = cookie.getValue();
					if (jwtService.verifyToken(accessToken)) {
						return true;
					}
				}
			}
		}
		throw new CustomException(CustomExceptionList.ACCESS_TOKEN_ERROR);
	}
}
