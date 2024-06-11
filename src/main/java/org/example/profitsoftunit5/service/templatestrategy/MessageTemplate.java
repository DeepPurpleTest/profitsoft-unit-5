package org.example.profitsoftunit5.service.templatestrategy;

import org.example.profitsoftunit5.model.event.NotificationType;
import org.example.profitsoftunit5.model.model.TaskMail;
import org.springframework.mail.SimpleMailMessage;

public interface MessageTemplate {

	NotificationType getType();

	SimpleMailMessage createMessage(TaskMail taskMail);
}
