package org.example.profitsoftunit5.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.profitsoftunit5.model.event.TaskCreateEvent;
import org.example.profitsoftunit5.model.model.MailStatus;
import org.example.profitsoftunit5.model.model.MailType;
import org.example.profitsoftunit5.model.model.TaskMail;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class TaskEventConsumer {

	private final TaskMailService taskMailService;

	@KafkaListener(
			topics = "assignee-mails",
			containerFactory = "mailKafkaListenerContainerFactory")
	public void listenAssigneeMails(TaskCreateEvent event) {
		log.info("Received assignee mail event: " + event);

		taskMailService.saveTaskMail(createMail(event, MailType.ASSIGNEE_NOTIFICATION));
	}

	@KafkaListener(
			topics = "reporter-mails",
			containerFactory = "mailKafkaListenerContainerFactory")
	public void listenReporterMails(TaskCreateEvent event) {
		log.info("Received reporter mail event: " + event);

		taskMailService.saveTaskMail(createMail(event, MailType.REPORTER_NOTIFICATION));
	}

	private TaskMail createMail(TaskCreateEvent event, MailType type) {
		return TaskMail.builder()
				.task(event.getTask())
				.receiver(event.getReceiver())
				.status(MailStatus.PENDING)
				.createdAt(event.getCreatedAt())
				.type(type)
				.build();
	}
}
