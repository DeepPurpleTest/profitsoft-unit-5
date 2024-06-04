package org.example.profitsoftunit5.service;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class MailService {

	private final JavaMailSender mailSender;
	private final SimpleMailMessage template;

	public void sendMessage(String messageText) {
		String email = "maksym.viskovatov@nure.ua";

		SimpleMailMessage message = new SimpleMailMessage();
		String text = String.format(
				Objects.requireNonNull(template.getText()),
				messageText);

		message.setTo(email);
		message.setText(text);

		mailSender.send(message);
	}
}
