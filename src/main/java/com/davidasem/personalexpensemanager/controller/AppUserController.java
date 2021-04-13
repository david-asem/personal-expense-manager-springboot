package com.davidasem.personalexpensemanager.controller;


import com.davidasem.personalexpensemanager.exception.EmailExistException;
import com.davidasem.personalexpensemanager.exception.UserNotFoundException;
import com.davidasem.personalexpensemanager.exception.UsernameExistException;
import com.davidasem.personalexpensemanager.exceptionhandling.ExceptionHandling;
import com.davidasem.personalexpensemanager.model.AppUser;
import com.davidasem.personalexpensemanager.service.AppUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = {"/", "api/v1"})
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AppUserController extends ExceptionHandling {

				private final AppUserService appUserService;

				@PostMapping("/register")
				public ResponseEntity<AppUser>register(@RequestBody AppUser appUser)
						throws UserNotFoundException, UsernameExistException, EmailExistException {
						AppUser newUser = appUserService.register(appUser.getFirstName(),
								appUser.getLastName(), appUser.getUsername(),
								appUser.getEmail(), appUser.getPassword() );

						return new ResponseEntity<>(newUser, HttpStatus.OK);
				}






}
