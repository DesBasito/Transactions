--liquibase formatted sql
--changeset Abu:add_initial_data

insert into AUTHORITIES (ROLE)
values ('USER');

insert into USERS(email, unique_id, name, surname, password, role_id, reset_password_token, enabled)
values ('john.doe@example.com', '723456', 'John', 'Doe',/* qwe */
        '$2a$12$WB2YUbFcCN0tm44SBcKUjua9yiFBsfB3vW02IjuwzY7HGtlQIKzy2', 1, null, true),
       ('jane.smith@example.com', '234677', 'Jane', 'Smith',/* qwerty */
        '$2a$10$UYXM0mgh1OLkf6r7Iq0kCe2KV/fZT/GB1SkbdddpjzXEHK8NOQomi', 1, null, true),
       ('alice.jones@example.com', '986432', 'Alice', 'Jones',/* password123 */
        '$2a$10$VGkwdmvXTqgn6yfmdZ6w7.NdMDeSYTs4JcDdySZ.yMdQ4qzGH3At.', 1, null, true),
       ('bob.brown@example.com', '123456', 'Bob', 'Brown', /* password456 */'$2a$10$zLoPtjUjaZAcOPjEuFunnOS13swef0FFxo06ujuyobopYxwD5F/s2',
        1, null, true);


INSERT INTO accounts (balance, owner)
VALUES (1000.00, 'john.doe@example.com'),
       (1000.00, 'jane.smith@example.com'),
       (7000.00, 'alice.jones@example.com'),
       (1000.00, 'bob.brown@example.com');

INSERT INTO transfers (sum_of_transfer, sender_id, recipient_id, transaction_date)
VALUES (200.00, 'john.doe@example.com', 'jane.smith@example.com', '2024-05-25 10:00:00'),
       (150.00, 'jane.smith@example.com', 'alice.jones@example.com', '2024-05-25 11:00:00'),
       (500.00, 'alice.jones@example.com', 'bob.brown@example.com', '2024-05-25 12:00:00'),
       (100.00, 'bob.brown@example.com', 'john.doe@example.com', '2024-05-25 13:00:00');

INSERT INTO PROVIDERS (NAME,Balance)
VALUES
    ('O!',0.0),
    ('Beeline',0.0),
    ('Megacom',0.0),
    ('BSK',0.0);

INSERT INTO USERS_PROVIDER (identifier, provider, balance)
VALUES
    ('john_provider', (SELECT id FROM PROVIDERS WHERE NAME = 'O!'),0.0),
    ('jane_provider', (SELECT id FROM PROVIDERS WHERE NAME = 'Beeline'),0.0),
    ('alice_provider', (SELECT id FROM PROVIDERS WHERE NAME = 'BSK'),0.0),
    ('bob_provider', (SELECT id FROM PROVIDERS WHERE NAME = 'Megacom'),0.0);
