package org.mjjaen.restapi.repository;

import org.mjjaen.restapi.model.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.concurrent.CompletableFuture;

public interface TaskRepositoryCustom {
    CompletableFuture<Page<Task>> findAllBy(String query, Pageable pageable);
}
