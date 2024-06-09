package org.example.profitsoftunit5.testcontainers;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.testcontainers.elasticsearch.ElasticsearchContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
public class BaseContainerTest {
	@Container
	protected static ElasticsearchContainer elasticsearchContainer = new ElasticSearchContainer();

	@BeforeAll
	static void setUp() {
		elasticsearchContainer.start();
	}

	@AfterAll
	static void destroy() {
		elasticsearchContainer.stop();
	}
}
