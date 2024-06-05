package org.example.profitsoftunit5.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.SimpleMailMessage;

@Configuration
public class SimpleMailConfig {

	// create map with templates?
	@Bean
	public SimpleMailMessage assigneeTemplateMessage() {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setText(
				"[Profitsoft-unit-2] %s assigned %s to you");
		return message;
	}
}
