INSERT INTO products (name, price, category)
VALUES ('Laptop', 1200.00, 'Electronics');
INSERT INTO products (name, price, category)
VALUES ('Smartphone', 800.00, 'Electronics');
INSERT INTO products (name, price, category)
VALUES ('Mechanical Keyboard', 150.00, 'Accessories');
INSERT INTO products (name, price, category)
VALUES ('24" Monitor', 220.00, 'Peripherals');
INSERT INTO products (name, price, category)
VALUES ('Wireless Headphones', 95.00, 'Audio');

INSERT INTO purchases (customer, date, total)
VALUES ('Carlos Perez', '2024-02-10T14:30:00', 1350.00);
INSERT INTO purchases (customer, date, total)
VALUES ('Ana Gomez', '2024-02-01T14:40:00', 1020.00);
INSERT INTO purchases (customer, date, total)
VALUES ('Luis Ramirez', '2024-02-10T14:50:00', 890.00);


INSERT INTO purchase_product (purchase_id, product_id)
VALUES (1, 1); -- Purchase 1 -> Laptop
INSERT INTO purchase_product (purchase_id, product_id)
VALUES (1, 3); -- Purchase 1 -> Mechanical Keyboard
INSERT INTO purchase_product (purchase_id, product_id)
VALUES (2, 2); -- Purchase 2 -> Smartphone
INSERT INTO purchase_product (purchase_id, product_id)
VALUES (2, 4); -- Purchase 2 -> 24" Monitor
INSERT INTO purchase_product (purchase_id, product_id)
VALUES (3, 5); -- Purchase 3 -> Wireless Headphones
INSERT INTO purchase_product (purchase_id, product_id)
VALUES (3, 1); -- Purchase 3 -> Laptop


INSERT INTO roles (name)
VALUES ('ROLE_USER');
INSERT INTO roles (name)
VALUES ('ROLE_ADMIN');

INSERT INTO users (username, password)
VALUES ('diego',
        '$2a$10$IKp9rdPtsq4/L28Ivj85yOI0nyTRwKX1fHZfXDAKRePHQUD2vATGK');
INSERT INTO users (username, password)
VALUES ('admin',
        '$2a$10$IKp9rdPtsq4/L28Ivj85yOI0nyTRwKX1fHZfXDAKRePHQUD2vATGK');


INSERT INTO users_roles (role_id, user_id)
VALUES (1, 1);
INSERT INTO users_roles (role_id, user_id)
VALUES (2, 2);