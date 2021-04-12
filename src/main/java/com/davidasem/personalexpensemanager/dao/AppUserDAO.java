package com.davidasem.personalexpensemanager.dao;


import com.davidasem.personalexpensemanager.model.AppUser;



public interface AppUserDAO {

		AppUser findUserByUsername( String username);
		AppUser findUserByEmail( String email);



		AppUser  saveNewUser(AppUser appUser);
}
