CREATE TABLE IF NOT EXISTS city(
    id SERIAL PRIMARY KEY,
    name varchar(100) NOT NULL UNIQUE,
    --positionX FLOAT NOT NULL,
    --positionY FLOAT NOT NULL,
    --temperature INTEGER NOT NULL,
    weatherStatus varchar(100)
);
