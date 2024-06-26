package org.example.profitsoftunit5.model.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Class represents receiver data
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Receiver {

	private String name;

	private String email;
}
