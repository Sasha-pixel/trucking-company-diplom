package com.example.truck.infrastructure.security.apikey;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.Transient;
import org.springframework.security.core.authority.AuthorityUtils;

@Transient
public class ApiKeyAuthenticationToken extends AbstractAuthenticationToken {

	private final String apiKey;

	public ApiKeyAuthenticationToken(final String apiKey, final boolean authenticated) {
		super(AuthorityUtils.NO_AUTHORITIES);
		this.apiKey = apiKey;
		setAuthenticated(authenticated);
	}

	public ApiKeyAuthenticationToken(final String apiKey) {
		this(apiKey, false);
	}

	public ApiKeyAuthenticationToken() {
		this(null, false);
	}

	@Override
	public Object getCredentials() {
		return null;
	}

	@Override
	public Object getPrincipal() {
		return apiKey;
	}

}
