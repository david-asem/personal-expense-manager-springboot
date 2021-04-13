package com.davidasem.personalexpensemanager.config;

import com.davidasem.personalexpensemanager.constant.SecurityConstant;
import com.davidasem.personalexpensemanager.utils.JwtAccessDeniedHandler;
import com.davidasem.personalexpensemanager.utils.JwtAuthenticationEntryPoint;
import com.davidasem.personalexpensemanager.utils.JwtAuthorizationFilter;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;



@AllArgsConstructor @Configuration @EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true) public class SecurityConfig
		extends WebSecurityConfigurerAdapter {
		private final JwtAccessDeniedHandler jwtAccessDeniedHandler;
		private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
		private final JwtAuthorizationFilter jwtAuthorizationFilter;
		@Qualifier("appUserDetailsService")
		private final UserDetailsService userDetailsService;
		private final BCryptPasswordEncoder bCryptPasswordEncoder;



		@Override protected void configure(AuthenticationManagerBuilder auth) throws Exception {
				auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
		}


		@Override protected void configure(HttpSecurity http) throws Exception {
				http.csrf().disable().cors().and().sessionManagement().sessionCreationPolicy(
						SessionCreationPolicy.STATELESS) //not keeping session ; using JWT
						.and().authorizeRequests().antMatchers(SecurityConstant.PUBLIC_URLS).permitAll()
						.anyRequest().authenticated().and().exceptionHandling()
						.accessDeniedHandler(jwtAccessDeniedHandler)
						.authenticationEntryPoint(jwtAuthenticationEntryPoint).and()
						.addFilterBefore(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class);
		}


		@Bean @Override public AuthenticationManager authenticationManager() throws Exception {
				return super.authenticationManager();
		}
}
