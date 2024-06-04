package org.example.profitsoftunit5.service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaListenerService {
	@KafkaListener(topics = "mail", groupId = "profitsoft-unit-2")
	public void listenGroupProfitUnit2(String message) {
		System.out.println("Received Message in profitsoft-unit-2: " + message);
	}
}
