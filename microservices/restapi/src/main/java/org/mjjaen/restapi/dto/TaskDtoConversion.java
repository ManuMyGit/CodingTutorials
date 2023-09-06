package org.mjjaen.restapi.dto;

import lombok.RequiredArgsConstructor;
import org.mjjaen.restapi.model.Task;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TaskDtoConversion {
    private final ModelMapper modelMapper;

    public TaskDto convertToDto(Task task) {
        TaskDto taskDto = task != null ? modelMapper.map(task, TaskDto.class) : null;
        return taskDto;
    }

    public Task convertToEntity(TaskDto taskDto) {
        Task task = taskDto != null ? modelMapper.map(taskDto, Task.class) : null;
        return task;
    }
}
