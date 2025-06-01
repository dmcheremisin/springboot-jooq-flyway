package org.example.springbootjooq.controller;

import org.example.springbootjooq.dto.PersonDto;
import org.example.springbootjooq.dto.PersonWithTasksDto;
import org.example.springbootjooq.dto.TaskDto;
import org.example.springbootjooq.generated.tables.records.PersonRecord;
import org.example.springbootjooq.generated.tables.records.TaskRecord;
import org.jooq.DSLContext;
import org.jooq.Field;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

import static org.example.springbootjooq.generated.Tables.PERSON;
import static org.example.springbootjooq.generated.Tables.TASK;

@RestController
@RequestMapping("/persons")
public class PersonsController {

    public static List<Field<?>> getPersonTaskFields() {
        List<Field<?>> fields = new ArrayList<>();
        fields.addAll(Arrays.asList(PERSON.fields()));
        fields.addAll(Arrays.asList(TASK.fields()));
        return fields;
    }

    @Autowired
    DSLContext dslContext;

    @GetMapping
    public List<PersonDto> persons() {
        return dslContext.select(PERSON.fields()).from(PERSON).fetch()
                .map(record -> new PersonDto(record.get(PERSON.ID), record.get(PERSON.NAME)));
    }

    @GetMapping("/tasks")
    public Collection<PersonWithTasksDto> tasks() {
        Map<Integer, PersonWithTasksDto> persons = new HashMap<>();
        dslContext.select(getPersonTaskFields())
                .from(PERSON)
                .innerJoin(TASK).on(TASK.PERSON_ID.eq(PERSON.ID))
                .orderBy(PERSON.ID.asc())
                .fetch(record -> {
                    PersonRecord personRecord = record.into(PERSON);
                    Integer personId = personRecord.get(PERSON.ID);
                    PersonWithTasksDto personWithTasksDto =
                            persons.computeIfAbsent(personId, id ->
                                    new PersonWithTasksDto(id, personRecord.getName(), new ArrayList<>()));
                    TaskRecord taskRecord = record.into(TASK);
                    TaskDto taskDto =
                            new TaskDto(taskRecord.getId(), personId, taskRecord.getSubject(), taskRecord.getStatus());
                    personWithTasksDto.addTask(taskDto);
                    return personWithTasksDto;
                });
        return persons.values();
    }
}
