package com.davidasem.personalexpensemanager.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data @NoArgsConstructor @AllArgsConstructor public class AppUser implements Serializable {

		@Id
		@SequenceGenerator(name = "user_sequence", sequenceName = "user_sequence", allocationSize = 1)
		@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_sequence")
		@Column(nullable = false, updatable = false) private Long userId;
		private String firstName;
		private String lastName;
		private String email;
		private String password;
		private String username;
		private String profileImageUrl;
		private Date lastLoginDate;
		private Date lastLoginDateDisplay;
		private Date createdDate;
		private Boolean isDisabled;
		private Boolean isEnabled;
		private Expense expense;
		@Enumerated(EnumType.STRING) private AppUserRole appUserRole;
		private String[] permissions;

}
