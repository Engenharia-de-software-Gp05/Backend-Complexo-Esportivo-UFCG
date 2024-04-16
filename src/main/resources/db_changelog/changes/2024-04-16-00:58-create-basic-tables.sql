--liquibase formatted sql
--changeset samuelcluna:create-basic-tables context:new splitStatements:true endDelimiter:;


-- SaceUser table

CREATE TABLE sace_user
(
    id           BIGSERIAL NOT NULL,
    created_at   TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at   TIMESTAMP NOT NULL DEFAULT NOW(),
    name         TEXT      NOT NULL,
    email        TEXT,
    phone_number TEXT      NOT NULL,
    student_id   TEXT,
    password     TEXT      NOT NULL,
    CONSTRAINT sace_user_id_pkey PRIMARY KEY (id),
    CONSTRAINT sace_user_email_ukey UNIQUE (email),
    CONSTRAINT sace_user_student_id_ukey UNIQUE (student_id)
);

-- UserRole table

CREATE TABLE user_role
(
    id      BIGSERIAL NOT NULL,
    user_id BIGINT    NOT NULL,
    role    TEXT      NOT NULL,
    CONSTRAINT user_role_id_pkey PRIMARY KEY (id),
    CONSTRAINT user_role_user_id_fk FOREIGN KEY (user_id) REFERENCES sace_user (id) ON DELETE CASCADE
);

-- Create SignUpConfirmationCode table

CREATE TABLE sign_up_confirmation_code
(
    id                BIGSERIAL NOT NULL,
    created_at        TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at        TIMESTAMP NOT NULL DEFAULT NOW(),
    sace_user_id      BIGINT    NOT NULL,
    confirmation_code TEXT      NOT NULL,
    expires_at        TIMESTAMP NOT NULL,
    CONSTRAINT sign_up_confirmation_code_id_pkey PRIMARY KEY (id),
    CONSTRAINT sign_up_confirmation_code_user_id_fk FOREIGN KEY (sace_user_id) REFERENCES sace_user (id) ON DELETE CASCADE
);


-- Create Court table

CREATE TABLE court
(
    id                   BIGSERIAL NOT NULL,
    created_at           TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at           TIMESTAMP NOT NULL DEFAULT NOW(),
    name                 TEXT      NOT NULL,
    status               TEXT      NOT NULL,
    reservation_duration BIGINT    NOT NULL,
    CONSTRAINT court_id_pkey PRIMARY KEY (id)
);

-- Court Image URL Table

CREATE TABLE court_image_url
(
    court_id  BIGINT NOT NULL,
    image_url TEXT   NOT NULL,
    FOREIGN KEY (court_id) REFERENCES court (id) ON DELETE CASCADE
);

-- Create Reservation table

CREATE TABLE reservation
(
    id              BIGSERIAL NOT NULL,
    created_at      TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at      TIMESTAMP NOT NULL DEFAULT NOW(),
    start_date_time TIMESTAMP NOT NULL,
    end_date_time   TIMESTAMP NOT NULL,
    court_id        BIGINT    NOT NULL,
    sace_user_id    BIGINT    NOT NULL,
    CONSTRAINT reservation_id_pkey PRIMARY KEY (id),
    CONSTRAINT reservation_court_id_fk FOREIGN KEY (court_id) REFERENCES court (id) ON DELETE CASCADE,
    CONSTRAINT reservation_sace_user_id_fk FOREIGN KEY (sace_user_id) REFERENCES sace_user (id) ON DELETE CASCADE
);

-- Create UnavailableReservation table

CREATE TABLE unavailable_reservation
(
    id              BIGSERIAL NOT NULL,
    created_at      TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at      TIMESTAMP NOT NULL DEFAULT NOW(),
    start_date_time TIMESTAMP NOT NULL,
    end_date_time   TIMESTAMP NOT NULL,
    court_id        BIGINT    NOT NULL,
    CONSTRAINT unavailable_reservation_id_pkey PRIMARY KEY (id),
    CONSTRAINT unavailable_reservation_court_id_fk FOREIGN KEY (court_id) REFERENCES court (id) ON DELETE CASCADE
);
