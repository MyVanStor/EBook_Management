package com.example.EBook_Management_BE.common.filter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.util.Pair;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.EBook_Management_BE.common.components.JwtTokenUtil;
import com.example.EBook_Management_BE.common.entity.User;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtTokenFilter extends OncePerRequestFilter {
	@Value("${api.prefix}")
	private String apiPrefix;
	private final UserDetailsService userDetailsService;
	private final JwtTokenUtil jwtTokenUtil;

	@Override
	protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response,
			@NonNull FilterChain filterChain) throws ServletException, IOException {
		try {
			if (isBypassToken(request)) {
				filterChain.doFilter(request, response); // enable bypass
				return;
			}
			final String authHeader = request.getHeader("Authorization");
			if (authHeader == null || !authHeader.startsWith("Bearer ")) {
				response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "authHeader null or not started with Bearer");
				return;
			}
			final String token = authHeader.substring(7);
			final String phoneNumber = jwtTokenUtil.extractPhoneNumber(token);
			if (phoneNumber != null && SecurityContextHolder.getContext().getAuthentication() == null) {
				User userDetails = (User) userDetailsService.loadUserByUsername(phoneNumber);
				if (jwtTokenUtil.validateToken(token, userDetails)) {
					UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
							userDetails, null, userDetails.getAuthorities());
					authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
					SecurityContextHolder.getContext().setAuthentication(authenticationToken);
				}
			}
			filterChain.doFilter(request, response); // enable bypass
		} catch (Exception e) {
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			response.getWriter().write(e.getMessage());
		}

	}

	private boolean isBypassToken(@NonNull HttpServletRequest request) {
		final List<Pair<String, String>> bypassTokens = Arrays.asList(
				// Healthcheck request, no JWT token required
				Pair.of(String.format("%s/healthcheck/health", apiPrefix), "GET"),
				Pair.of(String.format("%s/actuator/**", apiPrefix), "GET"),

				Pair.of(String.format("%s/author**", apiPrefix), "GET"),
				Pair.of(String.format("%s/category**", apiPrefix), "GET"),
				Pair.of(String.format("%s/painter**", apiPrefix), "GET"),

				Pair.of(String.format("%s/book**", apiPrefix), "GET"),
				
				Pair.of(String.format("%s/rating/all**", apiPrefix), "GET"),

				Pair.of(String.format("%s/user/register", apiPrefix), "POST"),
				Pair.of(String.format("%s/user/login", apiPrefix), "POST"),
				Pair.of(String.format("%s/user/refreshToken", apiPrefix), "POST"),

				// Swagger
				Pair.of("/api-docs", "GET"), Pair.of("/api-docs/**", "GET"), Pair.of("/swagger-resources", "GET"),
				Pair.of("/swagger-resources/**", "GET"), Pair.of("/configuration/ui", "GET"),
				Pair.of("/configuration/security", "GET"), Pair.of("/swagger-ui/**", "GET"),
				Pair.of("/swagger-ui.html", "GET"), Pair.of("/swagger-ui/index.html", "GET"));

		String requestPath = request.getServletPath();
		String requestMethod = request.getMethod();

		for (Pair<String, String> token : bypassTokens) {
			String path = token.getFirst();
			String method = token.getSecond();
			// Check if the request path and method match any pair in the bypassTokens list
			if (requestPath.matches(path.replace("**", ".*")) && requestMethod.equalsIgnoreCase(method)) {
				return true;
			}
		}
		return false;
	}

}
