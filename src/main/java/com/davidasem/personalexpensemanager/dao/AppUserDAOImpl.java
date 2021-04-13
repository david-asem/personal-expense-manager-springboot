package com.davidasem.personalexpensemanager.dao;

import com.davidasem.personalexpensemanager.model.AppUser;
import com.davidasem.personalexpensemanager.utils.CustomPropertyParamSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository public class AppUserDAOImpl extends BeanPropertyRowMapper implements AppUserDAO {

		private static final Logger log = LoggerFactory.getLogger(AppUserDAOImpl.class);

		private final JdbcTemplate jdbcTemplate;


		@Autowired public AppUserDAOImpl(JdbcTemplate jdbcTemplate) {
				this.jdbcTemplate = jdbcTemplate;
		}

		@Override public AppUser findUserByUsername(String username) {
				try {
						String sql = "SELECT * FROM USER  WHERE username = ?";
						return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(AppUser.class), username);
				} catch (EmptyResultDataAccessException ignored){
						return null;
				}

		}

		@Override
		public AppUser findUserByEmail(String email) {
				return null;
		}

		@Override
		public AppUser saveNewUser(AppUser appUser) {
				try {
						String query = "INSERT INTO USER (FIRST_NAME, LAST_NAME, USERNAME, EMAIL_ADDRESS, PASSWORD) VALUES (?,?,?,?,?)";
						jdbcTemplate.update(query, appUser.getFirstName(), appUser.getLastName(), appUser.getUsername(), new CustomPropertyParamSource(appUser));
				}
				catch (Exception e){
						return null;

				}

			return appUser;
		}
}
