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

INSERT INTO MEALS (description, price)
VALUES ('Rice with seafood', 400),
       ('Soup puree', 300),
       ('Royal roast beef', 600),
       ('Orange juice', 10),
       ('Coffee', 10),
       ('Milk cocktail', 20),
       ('Orange ice cream', 50),
       ('Cherry ice cream', 55),
       ('Apple ice cream', 51);


INSERT INTO MENU (RESTAURANT_ID)
VALUES ( 1 ),
       (3),
       (2);

INSERT INTO MENU_MEAL(menu_id, meal_id)
VALUES (1, 1),
       (1, 2),
       (1, 5),
       (3, 1),
       (3, 3),
       (3, 6);
