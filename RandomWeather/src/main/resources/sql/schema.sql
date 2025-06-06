CREATE TABLE IF NOT EXISTS city(
    id SERIAL PRIMARY KEY,
    name varchar(100) NOT NULL UNIQUE,
    latitude varchar(20),
    longitude varchar(20),
    --CONSTRAINT coordinates UNIQUE (latitude,  longitude),
    dateValue varchar(10),
    weatherStatus varchar(100)
);
