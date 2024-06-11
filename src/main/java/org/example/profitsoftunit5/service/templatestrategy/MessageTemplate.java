package org.example.profitsoftunit5.service.templatestrategy;

import org.example.profitsoftunit5.model.event.NotificationType;
import org.example.profitsoftunit5.model.model.TaskMail;
import org.springframework.mail.SimpleMailMessage;

/**
 * The MessageTemplate interface defines methods for creating email messages based on a specific notification type.
 * Implementations of this interface are responsible for generating SimpleMailMessage objects tailored for different types of notifications.
 */
public interface MessageTemplate {

	/**
	 * Gets the notification type associated with this message template.
	 */
	NotificationType getType();

	/**
	 * Creates a SimpleMailMessage based on the provided TaskMail object.
	 */
	SimpleMailMessage createMessage(TaskMail taskMail);
}
