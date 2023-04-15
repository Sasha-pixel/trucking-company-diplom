package ru.filit.notifications.business.controller;

import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.filit.notifications.infrastructure.configuration.property.KeycloakConfigurationProperties;

@RestController
@RequestMapping("/api/v1/")
@RequiredArgsConstructor
public class KeycloakConfigController {

	private final KeycloakConfigurationProperties keycloakConfigurationProperties;

	@GetMapping("/auth/keycloak.json")
	public ResponseEntity<Map<String, Object>> get() {
		return ResponseEntity.ok(keycloakConfigurationProperties.getKeycloak());
	}

}
