package org.example.profitsoftunit5.config;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.example.profitsoftunit5.model.event.TaskCreateEvent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@EnableKafka
@Configuration
public class KafkaConsumerConfig {

	@Value(value = "${spring.kafka.bootstrap-servers}")
	private String bootstrapAddress;

	@Bean
	public ConsumerFactory<String, TaskCreateEvent> taskCreateConsumerFactory() {
		Map<String, Object> props = new HashMap<>();

		props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
		props.put(ConsumerConfig.GROUP_ID_CONFIG, "profitsoft-unit-5");
		props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);

		return new DefaultKafkaConsumerFactory<>(
				props,
				new StringDeserializer(),
				new JsonDeserializer<>(TaskCreateEvent.class));
	}

	@Bean
	public ConcurrentKafkaListenerContainerFactory<String, TaskCreateEvent> taskCreateKafkaListenerContainerFactory() {

		ConcurrentKafkaListenerContainerFactory<String, TaskCreateEvent> factory = new ConcurrentKafkaListenerContainerFactory<>();
		factory.setConsumerFactory(taskCreateConsumerFactory());
		return factory;
	}
}
