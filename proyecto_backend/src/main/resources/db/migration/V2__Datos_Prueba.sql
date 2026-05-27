
-- Inserto como mínimo 2 géneros distintos 
INSERT IGNORE INTO genero (id, nombre) VALUES (1, 'Hombre');
INSERT IGNORE INTO genero (id, nombre) VALUES (2, 'Mujer');

-- Inserto como mínimo 4 puestos de trabajo distinto
INSERT IGNORE INTO puesto_trabajo (id, nombre) VALUES (1, 'Senior Project Manager');
INSERT IGNORE INTO puesto_trabajo (id, nombre) VALUES (2, 'Senior Architect');
INSERT IGNORE INTO puesto_trabajo (id, nombre) VALUES (3, 'Head Of Operations');
INSERT IGNORE INTO puesto_trabajo (id, nombre) VALUES (4, 'Automation Tester');

-- Inserto 1 usuario con todos sus datos obligatorios rellenos
-- Meto al usuario MAR para que coincida 
INSERT IGNORE INTO usuario (id, nick_usuario, contrasena, fecha_hora_creacion, id_genero, nombre, primer_apellido, segundo_apellido, fecha_nacimiento, hora_desayuno, id_puesto, es_admin)
VALUES (1, 'MAR', 'simplepassword', NOW(), 1, 'Miguel Angel', 'Rosales', 'Navarro', '1981-12-18', '09:15:00', 1, 1);

-- Le meto al menos 1 dirección obligatoria 
INSERT IGNORE INTO direccion (id, nombre_calle, numero_calle, id_usuario, direccion_principal)
VALUES (1, 'Piruleta Street', 5, 1, 1);