-- Add cover_image column to books table if it does not exist
ALTER TABLE books ADD COLUMN IF NOT EXISTS cover_image VARCHAR(255);

-- Add published_year column to books table if it does not exist
ALTER TABLE books ADD COLUMN IF NOT EXISTS published_year VARCHAR(10);

-- Add genres column to books table if it does not exist
ALTER TABLE books ADD COLUMN IF NOT EXISTS genres VARCHAR(255);
