package org.mjjaen.restapi.repository;

import org.bson.types.ObjectId;
import org.mjjaen.restapi.config.AsyncConfig;
import org.mjjaen.restapi.model.Task;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Repository
public interface TaskRepository extends MongoRepository<Task, ObjectId>, TaskRepositoryCustom {
    @Async(AsyncConfig.TASK_EXECUTOR_REPOSITORY)
    CompletableFuture<List<Task>> findAllBy(final Pageable pageable);

    @Async(AsyncConfig.TASK_EXECUTOR_REPOSITORY)
    CompletableFuture<Task> findOneById(final ObjectId id);
}
