CREATE DATABASE feverdb;

\connect feverdb;

CREATE TABLE base_event
(
    id            SERIAL PRIMARY KEY,
    base_event_id VARCHAR(255) NOT NULL,
    sell_mode     VARCHAR(255),
    title         VARCHAR(255)
);

CREATE TABLE event
(
    id               SERIAL PRIMARY KEY,
    event_id         VARCHAR(255) NOT NULL,
    event_start_date TIMESTAMP,
    event_end_date   TIMESTAMP,
    sell_from        TIMESTAMP,
    sell_to          TIMESTAMP,
    sold_out         BOOLEAN,
    base_event_id    BIGINT,
    CONSTRAINT fk_base_event
        FOREIGN KEY (base_event_id)
            REFERENCES base_event (id)
);


CREATE TABLE zone
(
    id       SERIAL PRIMARY KEY,
    zone_id  VARCHAR(255) NOT NULL,
    capacity INT,
    price    DOUBLE PRECISION,
    name     VARCHAR(255),
    numbered BOOLEAN,
    event_id BIGINT,
    CONSTRAINT fk_event
        FOREIGN KEY (event_id)
            REFERENCES event (id)
);