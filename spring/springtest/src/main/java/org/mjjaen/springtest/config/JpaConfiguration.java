package org.mjjaen.springtest.config;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/*
 * This is required because if JPA is configured on your application's main class, you're indicating that jpa repositories must always be enabled,
 * irrespective of which particular slice of functionality you're trying to test. This will prevent unit testing from working as the repository bean
 * will always be created, even if only testing the controller.
 */
@EnableJpaRepositories
@EnableJpaAuditing
public class JpaConfiguration {
}
