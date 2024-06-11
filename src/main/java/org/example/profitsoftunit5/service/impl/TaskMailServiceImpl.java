package org.example.profitsoftunit5.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.profitsoftunit5.model.model.TaskMail;
import org.example.profitsoftunit5.repository.TaskMailRepository;
import org.example.profitsoftunit5.service.TaskMailService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class TaskMailServiceImpl implements TaskMailService {

	private final TaskMailRepository taskMailRepository;

	@Override
	public void saveTaskMail(TaskMail taskMail) {
		TaskMail saved = taskMailRepository.save(taskMail);
		log.info("TaskMail: {}", saved);
	}

	@Override
	public List<TaskMail> getTasksNeedToSend() {
		return taskMailRepository.findAllNeedToSend();
	}

	@Override
	public void saveAll(Set<TaskMail> taskMails) {
		taskMailRepository.saveAll(taskMails);
	}
}
