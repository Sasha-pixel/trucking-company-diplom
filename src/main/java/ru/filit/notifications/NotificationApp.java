package ru.filit.notifications;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan("ru.filit.notifications.infrastructure.configuration")
public class NotificationApp {

	public static void main(String[] args) {
		SpringApplication.run(NotificationApp.class);
	}

}
