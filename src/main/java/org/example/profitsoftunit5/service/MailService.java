package org.example.profitsoftunit5.service;

import lombok.RequiredArgsConstructor;
import org.example.profitsoftunit5.model.model.MailStatus;
import org.example.profitsoftunit5.model.model.TaskMail;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class MailService {

	private final JavaMailSender mailSender;
	private final SimpleMailMessage assigneeTemplate;
	private final TaskMailService taskMailService;

	public void sendMessages() {
		List<TaskMail> createdTasks = taskMailService.getTasksNeedToSend();
		Map<TaskMail, SimpleMailMessage> messages = new HashMap<>();

		createdTasks.forEach(task -> messages.put(task, createMessage(task)));

		messages.forEach(this::sendMessagesForTask);

		taskMailService.saveAll(messages.keySet());
	}

	private SimpleMailMessage createMessage(TaskMail task) {
		SimpleMailMessage message = new SimpleMailMessage();
		String text = String.format(
				Objects.requireNonNull(assigneeTemplate.getText()),
				task.getReporterName(),
				task.getTaskName());

		message.setTo(task.getAssigneeEmail());
		message.setText(text);

		return message;
	}

	private void sendMessagesForTask(TaskMail task, SimpleMailMessage message) {
		try {
			mailSender.send(message);
			task.setStatus(MailStatus.SENT);
		} catch (Exception e) {
			task.setStatus(MailStatus.FAILED);
			task.setErrorMessage(e.getClass().getName() + ": " + e.getMessage());
		}
	}
}
