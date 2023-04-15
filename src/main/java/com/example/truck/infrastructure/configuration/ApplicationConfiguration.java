package com.example.truck.infrastructure.configuration;

import io.tesler.core.config.APIConfig;
import io.tesler.core.config.CoreApplicationConfig;
import io.tesler.core.config.UIConfig;
import io.tesler.core.config.properties.APIProperties;
import io.tesler.core.config.properties.UIProperties;
import io.tesler.core.file.service.TeslerFileService;
import io.tesler.core.file.service.TeslerFileServiceSimple;
import io.tesler.model.core.config.PersistenceJPAConfig;
import java.util.concurrent.Executors;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.concurrent.ConcurrentTaskExecutor;
import org.springframework.web.servlet.config.annotation.AsyncSupportConfigurer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
@Import({
		CoreApplicationConfig.class,
		PersistenceJPAConfig.class,
		UIConfig.class,
		APIConfig.class
})
@EnableJpaRepositories(basePackages = "com.example.truck")
@EntityScan({"io.tesler", "com.example.truck"})
public class ApplicationConfiguration {

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(final CorsRegistry registry) {
				registry
						.addMapping("/**")
						.allowedMethods("*")
						.allowedOrigins("*")
						.allowedHeaders("*");
			}

			@Override
			public void configureAsyncSupport(AsyncSupportConfigurer configurer) {
				configurer.setTaskExecutor(getTaskExecutor());
			}
		};
	}

	@Bean
	protected ConcurrentTaskExecutor getTaskExecutor() {
		return new ConcurrentTaskExecutor(Executors.newFixedThreadPool(5));
	}

	@Bean
	TeslerFileService teslerFileService(@Value("${tesler.file.folder:}") final String fileFolder) {
		return new TeslerFileServiceSimple(fileFolder);
	}

	@Bean
	@ConfigurationProperties("tesler.api")
	public APIProperties apiProperties() {
		return new APIProperties();
	}

	@Bean
	@ConfigurationProperties("tesler.ui")
	public UIProperties uiProperties() {
		return new UIProperties();
	}

}
