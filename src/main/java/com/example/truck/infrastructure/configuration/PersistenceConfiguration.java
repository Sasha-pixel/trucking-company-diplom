package com.example.truck.infrastructure.configuration;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.PlatformTransactionManager;

import io.tesler.api.config.TeslerBeanProperties;
import io.tesler.api.service.tx.ITransactionStatus;
import io.tesler.model.core.config.PersistenceJPAConfig;
import io.tesler.model.core.tx.TeslerJpaTransactionManagerForceActiveAware;

@EnableJpaRepositories(basePackages = {"com.example"})
@EntityScan({"io.tesler", "com.example.truck"})
@Import(PersistenceJPAConfig.class)
@Configuration
public class PersistenceConfiguration {

	@Bean
	public PlatformTransactionManager transactionManager(
			final ApplicationContext applicationContext,
			final TeslerBeanProperties teslerBeanProperties,
			final ITransactionStatus txStatus) {
		return new TeslerJpaTransactionManagerForceActiveAware(applicationContext, teslerBeanProperties, txStatus);
	}

}
