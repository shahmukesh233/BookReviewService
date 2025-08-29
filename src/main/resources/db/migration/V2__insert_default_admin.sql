INSERT INTO users (username, password, role, firstname, lastname)
VALUES ('superadmin', '$2a$10$w3iqqVdi9V0Z.KAvj2b84e084ZIp5C1G6F7OsCzN/Pc2f9P24gKC2', 'ADMIN', 'Super', 'Admin')
ON CONFLICT (username) DO NOTHING;
