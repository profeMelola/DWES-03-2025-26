-- Categorías
INSERT INTO CATEGORIA (codigo, nombre, descripcion) VALUES ('CAT-2EST', '2 estrellas', 'Servicios básicos, ideal para presupuestos limitados');
INSERT INTO CATEGORIA (codigo, nombre, descripcion) VALUES ('CAT-3EST', '3 estrellas', 'Buena relación calidad-precio');
INSERT INTO CATEGORIA (codigo, nombre, descripcion) VALUES ('CAT-4EST', '4 estrellas', 'Alta calidad y confort');
INSERT INTO CATEGORIA (codigo, nombre, descripcion) VALUES ('CAT-5EST', '5 estrellas', 'Lujo y servicios exclusivos');

-- Hoteles
INSERT INTO HOTEL (codigo, nombre, descripcion, piscina, localidad, categoria_id) VALUES ('HOT-001', 'Hotel Playa', 'Frente al mar', true, 'Valencia', 3);
INSERT INTO HOTEL (codigo, nombre, descripcion, piscina, localidad, categoria_id) VALUES ('HOT-002', 'Hotel Centro', 'En el centro histórico', false, 'Madrid', 2);
INSERT INTO HOTEL (codigo, nombre, descripcion, piscina, localidad, categoria_id) VALUES ('HOT-003', 'Hotel Rural', 'Entorno natural', true, 'Asturias', 1);
INSERT INTO HOTEL (codigo, nombre, descripcion, piscina, localidad, categoria_id) VALUES ('HOT-004', 'Hotel Lujo', 'Exclusivo y elegante', true, 'Sevilla', 4);
INSERT INTO HOTEL (codigo, nombre, descripcion, piscina, localidad, categoria_id) VALUES ('HOT-005', 'Hotel Negocios', 'Cerca del aeropuerto', false, 'Barcelona', 3);
INSERT INTO HOTEL (codigo, nombre, descripcion, piscina, localidad, categoria_id) VALUES ('HOT-006', 'Hotel Familiar', 'Ideal para familias', true, 'Bilbao', 2);

-- Habitaciones
INSERT INTO HABITACION (codigo, tamano, doble, precio_noche, incluye_desayuno, ocupada, hotel_id) VALUES ('HAB-001-A', 20, false, 50.0, true, false, 1);
INSERT INTO HABITACION (codigo, tamano, doble, precio_noche, incluye_desayuno, ocupada, hotel_id) VALUES ('HAB-002-A', 30, true, 90.0, true, true, 1);
INSERT INTO HABITACION (codigo, tamano, doble, precio_noche, incluye_desayuno, ocupada, hotel_id) VALUES ('HAB-003-A', 25, true, 100.0, false, false, 1);
INSERT INTO HABITACION (codigo, tamano, doble, precio_noche, incluye_desayuno, ocupada, hotel_id) VALUES ('HAB-004-A', 15, false, 45.0, false, false, 2);
INSERT INTO HABITACION (codigo, tamano, doble, precio_noche, incluye_desayuno, ocupada, hotel_id) VALUES ('HAB-005-A', 25, true, 80.0, true, false, 2);
INSERT INTO HABITACION (codigo, tamano, doble, precio_noche, incluye_desayuno, ocupada, hotel_id) VALUES ('HAB-006-A', 35, true, 110.0, true, true, 2);
INSERT INTO HABITACION (codigo, tamano, doble, precio_noche, incluye_desayuno, ocupada, hotel_id) VALUES ('HAB-007-A', 10, false, 30.0, false, false, 3);
INSERT INTO HABITACION (codigo, tamano, doble, precio_noche, incluye_desayuno, ocupada, hotel_id) VALUES ('HAB-008-A', 20, true, 60.0, false, false, 3);
INSERT INTO HABITACION (codigo, tamano, doble, precio_noche, incluye_desayuno, ocupada, hotel_id) VALUES ('HAB-009-A', 30, true, 95.0, true, true, 3);
INSERT INTO HABITACION (codigo, tamano, doble, precio_noche, incluye_desayuno, ocupada, hotel_id) VALUES ('HAB-010-A', 40, true, 200.0, true, false, 4);
INSERT INTO HABITACION (codigo, tamano, doble, precio_noche, incluye_desayuno, ocupada, hotel_id) VALUES ('HAB-011-A', 35, true, 180.0, true, false, 4);
INSERT INTO HABITACION (codigo, tamano, doble, precio_noche, incluye_desayuno, ocupada, hotel_id) VALUES ('HAB-012-A', 20, false, 150.0, true, true, 4);
INSERT INTO HABITACION (codigo, tamano, doble, precio_noche, incluye_desayuno, ocupada, hotel_id) VALUES ('HAB-013-A', 15, false, 55.0, false, false, 5);
INSERT INTO HABITACION (codigo, tamano, doble, precio_noche, incluye_desayuno, ocupada, hotel_id) VALUES ('HAB-014-A', 25, true, 85.0, true, false, 5);
INSERT INTO HABITACION (codigo, tamano, doble, precio_noche, incluye_desayuno, ocupada, hotel_id) VALUES ('HAB-015-A', 30, true, 95.0, false, true, 5);
INSERT INTO HABITACION (codigo, tamano, doble, precio_noche, incluye_desayuno, ocupada, hotel_id) VALUES ('HAB-016-A', 20, false, 60.0, true, false, 6);
INSERT INTO HABITACION (codigo, tamano, doble, precio_noche, incluye_desayuno, ocupada, hotel_id) VALUES ('HAB-017-A', 30, true, 100.0, true, false, 6);
INSERT INTO HABITACION (codigo, tamano, doble, precio_noche, incluye_desayuno, ocupada, hotel_id) VALUES ('HAB-018-A', 25, true, 90.0, false, true, 6);


-- ROLES Y USUARIOS DE EJEMPLO!!!!!
INSERT INTO roles (name) VALUES ('ROLE_USER');
INSERT INTO roles (name) VALUES ('ROLE_ADMIN');
INSERT INTO roles (name) VALUES ('ROLE_PROFESOR');

INSERT INTO users (username, password) VALUES ('admin', '$2a$10$Ey/H5tNIopwhtVYXQ76Ms.oeiol3A4NiG3/HJekFyKkmgLVbR1n1C');
INSERT INTO users (username, password) VALUES ('user', '$2a$10$XVgKh.17he10CTo6Av57xOlSpnQWYxVJyshfkxjPKFLGTfth7FQZy');
INSERT INTO users (username, password) VALUES ('profesor', '$2a$10$Ey/H5tNIopwhtVYXQ76Ms.oeiol3A4NiG3/HJekFyKkmgLVbR1n1C');


INSERT INTO users_roles (role_id,user_id) VALUES(2,1);
INSERT INTO users_roles (role_id,user_id) VALUES(1,2);
INSERT INTO users_roles (role_id, user_id) VALUES (3, 3);
--INSERT INTO users_roles (role_id, user_id) VALUES (1, 3);

-- pendiente: añadir más roles a usuarios