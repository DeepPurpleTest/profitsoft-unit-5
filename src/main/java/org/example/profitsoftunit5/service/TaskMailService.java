package org.example.profitsoftunit5.service;

import org.example.profitsoftunit5.model.model.TaskMail;

import java.util.List;
import java.util.Set;

public interface TaskMailService {

	void saveTaskMail(TaskMail taskMail);

	List<TaskMail> getTasksNeedToSend();

	void saveAll(Set<TaskMail> taskMails);
}
