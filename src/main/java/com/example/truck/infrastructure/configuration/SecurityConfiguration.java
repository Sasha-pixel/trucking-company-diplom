package com.example.truck.infrastructure.configuration;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.authority.mapping.SimpleAuthorityMapper;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter;
import org.springframework.security.web.authentication.session.RegisterSessionAuthenticationStrategy;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

import com.example.truck.infrastructure.security.apikey.ApiKeyAuthenticationFilter;
import com.example.truck.infrastructure.security.apikey.ApiKeyAuthenticationProvider;
import com.example.truck.infrastructure.security.keycloak.TeslerKeycloakAuthenticationProvider;
import org.keycloak.adapters.KeycloakConfigResolver;
import org.keycloak.adapters.springboot.KeycloakSpringBootConfigResolver;
import org.keycloak.adapters.springsecurity.KeycloakConfiguration;
import org.keycloak.adapters.springsecurity.KeycloakSecurityComponents;
import org.keycloak.adapters.springsecurity.authentication.KeycloakAuthenticationProvider;
import org.keycloak.adapters.springsecurity.config.KeycloakWebSecurityConfigurerAdapter;
import io.tesler.api.service.session.TeslerAuthenticationService;
import io.tesler.core.config.properties.UIProperties;
import io.tesler.core.metahotreload.conf.properties.MetaConfigurationProperties;

import static io.tesler.core.config.properties.APIProperties.TESLER_API_PATH_SPEL;

@EnableWebSecurity
@ComponentScan(basePackageClasses = KeycloakSecurityComponents.class)
@RequiredArgsConstructor
@KeycloakConfiguration
@Configuration
public class SecurityConfiguration extends KeycloakWebSecurityConfigurerAdapter {

	private final TeslerAuthenticationService teslerAuthenticationService;

	private final TeslerKeycloakAuthenticationProvider teslerKeycloakAuthenticationProvider;

	private final ApiKeyAuthenticationProvider apiKeyAuthenticationProvider;

	private final MetaConfigurationProperties metaConfigurationProperties;

	private final UIProperties uiProperties;

	@Value(TESLER_API_PATH_SPEL + "${app.api-path}" + "/**")
	private String apiURI;

	@Value("${app.authentication.api-key-header}")
	private String apiKeyHeader;

	@Override
	protected void configure(final HttpSecurity http) throws Exception {
		super.configure(http);
		http.csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());
		http.cors();
		http.headers().frameOptions().sameOrigin();
		if (metaConfigurationProperties.isDevPanelEnabled()) {
			http.authorizeRequests().antMatchers("/api/v1/bc-registry/**").authenticated();
		} else {
			http.authorizeRequests().antMatchers("/api/v1/bc-registry/**").denyAll();
		}
		http
				.csrf().disable()
				.authorizeRequests(authorizeRequests -> authorizeRequests
						.antMatchers("/rest/**").permitAll()
						.antMatchers("/css/**").permitAll()
						.antMatchers(uiProperties.getPath() + "/**").permitAll()
						.antMatchers("/api/v1/file/**").permitAll()
						.antMatchers("/api/v1/auth/**").permitAll()
						.antMatchers(apiURI).authenticated()
						.antMatchers("/actuator/**").authenticated()
						.antMatchers("/**").fullyAuthenticated()
				)
				.addFilterBefore(new ApiKeyAuthenticationFilter(authenticationManagerBean(), apiURI, apiKeyHeader), AnonymousAuthenticationFilter.class);
	}

	@Override
	public void configure(final AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
		KeycloakAuthenticationProvider keycloakAuthenticationProvider = keycloakAuthenticationProvider();
		keycloakAuthenticationProvider.setGrantedAuthoritiesMapper(new SimpleAuthorityMapper());
		authenticationManagerBuilder.authenticationProvider(keycloakAuthenticationProvider);
		authenticationManagerBuilder.authenticationProvider(apiKeyAuthenticationProvider);
		authenticationManagerBuilder.userDetailsService(teslerAuthenticationService);
	}

	@Override
	protected KeycloakAuthenticationProvider keycloakAuthenticationProvider() {
		return teslerKeycloakAuthenticationProvider;
	}

	@Bean
	@Override
	protected SessionAuthenticationStrategy sessionAuthenticationStrategy() {
		return new RegisterSessionAuthenticationStrategy(new SessionRegistryImpl());
	}

	@Bean
	public KeycloakConfigResolver keycloakConfigResolver() {
		return new KeycloakSpringBootConfigResolver();
	}

	@Bean(BeanIds.AUTHENTICATION_MANAGER)
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

}
