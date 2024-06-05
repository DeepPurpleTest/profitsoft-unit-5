package org.example.profitsoftunit5.model.model;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.Document;

@Data
@Document(indexName = "task-mail-index")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TaskMail {

	@Id
	private String id;

	private String taskName;

	private String taskDescription;

	private String assigneeEmail;

	private String receiverEmail;
}
