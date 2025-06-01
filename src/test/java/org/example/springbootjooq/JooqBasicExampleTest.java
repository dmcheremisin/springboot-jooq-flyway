package org.example.springbootjooq;

import org.jooq.DSLContext;
import org.jooq.Record1;
import org.jooq.Result;
import org.jooq.impl.DSL;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;


public class JooqBasicExampleTest extends BaseTest {

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

    @Test
    void testInsert() {
        int executed = dslContext.insertInto(DSL.table("PERSON"))
                .set(DSL.field("id"), 10)
                .set(DSL.field("name"), "PETER")
                .execute();
        assertThat(executed).isEqualTo(1);

        Result<Record1<Object>> fetch = dslContext.select(DSL.field("name"))
                .from(DSL.table("PERSON"))
                .where(DSL.field("name").eq("PETER"))
                .fetch();
        System.out.println(fetch);
    }
}
