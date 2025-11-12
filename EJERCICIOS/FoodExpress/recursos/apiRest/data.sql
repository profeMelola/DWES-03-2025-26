-- Roles
INSERT INTO roles (nombre) VALUES ('ADMIN');
INSERT INTO roles (nombre) VALUES ('CLIENTE');
INSERT INTO roles (nombre) VALUES ('REPARTIDOR');

-- Usuarios
INSERT INTO usuarios (username, password, nombre, email, rol_id) VALUES
('admin', 'admin123', 'Admin Root', 'admin@foodexpress.com', 1),
('juan', '1234', 'Juan Pérez', 'juan@correo.com', 2),
('maria', '1234', 'María López', 'maria@correo.com', 2),
('repa1', '1234', 'Repartidor Uno', 'repa1@correo.com', 3);

-- Restaurantes
INSERT INTO restaurantes (nombre, direccion, telefono) VALUES
('Burger Planet', 'Calle Luna 45', '600111222'),
('Pasta Nova', 'Av. Italia 12', '600222333'),
('Sushi Go', 'Calle Japón 3', '600333444');

-- Platos (unos 15 para paginar)
INSERT INTO platos (nombre, precio, categoria, restaurante_id) VALUES
('Cheeseburger', 8.50, 'Hamburguesas', 1),
('Doble Bacon', 10.90, 'Hamburguesas', 1),
('Veggie Burger', 9.20, 'Hamburguesas', 1),
('Spaghetti Carbonara', 11.50, 'Pasta', 2),
('Lasagna Bolognesa', 12.00, 'Pasta', 2),
('Fetuccine Alfredo', 10.75, 'Pasta', 2),
('Sushi Maki', 13.50, 'Sushi', 3),
('Nigiri Salmón', 12.90, 'Sushi', 3),
('Tempura', 9.80, 'Entrante', 3),
('Patatas Deluxe', 4.50, 'Entrante', 1),
('Tiramisú', 5.90, 'Postre', 2),
('Helado Matcha', 6.20, 'Postre', 3),
('Onigiri', 7.80, 'Entrante', 3),
('Tagliatelle Pesto', 10.20, 'Pasta', 2),
('Chicken Burger', 9.80, 'Hamburguesas', 1);

-- Pedidos
INSERT INTO pedidos (fecha, estado, usuario_id, restaurante_id) VALUES
('2025-10-01 12:00:00', 'ENTREGADO', 2, 1),
('2025-10-02 13:00:00', 'PREPARANDO', 3, 2),
('2025-10-03 20:15:00', 'ENTREGADO', 2, 3);

-- Detalles pedidos
INSERT INTO detalles_pedido VALUES (1, 1, 2, 17.00);
INSERT INTO detalles_pedido VALUES (1, 10, 1, 4.50);
INSERT INTO detalles_pedido VALUES (2, 4, 1, 11.50);
INSERT INTO detalles_pedido VALUES (2, 5, 1, 12.00);
INSERT INTO detalles_pedido VALUES (3, 7, 1, 13.50);
INSERT INTO detalles_pedido VALUES (3, 8, 1, 12.90);
INSERT INTO detalles_pedido VALUES (3, 12, 1, 6.20);
