CREATE TABLE storage (

    id uuid PRIMARY KEY,
    medication_name varchar(255),
    drone_id uuid NOT NULL,
    count int,
    CONSTRAINT fk_drone_to_storage FOREIGN KEY(drone_id) REFERENCES drone(id),
    CONSTRAINT fk_medication_to_storage FOREIGN KEY(medication_name) REFERENCES medication(name)
);