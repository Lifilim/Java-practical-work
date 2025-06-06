-- Добавляем новые столбцы для координат
ALTER TABLE city
ADD COLUMN longitude DECIMAL(10, 3),
ADD COLUMN latitude DECIMAL(10, 3),
ADD COLUMN dateValue varchar(10);

-- Добавляем ограничение уникальности для пары координат
ALTER TABLE city
ADD CONSTRAINT coordinates_unique UNIQUE (longitude, latitude);

