INSERT INTO user_entity (user_id, created_at, email, name, password, role, username)
VALUES (gen_random_uuid(), now(), 'staff@gmail.com', 'staff', '$2a$10$D9PUIzs8TglvBOl7O9tMsOWRYcav2StweJRMIwZmVZCkkcCOWueYK', 'STAFF', 'staff');

INSERT INTO user_entity (user_id, created_at, email, name, password, role, username)
VALUES (gen_random_uuid(), now(), 'user@gmail.com', 'user', '$2a$10$vXeNnJg3j9KQ/m9ztaIoUuXXoYxyIE1h01HMlu6iY7AON2PncuVWm', 'USER', 'user');

INSERT INTO user_entity (user_id, created_at, email, name, password, role, username)
VALUES (gen_random_uuid(), now(), 'guest@gmail.com', 'guest', '$2a$10$FpFh8YajkE9A6dQqEPiPkuirSgEfVCYBS3PLCaYbJ9EFGCHHIzq8W', 'GUEST', 'guest');