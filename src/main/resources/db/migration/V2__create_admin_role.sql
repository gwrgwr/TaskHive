INSERT INTO user_entity (user_id, created_at, email, name, password, role, username)
VALUES (gen_random_uuid(), now(), 'admin@gmail.com', 'admin', '$2a$10$rqfcRElWz36lY95fTZ4c3eunuLAJiDkztxqpi0JoDcL.AH47qTIJC', 'ADMIN', 'admin');

