package org.example.springbootjooq.dto;

import java.util.List;

public record PersonWithTasksDto(Integer id, String name, List<TaskDto> tasks) {

    public void addTask(TaskDto task) {
        this.tasks.add(task);
    }
}
