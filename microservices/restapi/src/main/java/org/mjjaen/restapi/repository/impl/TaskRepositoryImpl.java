package org.mjjaen.restapi.repository.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mjjaen.restapi.model.Task;
import org.mjjaen.restapi.repository.TaskRepositoryCustom;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import java.util.List;

import java.util.Arrays;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

@RequiredArgsConstructor
@Slf4j
public class TaskRepositoryImpl implements TaskRepositoryCustom {
    private final MongoTemplate mongoTemplate;
    private final Executor repositoryTaskExecutor;

    @Override
    public CompletableFuture<Page<Task>> findAllBy(String query, Pageable pageable) {
        return CompletableFuture.supplyAsync(() -> {
            log.info("Execution findAll repository method");
            Query myQuery = buildQueryFromString(query);
            long numResults = mongoTemplate.count(myQuery, Task.class);
            List<Task> result = mongoTemplate.find(buildQueryFromString(query).with(pageable), Task.class);
            return new PageImpl<>(result, pageable, numResults);
        }, repositoryTaskExecutor);
    }

    private Query buildQueryFromString(String search) {
        Query query = new Query();
        if(search == null)
            return query;
        Arrays.stream(search.split(":")).forEach(criteria -> {
            if(criteria.contains("!="))
                query.addCriteria(Criteria.where(criteria.substring(0, criteria.indexOf("!="))).ne(criteria.substring(criteria.indexOf("!=") + 2)));
            else if(criteria.contains(">="))
                query.addCriteria(Criteria.where(criteria.substring(0, criteria.indexOf(">="))).gte(criteria.substring(criteria.indexOf(">=") + 2)));
            else if(criteria.contains("<="))
                query.addCriteria(Criteria.where(criteria.substring(0, criteria.indexOf("<="))).lte(criteria.substring(criteria.indexOf("<=") + 2)));
            else if(criteria.contains("="))
                query.addCriteria(Criteria.where(criteria.substring(0, criteria.indexOf("="))).is(criteria.substring(criteria.indexOf("=") + 1)));
            else if(criteria.contains(">"))
                query.addCriteria(Criteria.where(criteria.substring(0, criteria.indexOf(">"))).gt(criteria.substring(criteria.indexOf(">") + 1)));
            else if(criteria.contains("<"))
                query.addCriteria(Criteria.where(criteria.substring(0, criteria.indexOf("<"))).lt(criteria.substring(criteria.indexOf("<") + 1)));
        });
        return query;
    }
}
