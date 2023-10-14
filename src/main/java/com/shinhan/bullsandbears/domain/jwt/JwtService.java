package com.shinhan.bullsandbears.domain.jwt;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class JwtService {

	@Value("${jwt.secretKey}")
	private String secretKey;

	static final long ACCESS_PERIOD = 1000L * 60L * 10L * 6L; // 1시간
	static final long REFRESH_PERIOD = 1000L * 60L * 60L * 24L * 30L; // 3달
	static final String DATE_FORMAT = "yyyy-MM-dd-HH-mm-ss";

	@PostConstruct
	protected void init() {
		secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
	}

	public Jwt generateToken(String email, String name, String provider) {
		Claims claims = Jwts.claims().setSubject("token");
		claims.put("email", email);
		claims.put("name", name);
		claims.put("provider", provider);

		Date now = new Date();

		return new Jwt(
			Jwts.builder()
				.setClaims(claims)
				.setIssuedAt(now)
				.setExpiration(new Date(now.getTime() + ACCESS_PERIOD))
				.signWith(SignatureAlgorithm.HS256, secretKey)
				.compact(),
			Jwts.builder()
				.setClaims(claims)
				.setIssuedAt(now)
				.setExpiration(new Date(now.getTime() + REFRESH_PERIOD))
				.signWith(SignatureAlgorithm.HS256, secretKey)
				.compact());
	}

	public boolean verifyToken(String token) {
		try {
			Jws<Claims> claims = Jwts.parser()
				.setSigningKey(secretKey)
				.parseClaimsJws(token);
			return claims.getBody()
				.getExpiration()
				.after(new Date());
		} catch (Exception e) {
			return false;
		}
	}

	public String getEmail(String token) {
		return (String)Jwts.parser()
			.setSigningKey(secretKey)
			.parseClaimsJws(token)
			.getBody()
			.get("email");
	}

	public String getProvider(String token) {
		return (String)Jwts.parser()
			.setSigningKey(secretKey)
			.parseClaimsJws(token)
			.getBody()
			.get("provider");
	}

	public String getName(String token) {
		return (String)Jwts.parser()
			.setSigningKey(secretKey)
			.parseClaimsJws(token)
			.getBody()
			.get("name");
	}

	public Date getExpiration(String token) {
		return (Date)Jwts.parser()
			.setSigningKey(secretKey)
			.parseClaimsJws(token)
			.getBody()
			.getExpiration();
	}

	public String dateToString(String token) {
		DateFormat expirationFormat = new SimpleDateFormat(DATE_FORMAT);
		Date tokenExpirationDate = getExpiration(token);
		return expirationFormat.format(tokenExpirationDate);
	}
}
