package org.example.springbootjooq.controller;

import org.example.springbootjooq.dto.PersonDto;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.example.springbootjooq.generated.Tables.PERSON;

@RestController
public class TestController {

    @Autowired
    DSLContext dslContext;

    @GetMapping("/test")
    public List<PersonDto> test() {
        return dslContext.select(PERSON.fields()).from(PERSON).fetch()
                .map(record -> new PersonDto(record.get(PERSON.ID), record.get(PERSON.NAME)));
    }
}
