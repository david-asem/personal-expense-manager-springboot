package com.davidasem.personalexpensemanager.utils;

import com.davidasem.personalexpensemanager.constant.SecurityConstant;
import com.davidasem.personalexpensemanager.restmodels.HttpResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Component
public class JwtAuthenticationEntryPoint extends Http403ForbiddenEntryPoint {

		@Override
		public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception)
				throws IOException {

				HttpResponse httpResponse = new HttpResponse(HttpStatus.FORBIDDEN.value(), HttpStatus.FORBIDDEN, HttpStatus.FORBIDDEN.getReasonPhrase().toUpperCase(),
						SecurityConstant.FORBIDDEN_MESSAGE);
					response.setContentType(APPLICATION_JSON_VALUE);
					response.setStatus(HttpStatus.FORBIDDEN.value());
					OutputStream outputStream = response.getOutputStream();
					ObjectMapper objectMapper = new ObjectMapper();
				objectMapper.writeValue(outputStream, httpResponse);
				outputStream.flush();
		}


}
