package ru.filit.notifications.infrastructure.configuration.property;

import java.util.Map;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Setter
@Getter
@Component
@ConfigurationProperties("app")
public class KeycloakConfigurationProperties {

	private Map<String, Object> keycloak;

}
