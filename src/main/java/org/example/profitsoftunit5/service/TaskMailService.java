package org.example.profitsoftunit5.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.profitsoftunit5.model.event.TaskCreateEvent;
import org.example.profitsoftunit5.model.model.MailStatus;
import org.example.profitsoftunit5.model.model.TaskMail;
import org.example.profitsoftunit5.repository.TaskMailRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class TaskMailService {

	private final TaskMailRepository taskMailRepository;

	public void saveTaskMail(TaskMail taskMail) {
		TaskMail saved = taskMailRepository.save(taskMail);
		log.info("TaskMail: {}", saved);
	}

	public List<TaskMail> getTasksNeedToSend() {
		return taskMailRepository.findAllNeedToSend();
	}

	public void saveAll(Set<TaskMail> taskMails) {
		taskMailRepository.saveAll(taskMails);
	}
}
