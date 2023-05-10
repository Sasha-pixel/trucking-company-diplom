package com.example.truck.infrastructure.security.keycloak;

import java.util.ArrayList;
import java.util.Set;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.example.truck.business.repository.DepartmentRepository;
import com.example.truck.business.repository.UserRepository;
import org.hibernate.LockOptions;
import org.keycloak.adapters.springsecurity.account.SimpleKeycloakAccount;
import org.keycloak.adapters.springsecurity.authentication.KeycloakAuthenticationProvider;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.keycloak.representations.AccessToken;
import io.tesler.api.data.dictionary.LOV;
import io.tesler.api.service.session.InternalAuthorizationService;
import io.tesler.api.service.tx.TransactionService;
import io.tesler.core.service.impl.UserRoleService;
import io.tesler.core.util.SQLExceptions;
import io.tesler.model.core.dao.JpaDao;
import io.tesler.model.core.entity.User;
import io.tesler.model.core.entity.User_;

import static io.tesler.api.service.session.InternalAuthorizationService.VANILLA;

@Component
@Slf4j
@RequiredArgsConstructor
public class TeslerKeycloakAuthenticationProvider extends KeycloakAuthenticationProvider {

	private final UserRepository userRepository;

	private final DepartmentRepository departmentRepository;

	private final JpaDao jpaDao;

	private final TransactionService txService;

	private final InternalAuthorizationService authzService;

	private final UserRoleService userRoleService;

	@Override
	public Authentication authenticate(final Authentication authentication) throws AuthenticationException {
		final Authentication auth = super.authenticate(authentication);
		final KeycloakAuthenticationToken accessToken = (KeycloakAuthenticationToken) auth;
		final SimpleKeycloakAccount account = (SimpleKeycloakAccount) accessToken.getDetails();

		txService.invokeInTx(() -> {
			upsertUserAndRoles(
					account.getKeycloakSecurityContext().getToken(),
					accessToken.getAccount().getRoles()
			);
			return null;
		});

		return authentication;
	}

	//TODO>>taken "as is" from real project - refactor
	private void upsertUserAndRoles(final AccessToken accessToken, final Set<String> roles) {
		txService.invokeInNewTx(() -> {
			authzService.loginAs(authzService.createAuthentication(VANILLA));
			User user = null;
			try {
				user = getUserByLogin(accessToken.getName().toUpperCase());
				if (user == null) {
					upsert(accessToken, roles.stream().findFirst().orElse(null));
				}
				user = getUserByLogin(accessToken.getName().toUpperCase());
				userRoleService.upsertUserRoles(user.getId(), new ArrayList<>(roles));
			} catch (Exception e) {
				log.error(e.getLocalizedMessage(), e);
			}

			if (user == null) {
				throw new UsernameNotFoundException(null);
			}
			SecurityContextHolder.getContext().setAuthentication(null);
			return null;
		});
	}

	//TODO>>taken "as is" from real project - refactor
	public User upsert(final AccessToken accessToken, final String role) {
		txService.invokeInNewTx(() -> {
					authzService.loginAs(authzService.createAuthentication(VANILLA));
					for (int i = 1; i <= 10; i++) {
						final User existing = getUserByLogin(accessToken.getName().toUpperCase());
						if (existing != null) {
							jpaDao.lockAndRefresh(existing, LockOptions.WAIT_FOREVER);
							updateUser(accessToken, role, existing);
							return existing;
						}
						try {
							final User newUser = new User();
							updateUser(accessToken, role, newUser);
							Long id = txService.invokeNoTx(() -> userRepository.save(newUser).getId());
							return userRepository.findById(id);
						} catch (Exception ex) {
							if (SQLExceptions.isUniqueConstraintViolation(ex)) {
								log.error(ex.getLocalizedMessage(), ex);
							} else {
								throw ex;
							}
						}
					}
					SecurityContextHolder.getContext().setAuthentication(null);
					return null;
				}
		);
		return null;
	}

	private User getUserByLogin(final String login) {
		return userRepository.findOne(
				(root, cq, cb) -> cb.equal(cb.upper(root.get(User_.login)), login.toUpperCase())
		).orElse(null);
	}

	private void updateUser(final AccessToken accessToken, final String role, final User user) {
		if (user.getLogin() == null) {
			user.setLogin(accessToken.getName().toUpperCase());
		}

		user.setInternalRole(new LOV(role));
		user.setUserPrincipalName(accessToken.getName());
		user.setFirstName(accessToken.getGivenName());
		user.setLastName(accessToken.getFamilyName());
		user.setTitle(accessToken.getName());
		user.setFullUserName(accessToken.getName());
		user.setEmail(accessToken.getEmail());
		user.setPhone(accessToken.getPhoneNumber());
		user.setActive(true);
		user.setDepartment(departmentRepository.findById(0L).orElse(null));
	}

}
