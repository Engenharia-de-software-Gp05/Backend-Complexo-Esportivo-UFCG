CREATE TABLE users
(
    id         BIGSERIAL NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP NOT NULL DEFAULT NOW(),
    name TEXT NOT NULL,
    email TEXT,
    phone_number TEXT NOT NULL,
    student_id TEXT,
    roles TEXT[] NOT NULL,
    password TEXT NOT NULL,
    CONSTRAINT users_id_pkey PRIMARY KEY (id),
    CONSTRAINT users_email_ukey UNIQUE (email),
    CONSTRAINT users_student_id_ukey UNIQUE (student_id)
);

CREATE TRIGGER users_updated_at_column
    BEFORE UPDATE
    ON users
    FOR EACH ROW
    EXECUTE PROCEDURE update_at_column();