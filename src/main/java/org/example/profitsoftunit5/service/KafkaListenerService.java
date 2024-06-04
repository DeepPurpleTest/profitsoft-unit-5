package org.example.profitsoftunit5.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaListenerService {

	private final MailService mailService;

	@KafkaListener(topics = "mail", groupId = "profitsoft-unit-2")
	public void listenGroupProfitUnit2(String message) {
		log.info("Received Message in profitsoft-unit-2: " + message);
		mailService.sendMessage(message);
	}
}
