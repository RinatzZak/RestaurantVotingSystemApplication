INSERT INTO USERS (name, email, password)
VALUES ('User', 'user@yandex.ru', '{noop}password'),
       ('Admin', 'admin@gmail.com', '{noop}admin');

INSERT INTO USER_ROLES (role, user_id)
VALUES ('USER', 1),
       ('ADMIN', 2),
       ('USER', 2);

INSERT INTO RESTORAUNT (address, name)
VALUES ('193 Synods Street Eden Terrace', 'Kazuya'),
       ('Commercial Bay Level 2/7 Queen Street', 'Ahi'),
       ('Level 1/12 Wyndham Street', 'Culprit');

INSERT INTO MEAL (calories, description, price, RESTAURANT_ID)
VALUES (500, 'Rice with seafood', 400, 1),
       (700, 'Soup puree', 300, 2),
       (1000, 'Royal roast beef', 700, 3);
