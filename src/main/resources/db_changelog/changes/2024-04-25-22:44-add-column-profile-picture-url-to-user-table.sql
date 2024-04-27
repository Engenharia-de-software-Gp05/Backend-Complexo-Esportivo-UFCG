--liquibase formatted sql
--changeset samuelcluna:add-profile-picture-url-column-to-user-table context:seed splitStatements:true endDelimiter:;

ALTER TABLE sace_user
    ADD profile_picture_url VARCHAR(255);