package com.ludo.ms_user.filter;

import java.io.IOException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.stereotype.Component;

import org.springframework.web.filter.OncePerRequestFilter;

import com.ludo.ms_user.exception.global.InvalidAccessTokenException;
import com.ludo.ms_user.utils.JwtUtils;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

/*
	DEUX VERSION POSSIBLE 
	
	
		VERSION 1 :
			LAISSER LA GATEWAY VERIFIER LE JWT ET ENREGISTRER LE USER, LES ROLES & LES PERMISSIONS DANS LE HEADER DE LA REQUETE
			PUIS LES RECUPPERER DANS LE MICROSERVICE POUR L'AUTHENTIFICATION
			
		VERSION 2 :
			VERIFIER LE JWT DANS LE SERVICE EN LUI MEME ET ENREGISTRER LE USER, LES ROLES & LES PERMISSIONS DANS LE HEADER DE LA REQUETE
			PUIS LES RECUPPERER DANS LE MICROSERVICE POUR L'AUTHENTIFICATION
*/
	
	
//	VERSION 1 :
/*
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		String userId = request.getHeader("X-User-Id");
		String roleHeader = request.getHeader("X-Roles");
		String permissionHeader = request.getHeader("X-Permissions");
	
		if (userId == null || userId.isBlank()) {
			filterChain.doFilter(request, response);
			return;
		}
		
		List<SimpleGrantedAuthority> authorities = new ArrayList<>();
		
		if (roleHeader != null && !roleHeader.isBlank()) {
			Arrays.stream(roleHeader.split(","))
				.map(String::trim)
				.filter(role -> !role.isEmpty())
				.map(role -> new SimpleGrantedAuthority(role))
				.forEach(authorities::add);
		}
		
		if (permissionHeader != null && !permissionHeader.isBlank()) {
			Arrays.stream(permissionHeader.split(","))
				.map(String::trim)
				.filter(permission -> !permission.isEmpty())
				.map(permission -> new SimpleGrantedAuthority(permission))
				.forEach(authorities::add);
		}
		
		UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
			userId, null, authorities
		);
		
		SecurityContextHolder.getContext().setAuthentication(authentication);
		filterChain.doFilter(request, response);
	}
 */
	

//	VERSION 2 :
	private final JwtUtils jwtUtils;
	
	public JwtAuthenticationFilter(JwtUtils jwtUtils) {
		this.jwtUtils = jwtUtils;
	}
		
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException, InvalidAccessTokenException {

		String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
		
		if (authHeader == null || !authHeader.startsWith("Bearer ")) {
			throw new InvalidAccessTokenException();
		}
		String token = authHeader.substring(7);
		
		if (token.isEmpty()) {
			throw new InvalidAccessTokenException();			
		}
		
		try {
			
			Claims claims = jwtUtils.validateAccessToken(token);
			String userId = claims.getSubject();
			
			List<String> roles = getStringListClaim(claims, "roles");
			List<String> permissions = getStringListClaim(claims, "permissions");
			
			Collection<GrantedAuthority> authorities = new ArrayList<>();
			
			for (String role : roles) {
				authorities.add(new SimpleGrantedAuthority(role));
			}

			for (String permission : permissions) {
				authorities.add(new SimpleGrantedAuthority(permission));
			}
			
			UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userId, null, authorities);
			SecurityContextHolder.getContext().setAuthentication(authentication);
			
			filterChain.doFilter(request, response);
			
		} catch (InvalidAccessTokenException e) {
			throw e;
		} catch (Exception e) {
			SecurityContextHolder.clearContext();
			throw new InvalidAccessTokenException();
		}
	}
	
	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) {
		String path = request.getRequestURI();

		return path.startsWith("/auth/login")
			|| path.startsWith("/auth/register")
			|| path.startsWith("/auth/confirm-account")
			|| path.startsWith("/auth/resend-confirm-token")
			|| path.startsWith("/auth/refresh-session")
			|| path.startsWith("/auth/ask-reset-password")
			|| path.startsWith("/auth/reset-password/")
			|| path.equals("/actuator/health") 
			|| path.equals("/actuator/prometheus")
			|| request.getMethod().equalsIgnoreCase("OPTIONS");
	}

	private List<String> getStringListClaim(Claims claims, String claimName) {
		Object claim = claims.get(claimName);
		
		if (!(claim instanceof List<?> rawValues)) {
			throw new IllegalArgumentException("Invalid " + claimName + " claim");
		}
		
		return rawValues.stream()
			.map(val -> {
				if (!(val instanceof String)) {
					throw new IllegalArgumentException("Invalid " + claimName + " claim");
				}
				
				return (String) val;
			})
			.toList();
	}
}