package com.davidasem.personalexpensemanager.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service public class AppUserServiceImpl implements AppUserService, UserDetailsService {


		@Override public UserDetails loadUserByUsername(String username)
				throws UsernameNotFoundException {
				return null;
		}
}
