-- liquibase formatted sql

-- changeset amalashenko:1
CREATE table notifications (
    id bigint not null primary key,
    id_chat varchar(500) not null,
    text_note varchar(1000) not null,
    date_note timestamp
);