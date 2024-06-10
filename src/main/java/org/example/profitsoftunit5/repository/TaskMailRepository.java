package org.example.profitsoftunit5.repository;

import org.example.profitsoftunit5.model.model.TaskMail;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskMailRepository extends ElasticsearchRepository<TaskMail, String> {

	@Query("{\"bool\": {\"should\": [{\"term\": {\"status.keyword\": \"FAILED\"}}, {\"term\": {\"status.keyword\": \"PENDING\"}}]}}")
	List<TaskMail> findAllNeedToSend();
}
