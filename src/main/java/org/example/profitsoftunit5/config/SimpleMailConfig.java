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

	/**
	 * Configures a map of {@link NotificationType} to {@link MessageTemplate}.
	 *
	 * @param messageTemplates the list of message templates to be used
	 * @return a map where the key is the {@link NotificationType} and the value is the corresponding {@link MessageTemplate}
	 */
	@Bean
	public Map<NotificationType, MessageTemplate> mailTemplates(List<MessageTemplate> messageTemplates) {
		return messageTemplates.stream().collect(Collectors.toMap(
				MessageTemplate::getType,
				v -> v
		));
	}
}
