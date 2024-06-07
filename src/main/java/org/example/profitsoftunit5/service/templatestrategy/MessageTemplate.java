package org.example.profitsoftunit5.service.templatestrategy;

import org.example.profitsoftunit5.model.model.MailType;
import org.example.profitsoftunit5.model.model.TaskMail;
import org.springframework.mail.SimpleMailMessage;

public interface MessageTemplate {

	MailType getType();

	SimpleMailMessage createMessage(TaskMail taskMail);
}
