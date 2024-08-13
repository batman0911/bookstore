-- liquibase formatted sql
-- changeset author:app id:createTable-
-- see https://docs.liquibase.com/concepts/changelogs/sql-format.html

create table payment_code (
    id bigint not null auto_increment,
    text varchar(1024) not null,
    primary key (id)
);
