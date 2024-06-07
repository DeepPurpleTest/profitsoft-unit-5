package org.example.profitsoftunit5.model.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TaskCreateEvent {

	private Task task;

	private Receiver receiver;

	private LocalDateTime createdAt;
}
