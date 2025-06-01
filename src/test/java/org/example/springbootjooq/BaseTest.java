package org.example.springbootjooq;

import org.jooq.DSLContext;
import org.jooq.impl.DSL;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class BaseTest {

    @Autowired
    protected DSLContext dslContext;

    @Test
    void simpleQuery() {

        List<Map<String, Object>> map = dslContext.fetch("select * from PERSON")
                .map(record -> Map.of(
                        "id", record.get("id"),
                        "name", record.get("name")
                ));
        assertThat(map).contains(Map.of("id", 1, "name", "Axel"));

        dslContext.select(DSL.field("id"), DSL.field("name"))
                .from(DSL.table("PERSON"))
                .orderBy(DSL.field("name").desc())
                .fetch();
    }

}
