package com.ludo.ms_user.utils;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Component;

import com.ludo.ms_user.config.JwtProperties;
import com.ludo.ms_user.entity.Role;
import com.ludo.ms_user.entity.UserAccount;

import java.nio.charset.StandardCharsets;

import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtils {
	
	private final SecretKey accessKey;
	private final SecretKey refreshKey;
	private Long accessDuration;
	private Long refreshDuration;
	private final String issuer;
	private final String audience;
	
	public JwtUtils(JwtProperties properties) {
		this.accessKey = Keys.hmacShaKeyFor(properties.getAccessSecret().getBytes(StandardCharsets.UTF_8));
		this.refreshKey = Keys.hmacShaKeyFor(properties.getRefreshSecret().getBytes(StandardCharsets.UTF_8));
		this.accessDuration = properties.getAccessDuration();
		this.refreshDuration = properties.getRefreshDuration();
		this.issuer = properties.getIssuer();
		this.audience = properties.getAudience();
	}
	
	public Claims validateAccessToken(String token) {
		return Jwts.parser()
				.verifyWith(accessKey)
				.requireIssuer(issuer)
				.requireAudience(audience)
				.build()
				.parseSignedClaims(token)
				.getPayload();
	}
	
	public Claims validateRefreshToken(String token) {
		return Jwts.parser()
				.verifyWith(refreshKey)
				.requireIssuer(issuer)
				.requireAudience(audience)
				.build()
				.parseSignedClaims(token)
				.getPayload();
	}
	
	public JwtPairs signToken(UserAccount user) {
		
		Date now = new Date();
		Date accessExpiration = new Date(System.currentTimeMillis() + accessDuration);
		Date refreshExpiration = new Date(System.currentTimeMillis() + refreshDuration);
		
		String subject = user.getUserId().toString();
		
		List<String> roles = user.getRoles().stream()
				.map(r -> "ROLE_" + r.getName())
				.toList();
		
		Set<String> permissions = new LinkedHashSet<>();
		
		for (Role role : user.getRoles()) {
			role.getPermissions()
				.forEach(permission -> permissions.add(permission.getCode()));
		}
		List<String> permissionsList = List.copyOf(permissions);
		
		
		String accessToken = Jwts.builder()
				.header().add("typ", "JWT").and()
				.subject(subject)
				.issuer(issuer)
				.audience().add(audience).and()
				.claim("roles", roles)
				.claim("permissions", permissionsList)
				.issuedAt(now)
				.expiration(accessExpiration)
				.signWith(accessKey)
				.compact();
		
		String refreshToken = Jwts.builder()
				.header().add("typ", "JWT").and()
				.subject(subject)
				.issuer(issuer)
				.audience().add(audience).and()
				.claim("type", "REFRESH")
				.issuedAt(now)
				.expiration(refreshExpiration)
				.signWith(refreshKey)
				.compact();
		
		return JwtPairs.builder()
			.accessToken(accessToken)
			.refreshToken(refreshToken)
			.build();
	}
}
