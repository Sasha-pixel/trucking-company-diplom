package com.example.truck.infrastructure.security.apikey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import org.apache.commons.lang3.StringUtils;

@Component
public class ApiKeyAuthenticationProvider implements AuthenticationProvider {

	@Value("${app.authentication.api-key}")
	private String validApiKey;

	@Override
	public Authentication authenticate(final Authentication authentication) throws AuthenticationException {
		final String apiKey = (String) authentication.getPrincipal();

		if (StringUtils.isBlank(apiKey)) {
			throw new InsufficientAuthenticationException("No API key in request");
		} else {
			if (validApiKey.equals(apiKey)) {
				return new ApiKeyAuthenticationToken(apiKey, true);
			}
			throw new BadCredentialsException("API Key is invalid");
		}
	}

	@Override
	public boolean supports(final Class<?> authentication) {
		return ApiKeyAuthenticationToken.class.isAssignableFrom(authentication);
	}

}
