--liquibase formatted sql
--changeset samuelcluna:create-court-table context:new splitStatements:true endDelimiter:;

CREATE TABLE court
(
    id                   BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    "created-at"         TIMESTAMP WITHOUT TIME ZONE,
    "updated-at"         TIMESTAMP WITHOUT TIME ZONE,
    name                 VARCHAR(255)                            NOT NULL,
    status               VARCHAR(255)                            NOT NULL,
    reservation_duration BIGINT                                  NOT NULL,
    CONSTRAINT pk_court PRIMARY KEY (id)
);