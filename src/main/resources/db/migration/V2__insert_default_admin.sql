INSERT INTO users (username, password, role, firstname, lastname)
VALUES ('superadmin', '$2a$10$wH1QwQwQwQwQwQwQwOQwQwQwQwQwQwQwQwQwQwQwQwQwQwQwQwQw', 'ADMIN', 'Super', 'Admin')
ON CONFLICT (username) DO NOTHING;
