--liquibase formatted sql
--changeset Abu:add_initial_data

insert into AUTHORITIES (ROLE)
values ('USER');