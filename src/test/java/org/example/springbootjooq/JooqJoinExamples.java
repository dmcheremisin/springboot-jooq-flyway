package org.example.springbootjooq;

import org.example.springbootjooq.dto.PersonWithTasksDto;
import org.example.springbootjooq.dto.TaskDto;
import org.example.springbootjooq.generated.tables.records.PersonRecord;
import org.example.springbootjooq.generated.tables.records.TaskRecord;
import org.jooq.Field;
import org.jooq.Record;
import org.jooq.Record2;
import org.jooq.Result;
import org.jooq.impl.DSL;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.example.springbootjooq.controller.PersonsController.getPersonTaskFields;
import static org.example.springbootjooq.generated.Tables.PERSON;
import static org.example.springbootjooq.generated.Tables.TASK;

public class JooqJoinExamples extends BaseTest {

    @Test
    void selectPeopleAndCountTasks() {
        Result<Record2<String, Integer>> result = dslContext.select(PERSON.NAME, DSL.count(TASK.ID))
                .from(PERSON)
                .innerJoin(TASK).on(TASK.PERSON_ID.eq(PERSON.ID))
                .groupBy(PERSON.NAME)
                .orderBy(DSL.count(TASK.ID).asc())
                .fetch();
        System.out.println(result);
    }

    @Test
    void selectPeopleWithTasks() {
        List<Field<?>> fields = getPersonTaskFields();

        Result<Record> result = dslContext.select(fields)
                .from(PERSON)
                .innerJoin(TASK).on(TASK.PERSON_ID.eq(PERSON.ID))
                .orderBy(PERSON.ID.asc())
                .fetch();
        System.out.println(result);
    }

    @Test
    void selectPeopleWithTasksDto() {
        List<Field<?>> fields = getPersonTaskFields();

        Map<Integer, PersonWithTasksDto> persons = new HashMap<>();

        dslContext.select(fields)
                .from(PERSON)
                .innerJoin(TASK).on(TASK.PERSON_ID.eq(PERSON.ID))
                .orderBy(PERSON.ID.asc())
                .fetch(record -> {
                    PersonRecord personRecord = record.into(PERSON);
                    Integer personId = personRecord.get(PERSON.ID);
                    PersonWithTasksDto personWithTasksDto =
                            persons.computeIfAbsent(personId, id ->
                                    new PersonWithTasksDto(id, personRecord.get(PERSON.NAME), new ArrayList<>()));
                    TaskRecord taskRecord = record.into(TASK);
                    TaskDto taskDto =
                            new TaskDto(taskRecord.get(TASK.ID), personId, taskRecord.get(TASK.SUBJECT), taskRecord.get(TASK.STATUS));
                    personWithTasksDto.addTask(taskDto);
                    return personWithTasksDto;
                });
        assertThat(persons.values().size()).isEqualTo(3);
        System.out.println(persons.values());
    }
}
