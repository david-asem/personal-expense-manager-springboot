package com.davidasem.personalexpensemanager.utils;

import com.davidasem.personalexpensemanager.constant.SecurityConstant;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Component
public class JwtAuthorizationFilter extends OncePerRequestFilter {
		private final JWTGenerator jwtGenerator;

		public JwtAuthorizationFilter(JWTGenerator jwtGenerator) {
				this.jwtGenerator = jwtGenerator;
		}

		@Override
		protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
				FilterChain filterChain) throws ServletException, IOException {

				if(request.getMethod().equalsIgnoreCase(SecurityConstant.OPTIONS_HTTP_METHOD)){
						response.setStatus(HttpStatus.OK.value());
				}else{
						String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
								if(authorizationHeader == null || authorizationHeader.startsWith(SecurityConstant.TOKEN_HEADER)){
											filterChain.doFilter(request, response);
										return;
								}

							String token = authorizationHeader.substring(SecurityConstant.TOKEN_HEADER.length());
							String username = jwtGenerator.getSubject(token);
							if(jwtGenerator.isValidToken(username, token)){
							List<GrantedAuthority> permissions = jwtGenerator.getPermissions(token);
							Authentication authentication = jwtGenerator.getAuthentication(username, permissions, request);
							SecurityContextHolder.getContext().setAuthentication(authentication);
						}
							else{
					SecurityContextHolder.clearContext();
								}
		}
				filterChain.doFilter(request, response);
}
}
