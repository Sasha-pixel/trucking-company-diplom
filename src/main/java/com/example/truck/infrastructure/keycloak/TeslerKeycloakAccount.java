package com.example.truck.infrastructure.keycloak;


import io.tesler.api.data.dictionary.LOV;
import io.tesler.api.service.session.TeslerUserDetailsInterface;
import java.security.Principal;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.keycloak.adapters.RefreshableKeycloakSecurityContext;
import org.keycloak.adapters.springsecurity.account.SimpleKeycloakAccount;
import org.springframework.security.core.GrantedAuthority;

@Getter
@Setter
@Accessors(chain = true)
public class TeslerKeycloakAccount extends SimpleKeycloakAccount implements TeslerUserDetailsInterface {

	private static final long serialVersionUID = 4714671346784362939L;

	private Long id;

	private String username;

	private String password;

	private LOV userRole;

	private LOV timezone;

	private LOV localeCd;

	private Set<GrantedAuthority> authorities;

	public TeslerKeycloakAccount(
			final Principal principal,
			final Set<String> roles,
			final RefreshableKeycloakSecurityContext securityContext) {
		super(principal, roles, securityContext);
	}

	public boolean isAccountNonExpired() {
		return true;
	}

	public boolean isAccountNonLocked() {
		return true;
	}

	public boolean isCredentialsNonExpired() {
		return true;
	}

	public boolean isEnabled() {
		return true;
	}

}
