package org.example.profitsoftunit5.repository;

import org.example.profitsoftunit5.model.model.TaskMail;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskMailRepository extends ElasticsearchRepository<TaskMail, String> {
}
