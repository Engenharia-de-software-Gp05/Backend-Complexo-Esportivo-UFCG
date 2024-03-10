CREATE TABLE reservations
(
    id         BIGSERIAL NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP NOT NULL DEFAULT NOW(),
    user_id    BIGINT NOT NULL,
    court_id    BIGINT NOT NULL,
    start_date_time TIMESTAMP NOT NULL,
    end_date_time   TIMESTAMP NOT NULL,
    CONSTRAINT reservations_id_pkey PRIMARY KEY (id),
    CONSTRAINT reservations_user_id_fkey FOREIGN KEY (user_id) REFERENCES users (id),
    CONSTRAINT reservations_room_id_fkey FOREIGN KEY (room_id) REFERENCES courts (id)
);
