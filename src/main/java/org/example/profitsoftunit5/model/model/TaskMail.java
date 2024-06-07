package org.example.profitsoftunit5.model.model;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.profitsoftunit5.model.event.Receiver;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.time.LocalDateTime;

@Data
@Document(indexName = "task-mail-index")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TaskMail {

	@Id
	private String id;

	private String projectName;

	private String taskName;

	@Field(type = FieldType.Object)
	private Receiver receiver;

	private MailStatus status;

	private MailType type;

	private String errorMessage;

	private int attempts;

	@Field(type = FieldType.Date, format = DateFormat.date_hour_minute_second)
	private LocalDateTime createdAt;

	@Field(type = FieldType.Date, format = DateFormat.date_hour_minute_second)
	private LocalDateTime lastTry;
}
