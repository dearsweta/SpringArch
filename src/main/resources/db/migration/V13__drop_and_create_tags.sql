SET FOREIGN_KEY_CHECKS = 0;

DROP TABLE IF EXISTS tags;

create table tags
(
    id   int auto_increment
                        primary key,
    name varchar(255) not null
);

SET FOREIGN_KEY_CHECKS = 1;
