package org.example.profitsoftunit5.testcontainers;

import org.testcontainers.elasticsearch.ElasticsearchContainer;

public class ElasticSearchContainer extends ElasticsearchContainer {
	private static final String DOCKER_ELASTIC = "docker.elastic.co/elasticsearch/elasticsearch:8.6.1";

	private static final String CLUSTER_NAME = "sample-cluster";

	private static final String ELASTIC_SEARCH = "elasticsearch";

	private static final String DISCOVERY_TYPE = "discovery.type";

	private static final String DISCOVERY_TYPE_SINGLE_NODE = "single-node"
			;
	private static final String XPACK_SECURITY_ENABLED = "xpack.security.enabled";

	public ElasticSearchContainer() {
		super(DOCKER_ELASTIC);
		this.addFixedExposedPort(9200, 9200);
		this.addEnv(CLUSTER_NAME, ELASTIC_SEARCH);
		this.addEnv(DISCOVERY_TYPE, DISCOVERY_TYPE_SINGLE_NODE);
		this.addEnv(XPACK_SECURITY_ENABLED, Boolean.FALSE.toString());
	}
}
