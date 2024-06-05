package org.example.profitsoftunit5.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.profitsoftunit5.model.event.TaskCreateEvent;
import org.example.profitsoftunit5.model.model.TaskMail;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class TaskEventConsumer {

	private final MailService mailService;
	private final TaskMailService taskMailService;

	@KafkaListener(
			topics = "task-create",
			containerFactory = "taskKafkaListenerContainerFactory")
	public void listenTaskCreate(TaskCreateEvent event) {
		log.info("Received event of task create: " + event);

		taskMailService.saveTaskMail(taskMailService.mapToEntity(event));
	}
}
