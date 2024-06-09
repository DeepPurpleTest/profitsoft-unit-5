package org.example.profitsoftunit5.service;

import org.example.profitsoftunit5.model.event.Receiver;
import org.example.profitsoftunit5.model.event.Task;
import org.example.profitsoftunit5.model.model.MailStatus;
import org.example.profitsoftunit5.model.model.MailType;
import org.example.profitsoftunit5.model.model.TaskMail;
import org.example.profitsoftunit5.repository.TaskMailRepository;
import org.example.profitsoftunit5.testcontainers.BaseContainerTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class MailServiceTest extends BaseContainerTest {

	@MockBean
	private JavaMailSender javaMailSender;

	@MockBean
	private TaskEventConsumer eventConsumer;

	@Autowired
	private TaskMailRepository taskMailRepository;

	@Autowired
	private MailService mailService;

	@BeforeEach
	void setUp() {
		taskMailRepository.deleteAll();

		TaskMail taskMail = TaskMail.builder()
				.id("1")
				.task(Task.builder()
						.projectName("profitsoft-unit-5")
						.taskDescription("Test containers")
						.taskName("DEV-1 Test containers")
						.build())
				.receiver(Receiver.builder()
						.email("moksem@gmail.com")
						.name("Moksem")
						.build())
				.status(MailStatus.PENDING)
				.type(MailType.ASSIGNEE_NOTIFICATION)
				.errorMessage(null)
				.attempts(0)
				.createdAt(LocalDateTime.now())
				.lastTry(null)
				.build();

		taskMailRepository.save(taskMail);
	}

	@Test
	void sendMessages_shouldChangeStatusToSent() {
		doNothing().when(javaMailSender).send(any(SimpleMailMessage.class));
		mailService.sendMessages();

		Optional<TaskMail> byId = taskMailRepository.findById("1");
		assertTrue(byId.isPresent());

		TaskMail taskMail = byId.get();

		assertEquals("1", taskMail.getId());
		assertEquals(MailStatus.SENT, taskMail.getStatus());
		assertEquals(1, taskMail.getAttempts());
		assertNotNull(taskMail.getLastTry());
		assertNull(taskMail.getErrorMessage());
	}

	@Test
	void sendMessages_shouldChangeStatusToFailed() {
		doThrow(RuntimeException.class).when(javaMailSender).send(any(SimpleMailMessage.class));
		mailService.sendMessages();

		Optional<TaskMail> byId = taskMailRepository.findById("1");
		assertTrue(byId.isPresent());

		TaskMail taskMail = byId.get();

		assertEquals("1", taskMail.getId());
		assertEquals(MailStatus.FAILED, taskMail.getStatus());
		assertEquals(1, taskMail.getAttempts());
		assertNotNull(taskMail.getLastTry());
		assertNotNull(taskMail.getErrorMessage());
	}
}