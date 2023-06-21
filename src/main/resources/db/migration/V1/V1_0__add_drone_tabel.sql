CREATE TABLE drone (
    id uuid PRIMARY KEY,
    model varchar(13) NOT NULL,
    weight_limit int NOT NULL,
    battery_capacity int2 NOT NULL,
    state varchar(10) NOT NULL
);