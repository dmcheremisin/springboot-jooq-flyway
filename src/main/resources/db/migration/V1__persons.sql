CREATE TABLE person
(
    id   serial not null,
    name varchar(100) not null
);

INSERT INTO person (name)
values ('Axel');
INSERT INTO person ( name)
values ('Mr. Foo');
INSERT INTO person (name)
values ('Ms. Bar');