package org.example.profitsoftunit5.service.templatestrategy.impl;

import org.example.profitsoftunit5.model.event.NotificationType;
import org.example.profitsoftunit5.model.model.TaskMail;
import org.example.profitsoftunit5.service.templatestrategy.MessageTemplate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;

@Component
public class AssigneeTemplate implements MessageTemplate {
	@Override
	public NotificationType getType() {
		return NotificationType.ASSIGNEE_NOTIFICATION;
	}

	@Override
	public SimpleMailMessage createMessage(TaskMail taskMail) {
		SimpleMailMessage message = new SimpleMailMessage();

		String subjectTemplate = "[%s] Task '%s' assigned to you";
		String subject = String.format(
				subjectTemplate,
				taskMail.getTask().getProjectName(),
				taskMail.getTask().getTaskName());

		String textTemplate = "This issue assigned to you %s %s ";
		String text = String.format(
				textTemplate,
				taskMail.getTask().getTaskName(),
				taskMail.getTask().getTaskDescription());

		message.setTo(taskMail.getReceiver().getEmail());
		message.setSubject(subject);
		message.setText(text);

		return message;
	}
}
