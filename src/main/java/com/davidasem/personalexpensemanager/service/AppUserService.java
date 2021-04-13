package com.davidasem.personalexpensemanager.service;

import com.davidasem.personalexpensemanager.exception.EmailExistException;
import com.davidasem.personalexpensemanager.exception.UserNotFoundException;
import com.davidasem.personalexpensemanager.exception.UsernameExistException;
import com.davidasem.personalexpensemanager.model.AppUser;

public interface AppUserService {

		AppUser register (String firstName, String lastName, String username, String email, String password)
				throws UserNotFoundException, UsernameExistException, EmailExistException;
		//List<Expense> getExpenses();
		AppUser findUserByUsername(String username);
		AppUser findUserByEmail(String email);
}

