package org.example.profitsoftunit5.config;

import org.example.profitsoftunit5.model.event.NotificationType;
import org.example.profitsoftunit5.service.templatestrategy.MessageTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Configuration
public class SimpleMailConfig {

	@Bean
	public Map<NotificationType, MessageTemplate> mailTemplates(List<MessageTemplate> messageTemplates) {
		return messageTemplates.stream().collect(Collectors.toMap(
				MessageTemplate::getType,
				v -> v
		));
	}
}
