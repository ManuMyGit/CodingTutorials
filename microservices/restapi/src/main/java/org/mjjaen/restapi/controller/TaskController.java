package org.mjjaen.restapi.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.mjjaen.restapi.config.AsyncConfig;
import org.mjjaen.restapi.dto.TaskDto;
import org.mjjaen.restapi.dto.TaskDtoConversion;
import org.mjjaen.restapi.service.TaskService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

@RestController
@RequestMapping("/v1/task")
@RequiredArgsConstructor
@Slf4j
public class TaskController {
    private final TaskService taskService;
    private final TaskDtoConversion taskDtoConversion;

    private final Executor controllerTaskExecutor;

    /*
     * open http://localhost:8080/v3/api-docs
     * open http://localhost:8080/swagger-ui.html
     */
    @Operation(summary = "Get all tasks")
    //@Async(AsyncConfig.TASK_EXECUTOR_CONTROLLER) Disabled to show another way to call async code
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public CompletableFuture<ResponseEntity<TaskDto>> getTask(@RequestHeader HttpHeaders headers, HttpServletResponse response, @PathVariable ObjectId id) {
        CompletableFuture.runAsync(() -> {
            response.setHeader("Custom-Header", "foo");
            log.info("Execution getTask controller method with ObjectId = " + id);
        }, controllerTaskExecutor);
        return taskService
                .findById(id)
                .thenApplyAsync(taskDtoConversion::convertToDto)
                .thenApplyAsync(ResponseEntity::ok);
    }

    /*
     * To use pagination: ?page=x&size=y&sort=z (e.g. ?page=0&size=5&sort=id,desc&sort=description,desc)
     */
    @Operation(summary = "Get the details of a task")
    @Async(AsyncConfig.TASK_EXECUTOR_CONTROLLER)
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public CompletableFuture<ResponseEntity<Page<TaskDto>>> getAllTasks(@RequestHeader HttpHeaders headers, HttpServletResponse response, @RequestParam(required = false) String search, Pageable pageable) {
        response.setHeader("Custom-Header", "foo");
        log.info("Execution getAllTasks controller method");
        return taskService
                .findAll(search, pageable)
                .thenApplyAsync(e -> e.map(q -> taskDtoConversion.convertToDto(q)))
                .thenApplyAsync(ResponseEntity::ok);
    }

    @Operation(summary = "Create a task")
    @Async(AsyncConfig.TASK_EXECUTOR_CONTROLLER)
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public CompletableFuture<ResponseEntity<TaskDto>> createTask(@RequestHeader HttpHeaders headers, HttpServletResponse response, @Valid @RequestBody TaskDto taskDto) {
        response.setHeader("Custom-Header", "foo");
        log.info("Execution createTask controller method");
        return taskService
                .insert(taskDtoConversion.convertToEntity(taskDto))
                .thenApplyAsync(taskDtoConversion::convertToDto)
                .thenApply(internalTaskDto -> ResponseEntity.status(HttpStatus.CREATED).body(internalTaskDto));
    }

    @Operation(summary = "Update a task")
    @Async(AsyncConfig.TASK_EXECUTOR_CONTROLLER)
    @PatchMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public CompletableFuture<ResponseEntity<TaskDto>> updateTask(@RequestHeader HttpHeaders headers, HttpServletResponse response, @PathVariable ObjectId id, @RequestBody TaskDto taskDto) {
        response.setHeader("Custom-Header", "foo");
        log.info("Execution updateTask controller method");
        return taskService
                .update(taskDtoConversion.convertToEntity(taskDto), id)
                .thenApplyAsync(taskDtoConversion::convertToDto)
                .thenApplyAsync(internalTaskDto -> ResponseEntity.status(HttpStatus.ACCEPTED).body(internalTaskDto));
    }

    @Operation(summary = "Delete a task")
    @Async(AsyncConfig.TASK_EXECUTOR_CONTROLLER)
    @DeleteMapping(value = "/{id}")
    public CompletableFuture<ResponseEntity<Void>> deleteTask(@RequestHeader HttpHeaders headers, HttpServletResponse response, @PathVariable ObjectId id) {
        response.setHeader("Custom-Header", "foo");
        log.info("Execution deleteTask controller method");
        return taskService
                .deleteById(id)
                .thenApplyAsync(nothing -> ResponseEntity.status(HttpStatus.NO_CONTENT).body(nothing));
    }
}
