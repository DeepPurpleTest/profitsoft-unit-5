package org.example.profitsoftunit5.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.profitsoftunit5.model.event.NotificationType;
import org.example.profitsoftunit5.model.model.MailStatus;
import org.example.profitsoftunit5.model.model.TaskMail;
import org.example.profitsoftunit5.service.MailService;
import org.example.profitsoftunit5.service.templatestrategy.MessageTemplate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@RequiredArgsConstructor
public class MailServiceImpl implements MailService {

	private final JavaMailSender mailSender;
	private final Map<NotificationType, MessageTemplate> templates;
	private final TaskMailServiceImpl taskMailService;

	@Override
	@Scheduled(fixedDelayString = "${mails.delay}", timeUnit = TimeUnit.MINUTES)
	public void sendMessages() {
		log.info("Send messages -> start!");
		List<TaskMail> tasksToSend = taskMailService.getTasksNeedToSend();
		Map<TaskMail, SimpleMailMessage> messages = new HashMap<>();

		tasksToSend.forEach(task -> messages.put(task, createMessage(task)));

		messages.forEach(this::sendMessagesForTask);

		taskMailService.saveAll(messages.keySet());
	}

	private SimpleMailMessage createMessage(TaskMail taskMail) {
		MessageTemplate template = templates.get(taskMail.getNotificationType());
		if (template == null) {
			log.error("Template is not found for type: {}",  taskMail.getNotificationType());
			return new SimpleMailMessage();
		}

		return template.createMessage(taskMail);
	}

	private void sendMessagesForTask(TaskMail task, SimpleMailMessage message) {
		try {
			mailSender.send(message);
			task.setStatus(MailStatus.SENT);
		} catch (RuntimeException e) {
			task.setStatus(MailStatus.FAILED);
			task.setErrorMessage(e.getClass().getName() + ": " + e.getMessage());
		}

		task.setLastTry(Instant.now());
		task.setAttempts(task.getAttempts() + 1);
	}
}
