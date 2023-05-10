package com.example.truck.infrastructure.security.apikey;

import java.io.IOException;
import java.util.Optional;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

public class ApiKeyAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

	private final String apiKeyHeader;

	public ApiKeyAuthenticationFilter(final AuthenticationManager authenticationManager, final String processesUrl,
			final String apiKeyHeader) {
		super(processesUrl, authenticationManager);
		this.apiKeyHeader = apiKeyHeader;
	}

	@Override
	public Authentication attemptAuthentication(final HttpServletRequest request, final HttpServletResponse response) {
		return getAuthenticationManager().authenticate(
				Optional.ofNullable(request.getHeader(apiKeyHeader)).map(ApiKeyAuthenticationToken::new)
						.orElse(new ApiKeyAuthenticationToken())
		);
	}

	@Override
	protected void successfulAuthentication(final HttpServletRequest request, final HttpServletResponse response,
			final FilterChain chain, final Authentication authResult) throws IOException, ServletException {
		SecurityContextHolder.getContext().setAuthentication(authResult);
		chain.doFilter(request, response);
	}

}
