package org.example.profitsoftunit5.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.profitsoftunit5.model.event.TaskCreateEvent;
import org.example.profitsoftunit5.model.model.TaskMail;
import org.example.profitsoftunit5.repository.TaskMailRepository;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class TaskMailService {

	private final TaskMailRepository taskMailRepository;

	public void saveTaskMail(TaskCreateEvent taskCreateEvent) {
		TaskMail taskMail = mapToEntity(taskCreateEvent);

		TaskMail savedTaskMail = taskMailRepository.save(taskMail);
		log.info("Save task mail");
		log.info("Task: {}", savedTaskMail);
	}

	private TaskMail mapToEntity(TaskCreateEvent taskCreateEvent) {
		return TaskMail.builder()
				.taskName(taskCreateEvent.getTaskName())
				.taskDescription(taskCreateEvent.getTaskDescription())
				.assigneeEmail(taskCreateEvent.getAssigneeEmail())
				.receiverEmail(taskCreateEvent.getReceiverEmail())
				.build();
	}
}
