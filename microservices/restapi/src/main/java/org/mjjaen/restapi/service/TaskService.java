package org.mjjaen.restapi.service;

import org.bson.types.ObjectId;
import org.mjjaen.restapi.model.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.validation.Valid;
import java.util.concurrent.CompletableFuture;

public interface TaskService {
    CompletableFuture<Task> findById(ObjectId id);
    CompletableFuture<Page<Task>> findAll(final String search, final Pageable pageable);
    CompletableFuture<Task> insert(@Valid Task task);
    CompletableFuture<Task> update(Task task, ObjectId id);
    CompletableFuture<Void> deleteById(ObjectId id);
}
