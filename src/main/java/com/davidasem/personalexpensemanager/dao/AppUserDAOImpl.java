package com.davidasem.personalexpensemanager.dao;

import com.davidasem.personalexpensemanager.model.AppUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository public class AppUserDAOImpl extends BeanPropertyRowMapper implements AppUserDAO {

		@Autowired private static final Logger log = LoggerFactory.getLogger(AppUserDAOImpl.class);

		@Autowired private final JdbcTemplate jdbcTemplate;



		public AppUserDAOImpl(JdbcTemplate jdbcTemplate) {
				this.jdbcTemplate = jdbcTemplate;
		}

		@Override public AppUser findUserByUsername(String username) {
				String sql = "SELECT  FROM APPUSER WHERE  =? ";
				return null;
		}

		@Override public AppUser findUserByEmail(String email) {
				return null;
		}
}
