CREATE TABLE cars (
    id SERIAL PRIMARY KEY,
    brand TEXT NOT NULL,
    model TEXT NOT NULL,
    cost MONEY
);

CREATE TABLE drivers (
    id SERIAL PRIMARY KEY,
    name TEXT,
    age INTEGER CHECK (age > 18),
    having_license BOOLEAN,
    car_id INTEGER REFERENCES cars (id)
);

DROP TABLE drivers;
DROP TABLE cars;
