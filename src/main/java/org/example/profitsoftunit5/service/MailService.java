package org.example.profitsoftunit5.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.profitsoftunit5.model.model.MailStatus;
import org.example.profitsoftunit5.model.model.MailType;
import org.example.profitsoftunit5.model.model.TaskMail;
import org.example.profitsoftunit5.service.templatestrategy.MessageTemplate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class MailService {

	private final JavaMailSender mailSender;
	private final Map<MailType, MessageTemplate> templates;
	private final TaskMailService taskMailService;

	@Scheduled(fixedDelayString = "${mails.delay}")
	public void sendMessages() {
		log.info("Send messages -> start!");
		List<TaskMail> createdTasks = taskMailService.getTasksNeedToSend();
		Map<TaskMail, SimpleMailMessage> messages = new HashMap<>();

		createdTasks.forEach(task -> messages.put(task, createMessage(task)));

		messages.forEach(this::sendMessagesForTask);

		taskMailService.saveAll(messages.keySet());
	}

	private SimpleMailMessage createMessage(TaskMail taskMail) {
		MessageTemplate template = templates.get(taskMail.getType());
		if (template == null) {
			log.error("Template is not found for type: {}",  taskMail.getType());
			return new SimpleMailMessage();
		}

		return templates.get(taskMail.getType()).createMessage(taskMail);
	}

	private void sendMessagesForTask(TaskMail task, SimpleMailMessage message) {
		try {
			mailSender.send(message);
			task.setStatus(MailStatus.SENT);
		} catch (RuntimeException e) {
			task.setStatus(MailStatus.FAILED);
			task.setErrorMessage(e.getClass().getName() + ": " + e.getMessage());
		}

		task.setLastTry(LocalDateTime.now());
		task.setAttempts(task.getAttempts() + 1);
	}
}
