package org.example.springbootjooq.controller;

import org.example.springbootjooq.dto.TaskDto;
import org.example.springbootjooq.generated.tables.records.TaskRecord;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.example.springbootjooq.generated.Tables.TASK;

@RestController
@RequestMapping("/tasks")
public class TasksController {

    @Autowired
    DSLContext dslContext;

    @GetMapping()
    public List<TaskDto> tasks() {
        List<TaskDto> list = dslContext.select(TASK.fields())
                .from(TASK)
                .orderBy(TASK.ID.asc())
                .fetch(record -> {
                    TaskRecord taskRecord = record.into(TASK);
                    return new TaskDto(taskRecord.getId(), taskRecord.getPersonId(), taskRecord.getSubject(), taskRecord.getStatus());
                });
        return list;
    }
}
