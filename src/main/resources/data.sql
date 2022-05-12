INSERT INTO USERS (name, email, password)
VALUES ('User', 'user@yandex.ru', '{noop}password'),
       ('Admin', 'admin@gmail.com', '{noop}admin');

INSERT INTO USER_ROLES (role, user_id)
VALUES ('USER', 1),
       ('ADMIN', 2),
       ('USER', 2);

INSERT INTO RESTAURANT (address, name)
VALUES ('193 Synods Street Eden Terrace', 'Kazuya'),
       ('Commercial Bay Level 2/7 Queen Street', 'Ahi'),
       ('Level 1/12 Wyndham Street', 'Culprit'),
       ('95-97 Customs Street West Market Square', 'Hello Beasty');

INSERT INTO DISH (name, price, RESTAURANT_ID)
VALUES ('Rice with seafood', 400.00, 1),
       ('Soup puree', 300.00, 2),
       ('Royal roast beef', 600.00, 3),
       ('Orange juice', 10.00, 1),
       ('Coffee', 10.00, 2),
       ('Milk cocktail', 20.00, 3),
       ('Orange ice cream', 50.00, 1),
       ('Cherry ice cream', 55.00, 2),
       ('Apple ice cream', 51.00, 3);

INSERT INTO VOTE (DATE_ADDED, RESTAURANT_ID, USER_ID)
VALUES ('2020-10-10', 1, 1),
       ('2021-11-11', 2, 1),
       ('2022-01-01', 3, 1),
       (current_date, 1, 1);
