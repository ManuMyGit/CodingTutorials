package org.mjjaen.restapi.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.mjjaen.restapi.config.AsyncConfig;
import org.mjjaen.restapi.exception.DataNotFoundException;
import org.springframework.data.domain.Page;
import org.mjjaen.restapi.model.Task;
import org.mjjaen.restapi.repository.TaskRepository;
import org.mjjaen.restapi.service.TaskService;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;

import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
@Slf4j
@Validated
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;

    @Override
    @Async(AsyncConfig.TASK_EXECUTOR_SERVICE)
    public CompletableFuture<Task> findById(ObjectId id) {
        log.info("Execution findById service method with ObjectId = " + id);
        return taskRepository.findOneById(id).thenApplyAsync(task -> {
            if(task == null)
                throw new DataNotFoundException("Task " + id + " not found");
            return task;
        });
    }

    @Override
    @Async(AsyncConfig.TASK_EXECUTOR_SERVICE)
    public CompletableFuture<Page<Task>> findAll(final String search, final Pageable pageable) {
        log.info("Execution findAll service method");
        return taskRepository.findAllBy(search, pageable);
    }

    @Override
    @Async(AsyncConfig.TASK_EXECUTOR_SERVICE)
    public CompletableFuture<Task> insert(@Valid Task task) {
        log.info("Execution insert service method");
        return CompletableFuture.supplyAsync(() -> {
            task.setVersion(null);
            task.setDateCreated(null);
            task.setLastUpdated(null);
            return taskRepository.insert(task);
        });
    }

    @Override
    @Async(AsyncConfig.TASK_EXECUTOR_SERVICE)
    public CompletableFuture<Task> update(Task task, ObjectId id) {
        log.info("Execution update service method with ObjectId = " + id);
        return taskRepository.findOneById(id)
                .thenApplyAsync(taskFromDatabase -> {
                    if(taskFromDatabase == null)
                        throw new DataNotFoundException("Task " + id + " not found");
                    return taskFromDatabase;
                })
                .thenApplyAsync(taskFromDatabase -> {
                    task.setId(taskFromDatabase.getId());
                    task.setDateCreated(taskFromDatabase.getDateCreated());
                    task.setLastUpdated(taskFromDatabase.getLastUpdated());
                    task.setVersion(taskFromDatabase.getVersion());
                    if(task.getDataOne() == null)
                        task.setDataOne(taskFromDatabase.getDataOne());
                    if(task.getDataTwo() == null)
                        task.setDataTwo(taskFromDatabase.getDataTwo());
                    if(task.getDataThree() == null)
                        task.setDataThree(taskFromDatabase.getDataThree());
                    if(task.getDataFour() == null)
                        task.setDataFour(taskFromDatabase.getDataFour());
                    if(task.getDescription() == null)
                        task.setDescription(taskFromDatabase.getDescription());
                    if(task.getDefinitionOfDone() == null)
                        task.setDefinitionOfDone(taskFromDatabase.getDefinitionOfDone());
                    if(task.getPriority() == null)
                        task.setPriority(taskFromDatabase.getPriority());
                    return taskRepository.save(task);
                });
    }

    @Override
    @Async(AsyncConfig.TASK_EXECUTOR_SERVICE)
    public CompletableFuture<Void> deleteById(ObjectId id) {
        log.info("Execution deleteById service method with ObjectId = " + id);
        return taskRepository.findOneById(id)
                .thenApplyAsync(taskFromDatabase -> {
                    if (taskFromDatabase == null)
                        throw new DataNotFoundException("Task " + id + " not found");
                    return taskFromDatabase;
                })
                .thenApplyAsync(e -> {
                    taskRepository.delete(e);
                    return Void.TYPE.cast(null);
                });
    }
}
