package com.davidasem.personalexpensemanager.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.davidasem.personalexpensemanager.constant.SecurityConstant;
import com.davidasem.personalexpensemanager.model.AppUserPrincipal;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Arrays.stream;

@Component public class JWTGenerator {

		@Value("${jwt.secret}") private String secretKey;

		//generating token
		public String generateJwtToken(AppUserPrincipal appUserPrincipal) {
				String[] claims = getClaimsFromAppUser(appUserPrincipal);
				return JWT.create().withIssuer(SecurityConstant.TOKEN_ISSUER).withIssuedAt(new Date())
						.withSubject(appUserPrincipal.getUsername())
						.withArrayClaim(SecurityConstant.PERMISSIONS, claims)
						.withExpiresAt(new Date(System.currentTimeMillis() + SecurityConstant.EXPIRATION_TIME))
						.sign(Algorithm.HMAC512(secretKey.getBytes()));
		}

		//list of permissions from the tokens
		public List<GrantedAuthority> getPermissions(String token) {
				String[] claims = getClaimsFromToken(token);
				return stream(claims).map(SimpleGrantedAuthority::new).collect(Collectors.toList());
		}

		public Authentication getAuthentication(String userName, List<GrantedAuthority> permissions,
				HttpServletRequest request) {
				UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
						new UsernamePasswordAuthenticationToken(userName, permissions);
				usernamePasswordAuthenticationToken
						.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				return usernamePasswordAuthenticationToken;
		}


		//check if token is valid
		public boolean isValidToken(String userName, String token) {
				JWTVerifier verifier = getJWTVerifier();
				return StringUtils.isNotEmpty(userName) && !isTokenExpired(verifier, token);
		}

		private boolean isTokenExpired(JWTVerifier verifier, String token) {
				Date expiration = verifier.verify(token).getExpiresAt();
				return expiration.before(new Date());
		}

		//get subject and verify it
		public String getSubject(String token) {
				JWTVerifier verifier = getJWTVerifier();
				return verifier.verify(token).getSubject();
		}


		//get all claims or permissions from token
		private String[] getClaimsFromToken(String token) {
				JWTVerifier verifier = getJWTVerifier();
				return verifier.verify(token).getClaim(SecurityConstant.PERMISSIONS).asArray(String.class);
		}


		//verifies the token against the issued token
		private JWTVerifier getJWTVerifier() {
				JWTVerifier verifier;
				try {
						Algorithm algo = Algorithm.HMAC512(secretKey);
						verifier = JWT.require(algo).withIssuer(SecurityConstant.TOKEN_ISSUER).build();

				} catch (JWTVerificationException exception) {
						throw new JWTVerificationException(SecurityConstant.TOKEN_CANNOT_BE_VERIFIED);
				}

				return verifier;
		}


		//get all claims or permissions of the user
		private String[] getClaimsFromAppUser(AppUserPrincipal appUser) {
				List<String> permissions = new ArrayList<>();
				for (GrantedAuthority grantedAuthority : appUser.getAuthorities()) {
						permissions.add(grantedAuthority.getAuthority());
				}

				return permissions.toArray(new String[0]);
		}
}
