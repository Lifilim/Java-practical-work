CREATE TABLE IF NOT EXISTS city(
    id SERIAL PRIMARY KEY,
    name varchar(100) NOT NULL UNIQUE,
    latitude DECIMAL(10, 3),
    longitude DECIMAL(10, 3),
    --CONSTRAINT coordinates UNIQUE (latitude,  longitude),
    dateValue varchar(10),
    weatherStatus varchar(100)
);

CREATE TABLE IF NOT EXISTS city(
    id SERIAL PRIMARY KEY,
    name varchar(100) NOT NULL UNIQUE,
    weatherStatus varchar(100)
);
