package com.davidasem.personalexpensemanager.service;

import com.davidasem.personalexpensemanager.dao.AppUserDAOImpl;
import com.davidasem.personalexpensemanager.exception.EmailExistException;
import com.davidasem.personalexpensemanager.exception.UserNotFoundException;
import com.davidasem.personalexpensemanager.exception.UsernameExistException;
import com.davidasem.personalexpensemanager.model.AppUser;
import com.davidasem.personalexpensemanager.model.AppUserPrincipal;
import com.davidasem.personalexpensemanager.model.AppUserRole;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.Date;

@Service @Transactional @Qualifier("appUserDetailsService")
public class AppUserServiceImpl implements AppUserService, UserDetailsService {

		private final Logger logger = LoggerFactory.getLogger(getClass());
		private final AppUserDAOImpl appUserDAO;
		private final BCryptPasswordEncoder bCryptPasswordEncoder;

		@Autowired
		public AppUserServiceImpl(AppUserDAOImpl appUserDAO, BCryptPasswordEncoder bCryptPasswordEncoder) {
				this.appUserDAO = appUserDAO;
				this.bCryptPasswordEncoder = bCryptPasswordEncoder;
		}


		//throws UsernameNotFoundException
		@Override
		public UserDetails loadUserByUsername(String username)
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

		@Override
		public AppUser register(String firstName, String lastName, String username, String email,
				String password) throws UserNotFoundException, UsernameExistException, EmailExistException {
				
				validateNewUsernameAndEmail(StringUtils.EMPTY, username, email);
				AppUser appUser = new AppUser();
				String encodedPassword = encryptPass(password);
				appUser.setFirstName(firstName);
				appUser.setLastName(firstName);
				appUser.setUsername(username);
				appUser.setEmail(email);
				appUser.setPassword(encodedPassword);
				appUser.setProfileImageUrl(tempProfilePicUrl());
				appUser.setCreatedDate(new Date());
				appUser.setIsDisabled(false);
				appUser.setIsEnabled(true);
				appUser.setAppUserRole(AppUserRole.USER);
				appUserDAO.saveNewUser(appUser);

				return null;
		}

		private String tempProfilePicUrl() {
				return ServletUriComponentsBuilder.fromCurrentContextPath().path("/user/me/profile/image").toUriString();
		}

		private String encryptPass(String password) {
				return bCryptPasswordEncoder.encode(password);
		}

		private AppUser validateNewUsernameAndEmail(String currentUsername, String newUsername, String newEmail)
				throws UserNotFoundException, UsernameExistException, EmailExistException {

				if (StringUtils.isNotBlank(currentUsername)){
						AppUser currentUser = findUserByUsername(currentUsername);
								if (currentUser == null) {
										throw new UserNotFoundException( "User does not exist");
								}

								AppUser userByNewUsername = findUserByUsername(newUsername);
								if (userByNewUsername != null && !currentUser.getUserId().equals(userByNewUsername.getUserId())){
										throw new UsernameExistException("Username already taken");
								}
								AppUser userByNewEmail = findUserByEmail(newEmail);
								if (userByNewEmail !=null && !currentUser.getUserId().equals(userByNewEmail.getUserId())){
										throw new EmailExistException("Email already exist");
								}
								return currentUser;
				}

				else {
						AppUser userByUsername = findUserByUsername(newUsername);
						if (userByUsername !=null){
								throw new UsernameExistException("Username already taken");
						}
						AppUser userByEmail = findUserByEmail(newEmail);
						if (userByEmail !=null){
								throw new EmailExistException("Email already taken");
						}
						return  null;

				}

		}

		@Override public AppUser findUserByUsername(String username) {
				return null;
		}

		@Override public AppUser findUserByEmail(String email) {
				return null;
		}
}


