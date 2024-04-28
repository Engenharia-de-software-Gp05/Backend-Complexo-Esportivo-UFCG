--liquibase formatted sql
--changeset samuelcluna:create-court-image-url-table context:new splitStatements:true endDelimiter:;

CREATE TABLE court_image_url
(
    court_id    BIGINT NOT NULL,
    images_urls VARCHAR(255)
);

ALTER TABLE court_image_url
    ADD CONSTRAINT fk_court_image_url_on_court FOREIGN KEY (court_id) REFERENCES court (id) ON DELETE CASCADE;