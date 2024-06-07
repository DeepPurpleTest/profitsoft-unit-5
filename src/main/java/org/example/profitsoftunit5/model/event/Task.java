package org.example.profitsoftunit5.model.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Task {

	String projectName;

	String taskName;

	String taskDescription;
}
