package org.example.profitsoftunit5.service.templatestrategy.impl;

import org.example.profitsoftunit5.model.model.MailType;
import org.example.profitsoftunit5.model.model.TaskMail;
import org.example.profitsoftunit5.service.templatestrategy.MessageTemplate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;

@Component
public class ReporterTemplate implements MessageTemplate {

	@Override
	public MailType getType() {
		return MailType.REPORTER_NOTIFICATION;
	}

	@Override
	public SimpleMailMessage createMessage(TaskMail taskMail) {
		SimpleMailMessage message = new SimpleMailMessage();

		String template = "[%s] Task '%s' has been reported by you";
		String text = String.format(
				template,
				taskMail.getProjectName(),
				taskMail.getTaskName());

		message.setTo(taskMail.getReceiver().getEmail());
		message.setText(text);

		return message;
	}
}
