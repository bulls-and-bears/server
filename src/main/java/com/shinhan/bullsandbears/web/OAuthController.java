package com.shinhan.bullsandbears.web;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.shinhan.bullsandbears.common.CustomException;
import com.shinhan.bullsandbears.common.CustomExceptionList;
import com.shinhan.bullsandbears.domain.user.User;
import com.shinhan.bullsandbears.security.jwt.Jwt;
import com.shinhan.bullsandbears.security.jwt.JwtService;
import com.shinhan.bullsandbears.domain.user.UserRepository;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/oauth")
public class OAuthController {

    private final JwtService jwtService;
    private final UserRepository userRepository;

    @GetMapping("/token")
    public void createToken(HttpServletResponse response, HttpServletRequest request,
                            Authentication authentication) throws Exception {
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        Map<String, Object> attributes = oAuth2User.getAttributes();

        User user = getAuthUser(attributes);
        Jwt token = jwtService.generateToken(user.getEmail(), user.getProvider(), user.getName());
        user.setRefreshToken(token.getRefreshToken());
        userRepository.save(user);

        Cookie accessTokenCookie = new Cookie("accessToken", token.getAccessToken());
        accessTokenCookie.setHttpOnly(true);
        accessTokenCookie.setPath("/");
        response.addCookie(accessTokenCookie);

        response.sendRedirect(UriComponentsBuilder.fromUriString("http://localhost:3000")
                .queryParam("name", user.getName())
                .build()
                .encode(StandardCharsets.UTF_8)
                .toUriString());
    }

    @GetMapping("/refresh")
    public ResponseEntity<Map<String, String>> checkRefreshToken(HttpServletRequest request,
                                                                 HttpServletResponse response) {
        String refreshToken = request.getHeader("refreshToken");
        if (!jwtService.verifyToken(refreshToken)) {
            throw new CustomException(CustomExceptionList.REFRESH_TOKEN_ERROR);
        }

        String email = jwtService.getEmail(refreshToken);
        String provider = jwtService.getProvider(refreshToken);
        String name = jwtService.getName(refreshToken);

        User user = userRepository.findByEmailAndProvider(email, provider)
                .orElseThrow(() -> new CustomException(CustomExceptionList.NO_SUCH_USER_ERROR));

        if (!user.getRefreshToken().equals(refreshToken)) {
            throw new CustomException(CustomExceptionList.REFRESH_TOKEN_ERROR);
        }

        Jwt token = jwtService.generateToken(email, name, provider);
        String accessTokenExpiration = jwtService.dateToString(token.getAccessToken());
        Map<String, String> map = new HashMap<>();
        map.put("accessToken", token.getAccessToken());
        map.put("accessTokenExpiration", accessTokenExpiration);

        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    private User getAuthUser(Map<String, Object> attributes) {
        return userRepository.findByEmailAndProvider((String) attributes.get("email"),
                        (String) attributes.get("provider"))
                .orElseThrow(() -> new CustomException(CustomExceptionList.NO_SUCH_USER_ERROR));
    }
}
