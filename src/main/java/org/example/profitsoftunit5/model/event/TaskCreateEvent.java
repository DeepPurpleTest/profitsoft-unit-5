package org.example.profitsoftunit5.model.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

/**
 * Class represents task create event with notification type
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TaskCreateEvent {

	private Task task;

	private Receiver receiver;

	private Instant createdAt;

	private NotificationType notificationType;
}
