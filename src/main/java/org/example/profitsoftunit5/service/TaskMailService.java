package org.example.profitsoftunit5.service;

import org.example.profitsoftunit5.model.model.TaskMail;

import java.util.List;
import java.util.Set;

/**
 * Service for working with TaskMail entity
 */
public interface TaskMailService {

	void saveTaskMail(TaskMail taskMail);

	List<TaskMail> getTasksNeedToSend();

	void saveAll(Set<TaskMail> taskMails);
}
