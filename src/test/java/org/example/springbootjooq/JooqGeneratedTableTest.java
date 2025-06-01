package org.example.springbootjooq;

import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Record1;
import org.jooq.Result;
import org.jooq.impl.DSL;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.example.springbootjooq.generated.Tables.PERSON;

public class JooqGeneratedTableTest extends BaseTest {

    @Test
    public void selectRecords() {
        Result<Record> fetch = dslContext.select(PERSON.fields())
                .from(PERSON)
                .fetch();
        System.out.println(fetch);

        List<String> names = dslContext.select(PERSON.NAME)
                .from(PERSON)
                .orderBy(PERSON.NAME.desc())
                .fetch(record -> record.get(PERSON.NAME));
        System.out.println(names);
    }

    @Test
    public void testInsert() {
        int executed = dslContext.insertInto(PERSON)
                .set(PERSON.ID, 10)
                .set(PERSON.NAME, "Peter")
                .execute();
        assertThat(executed).isEqualTo(1);

        Record1<Integer> peter = dslContext.select(PERSON.ID)
                .from(PERSON)
                .where(PERSON.NAME.eq("Peter"))
                .fetchOne();
        assertThat(peter.get(PERSON.ID)).isEqualTo(10);

    }
}
