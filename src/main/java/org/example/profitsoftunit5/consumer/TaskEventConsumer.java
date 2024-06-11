package org.example.profitsoftunit5.consumer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.profitsoftunit5.model.event.TaskCreateEvent;
import org.example.profitsoftunit5.model.model.MailStatus;
import org.example.profitsoftunit5.model.model.TaskMail;
import org.example.profitsoftunit5.service.impl.TaskMailServiceImpl;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class TaskEventConsumer {

	private final TaskMailServiceImpl taskMailService;

	@KafkaListener(
			topics = "assignee-mails",
			containerFactory = "taskCreateKafkaListenerContainerFactory")
	public void listenAssigneeMails(TaskCreateEvent event) {
		log.info("Received assignee mail event: " + event);

		taskMailService.saveTaskMail(createMail(event));
	}

	@KafkaListener(
			topics = "reporter-mails",
			containerFactory = "taskCreateKafkaListenerContainerFactory")
	public void listenReporterMails(TaskCreateEvent event) {
		log.info("Received reporter mail event: " + event);

		taskMailService.saveTaskMail(createMail(event));
	}

	private TaskMail createMail(TaskCreateEvent event) {
		return TaskMail.builder()
				.task(event.getTask())
				.receiver(event.getReceiver())
				.status(MailStatus.PENDING)
				.createdAt(event.getCreatedAt())
				.notificationType(event.getNotificationType())
				.build();
	}
}