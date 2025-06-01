create table task
(
    id        serial       not null,
    person_id int          not null,
    subject   varchar(100) not null,
    status    varchar(100) not null
);

insert into task(person_id, subject, status)
values (1, 'Complete project', 'Pending'),
       (2, 'Update website content', 'In Progress'),
       (3, 'Organize team meeting', 'Completed'),
       (1, 'Review budget plan', 'Pending'),
       (4, 'Plan marketing campaign', 'Pending'),
       (2, 'Fix bugs in software', 'In Progress'),
       (3, 'Conduct client workshop', 'Completed'),
       (5, 'Draft new policy', 'Pending'),
       (4, 'Design new logo', 'In Progress'),
       (1, 'Prepare presentation', 'Completed');