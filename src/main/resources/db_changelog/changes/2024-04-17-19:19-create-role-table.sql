--liquibase formatted sql
--changeset samuelcluna:create-role-table context:new splitStatements:true endDelimiter:;

CREATE TABLE role
(
    user_id BIGINT       NOT NULL,
    roles   VARCHAR(255) NOT NULL
);

ALTER TABLE role
    ADD CONSTRAINT fk_role_on_sace_user FOREIGN KEY (user_id) REFERENCES sace_user (id);