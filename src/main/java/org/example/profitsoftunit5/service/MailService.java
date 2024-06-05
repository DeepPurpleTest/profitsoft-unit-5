package org.example.profitsoftunit5.service;

import lombok.RequiredArgsConstructor;
import org.example.profitsoftunit5.model.model.MailStatus;
import org.example.profitsoftunit5.model.model.TaskMail;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class MailService {

	private final JavaMailSender mailSender;
	private final SimpleMailMessage messageTemplate;
	private final TaskMailService taskMailService;

	public void sendMessages() {
		List<TaskMail> createdTasks = taskMailService.getTasksNeedToSend();
		Map<TaskMail, List<SimpleMailMessage>> messages = new HashMap<>();

		createdTasks.forEach(task -> messages.put(task, createMessagesForTask(task)));

		messages.forEach(this::sendMessagesForTask);

		taskMailService.saveAll(messages.keySet());
	}

	private List<SimpleMailMessage> createMessagesForTask(TaskMail task) {
		List<SimpleMailMessage> messages = new ArrayList<>();

		SimpleMailMessage reporterMessage = createMessage(task, task.getReporterEmail(), "reporter");
		messages.add(reporterMessage);

		if (task.getAssigneeEmail() != null && !task.getAssigneeEmail().isEmpty()) {
			SimpleMailMessage assigneeMessage = createMessage(task, task.getAssigneeEmail(), "assignee");
			messages.add(assigneeMessage);
		}

		return messages;
	}

	private SimpleMailMessage createMessage(TaskMail task, String recipientEmail, String role) {
		SimpleMailMessage message = new SimpleMailMessage();
		String text = String.format(
				Objects.requireNonNull(messageTemplate.getText()),
				role,
				task.getTaskName(),
				task.getTaskDescription());

		message.setTo(recipientEmail);
		message.setText(text);

		return message;
	}

	private void sendMessagesForTask(TaskMail task, List<SimpleMailMessage> messages) {
		for (SimpleMailMessage message : messages) {
			try {
				mailSender.send(message);
				task.setStatus(MailStatus.SENT);
			} catch (Exception e) {
				task.setStatus(MailStatus.FAILED);
				task.setErrorMessage(e.getClass().getName() + ": " + e.getMessage());
			}
		}
	}
}
