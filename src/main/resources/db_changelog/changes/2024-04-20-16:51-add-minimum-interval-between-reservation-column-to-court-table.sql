--liquibase formatted sql
--changeset samuelcluna:add-minimum-interval-between-reservation-column-to-court-table context:seed splitStatements:true endDelimiter:;

ALTER TABLE court
    ADD minimum_interval_between_reservation BIGINT NOT NULL;