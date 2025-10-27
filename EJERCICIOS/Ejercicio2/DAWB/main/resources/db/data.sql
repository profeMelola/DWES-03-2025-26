-- Insertamos datos en la tabla fabricante
INSERT INTO fabricante ( nombre) VALUES ('Asus');
INSERT INTO fabricante ( nombre) VALUES ( 'Lenovo');
INSERT INTO fabricante ( nombre) VALUES ( 'Hewlett-Packard');
INSERT INTO fabricante ( nombre) VALUES ( 'Samsung');
INSERT INTO fabricante ( nombre) VALUES ( 'Seagate');
INSERT INTO fabricante ( nombre) VALUES ( 'Crucial');
INSERT INTO fabricante ( nombre) VALUES ( 'Gigabyte');
INSERT INTO fabricante ( nombre) VALUES ( 'Huawei');
INSERT INTO fabricante ( nombre) VALUES ( 'Xiaomi');

-- Insertamos datos en la tabla producto
INSERT INTO producto ( nombre, codigo, precio, codigo_fabricante) VALUES ('Disco duro SATA3 1TB', '123A',86.99, 5);
INSERT INTO producto ( nombre, codigo, precio, codigo_fabricante) VALUES ( 'Memoria RAM DDR4 8GB', '123B', 120, 6);
INSERT INTO producto ( nombre, codigo, precio, codigo_fabricante) VALUES ( 'Disco SSD 1 TB', '111A',150.99, 4);
INSERT INTO producto ( nombre, codigo, precio, codigo_fabricante) VALUES ( 'GeForce GTX 1050Ti', '222B',185, 7);
INSERT INTO producto ( nombre, codigo, precio, codigo_fabricante) VALUES ( 'GeForce GTX 1080 Xtreme', '666Z',755, 6);
INSERT INTO producto ( nombre, codigo, precio, codigo_fabricante) VALUES ( 'Monitor 24 LED Full HD', '669Z',202, 1);
INSERT INTO producto ( nombre, codigo, precio, codigo_fabricante) VALUES ( 'Monitor 27 LED Full HD', '666A',245.99, 1);
INSERT INTO producto ( nombre, codigo, precio, codigo_fabricante) VALUES ( 'Portátil Yoga 520', '666B',559, 2);
INSERT INTO producto ( nombre, codigo, precio, codigo_fabricante) VALUES ( 'Portátil Ideapd 320', '665Z',444, 2);
INSERT INTO producto ( nombre, codigo, precio, codigo_fabricante) VALUES ( 'Impresora HP Deskjet 3720', '666H',59.99, 3);
INSERT INTO producto ( nombre, codigo, precio, codigo_fabricante) VALUES ( 'Impresora HP Laserjet Pro M26nw', '655Z',180, 3);