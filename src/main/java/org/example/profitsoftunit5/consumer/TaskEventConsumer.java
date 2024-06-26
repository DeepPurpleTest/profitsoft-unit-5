package org.example.profitsoftunit5.consumer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.profitsoftunit5.model.event.TaskCreateEvent;
import org.example.profitsoftunit5.model.model.MailStatus;
import org.example.profitsoftunit5.model.model.TaskMail;
import org.example.profitsoftunit5.service.TaskMailService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

/**
 * Consumer service for handling task events related to email notifications.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class TaskEventConsumer {

	private final TaskMailService taskMailService;

	/**
	 * Listens for task creation events
	 */
	@KafkaListener(
			topics = "task-mails",
			containerFactory = "taskCreateKafkaListenerContainerFactory")
	public void listenAssigneeMails(TaskCreateEvent event) {
		log.info("Received task mail event: " + event);

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
