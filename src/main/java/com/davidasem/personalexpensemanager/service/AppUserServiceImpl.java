package com.davidasem.personalexpensemanager.service;

import com.davidasem.personalexpensemanager.dao.AppUserDAOImpl;
import com.davidasem.personalexpensemanager.model.AppUser;
import com.davidasem.personalexpensemanager.model.AppUserPrincipal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service @Transactional @Qualifier("appUserDetailsService") public class AppUserServiceImpl
		implements AppUserService, UserDetailsService {

		private final Logger logger = LoggerFactory.getLogger(getClass());
		private final AppUserDAOImpl appUserDAO;

		@Autowired public AppUserServiceImpl(AppUserDAOImpl appUserDAO) {
				this.appUserDAO = appUserDAO;
		}


		//throws UsernameNotFoundException
		@Override public UserDetails loadUserByUsername(String username)
				throws UsernameNotFoundException {
						AppUser user = appUserDAO.findUserByUsername(username);
						if (user == null) {
								logger.error("Sorry, the username " + username + " does not exist");
								throw new UsernameNotFoundException("Sorry, the username " + username + " does not exist");
						} else {
								user.setLastLoginDateDisplay(user.getLastLoginDate());
								user.setLastLoginDate(new Date());
								appUserDAO.saveNewUser(user);
								AppUserPrincipal userPrincipal = new AppUserPrincipal(user);
								logger.info("Returning new user by username: " + username);
								return userPrincipal;

						}
}

}


