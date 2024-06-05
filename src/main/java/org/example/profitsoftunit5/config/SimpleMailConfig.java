package org.example.profitsoftunit5.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.SimpleMailMessage;

@Configuration
public class SimpleMailConfig {

	@Bean
	public SimpleMailMessage receiverTemplateMessage() {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setText(
				"You set as %s on task: %s \n description: %s");
		return message;
	}
}
