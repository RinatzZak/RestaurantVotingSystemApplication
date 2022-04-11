INSERT INTO USERS (name, email, password)
VALUES ('User', 'user@yandex.ru', '{noop}password'),
       ('Admin', 'admin@gmail.com', '{noop}admin');

INSERT INTO USER_ROLES (role, user_id)
VALUES ('USER', 1),
       ('ADMIN', 2),
       ('USER', 2);

INSERT INTO RESTAURANT (address, description)
VALUES ('193 Synods Street Eden Terrace', 'Kazuya'),
       ('Commercial Bay Level 2/7 Queen Street', 'Ahi'),
       ('Level 1/12 Wyndham Street', 'Culprit');

INSERT INTO MEAL (description, price, RESTAURANT_ID)
VALUES ('Rice with seafood', 400, 1),
       ('Soup puree', 300, 2),
       ('Royal roast beef', 700, 3),
       ('Orange juice', 10, 1),
       ('Coffee', 10, 2),
       ('Milk cocktail', 20, 3),
       ('Orange ice cream', 50, 1),
       ('Cherry ice cream', 55, 2),
       ('Apple ice cream', 51, 3);

