-- Ensure usernames are unique for reliable login lookups
CREATE UNIQUE INDEX IF NOT EXISTS ux_users_username ON users (username);

