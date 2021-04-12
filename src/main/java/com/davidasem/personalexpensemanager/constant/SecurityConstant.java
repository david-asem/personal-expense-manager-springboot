package com.davidasem.personalexpensemanager.constant;

public class SecurityConstant {
		public static final long EXPIRATION_TIME = 432_000_000;  //5 days
		public static final String TOKEN_HEADER = "Bearer ";
		public static final String JWT_TOKEN_HEADER = "Jwt-Token";
		public static final String TOKEN_CANNOT_BE_VERIFIED = "Token cannot be verified";
		public static final String TOKEN_ISSUER = "Intuition Personal Expense Manager";
		public static final String PERMISSIONS = "Permissions";
		public static final String FORBIDDEN_MESSAGE = "You need to log in to access this page";
		public static final String ACCESS_DENIED_MESSAGE =
				"You do not have permission to access this page";
		public static final String OPTIONS_HTTP_METHOD = "OPTIONS";
		public static final String[] PUBLIC_URLS =
				{"/api/v1/user/signup", "/api/v1/user/login", "/api/v1/user/resetpassword/**",
						"/api/v1/user/image/**"};

}
