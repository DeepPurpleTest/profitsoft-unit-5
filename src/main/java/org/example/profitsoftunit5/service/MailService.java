package org.example.profitsoftunit5.service;

import lombok.RequiredArgsConstructor;
import org.example.profitsoftunit5.model.model.MailStatus;
import org.example.profitsoftunit5.model.model.MailType;
import org.example.profitsoftunit5.model.model.TaskMail;
import org.example.profitsoftunit5.service.templatestrategy.MessageTemplate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class MailService {

	private final JavaMailSender mailSender;
	private final Map<MailType, MessageTemplate> templates;
	private final TaskMailService taskMailService;

	public void sendMessages() {
		List<TaskMail> createdTasks = taskMailService.getTasksNeedToSend();
		Map<TaskMail, SimpleMailMessage> messages = new HashMap<>();

		createdTasks.forEach(task -> messages.put(task, createMessage(task)));

		messages.forEach(this::sendMessagesForTask);

		taskMailService.saveAll(messages.keySet());
	}

	private SimpleMailMessage createMessage(TaskMail taskMail) {
		if (taskMail.getType().equals(MailType.ASSIGNEE_NOTIFICATION)) {
			return templates.get(MailType.ASSIGNEE_NOTIFICATION).createMessage(taskMail);
		} else {
			return templates.get(MailType.REPORTER_NOTIFICATION).createMessage(taskMail);
		}
	}

	private void sendMessagesForTask(TaskMail task, SimpleMailMessage message) {
		try {
			mailSender.send(message);
			task.setStatus(MailStatus.SENT);
		} catch (Exception e) {
			task.setStatus(MailStatus.FAILED);
			task.setErrorMessage(e.getClass().getName() + ": " + e.getMessage());
		}

		task.setLastTry(LocalDateTime.now());
		task.setAttempts(task.getAttempts() + 1);
	}
}
