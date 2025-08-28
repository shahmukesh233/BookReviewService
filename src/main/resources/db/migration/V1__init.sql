-- Flyway Migration Script: Initial schema
CREATE TABLE IF NOT EXISTS users (
    id SERIAL PRIMARY KEY,
    username VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(50) NOT NULL,
    firstname VARCHAR(255),
    lastname VARCHAR(255)
);

-- Failsafe: Add columns if not exist
ALTER TABLE users ADD COLUMN IF NOT EXISTS firstname VARCHAR(255);
ALTER TABLE users ADD COLUMN IF NOT EXISTS lastname VARCHAR(255);

CREATE TABLE IF NOT EXISTS books (
    id SERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    author VARCHAR(255) NOT NULL,
    description TEXT,
    genres VARCHAR(255),
    cover_image VARCHAR(255),
    published_year VARCHAR(10),
    avg_rating DOUBLE PRECISION,
    review_count INT
);

ALTER TABLE books ADD COLUMN IF NOT EXISTS genres VARCHAR(255);
ALTER TABLE books ADD COLUMN IF NOT EXISTS cover_image VARCHAR(255);
ALTER TABLE books ADD COLUMN IF NOT EXISTS published_year VARCHAR(10);
ALTER TABLE books ADD COLUMN IF NOT EXISTS avg_rating DOUBLE PRECISION;
ALTER TABLE books ADD COLUMN IF NOT EXISTS review_count INT;

CREATE TABLE IF NOT EXISTS reviews (
    id SERIAL PRIMARY KEY,
    rating INT NOT NULL,
    comment TEXT,
    book_id INT REFERENCES books(id) ON DELETE CASCADE,
    user_id INT REFERENCES users(id) ON DELETE CASCADE,
    created_at TIMESTAMP,
    modified_at TIMESTAMP
);

-- Failsafe: Add columns if not exist
ALTER TABLE reviews ADD COLUMN IF NOT EXISTS created_at TIMESTAMP;
ALTER TABLE reviews ADD COLUMN IF NOT EXISTS modified_at TIMESTAMP;
