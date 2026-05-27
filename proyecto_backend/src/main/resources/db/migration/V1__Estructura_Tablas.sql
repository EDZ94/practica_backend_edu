-- =========================================================================
-- MI SCRIPT DDL PARA CREAR EL ESQUEMA Y LAS TABLAS OBLIGATORIAS (EJERCICIO 1)
-- =========================================================================

-- Tabla maestra de Géneros 
CREATE TABLE IF NOT EXISTS genero (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL
);

-- Tabla maestra de Puestos de Trabajo
CREATE TABLE IF NOT EXISTS puesto_trabajo (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL
);

-- Tabla principal de Usuarios con todos sus campos obligatorios
CREATE TABLE IF NOT EXISTS usuario (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nick_usuario VARCHAR(50) NOT NULL UNIQUE, -- Le meto el UNIQUE para controlar los nicks duplicados
    contrasena VARCHAR(255) NOT NULL,
    fecha_hora_creacion DATETIME NOT NULL,
    id_genero INT NOT NULL,
    nombre VARCHAR(100) NOT NULL,
    primer_apellido VARCHAR(100) NOT NULL,
    segundo_apellido VARCHAR(100) NULL, -- NULLABLE 
    fecha_nacimiento DATE NOT NULL,
    hora_desayuno TIME NULL, -- NULLABLE 
    id_puesto INT NULL,      -- NULLABLE 
    es_admin BIT(1) NOT NULL DEFAULT 0, -- boolean obligatorio
    FOREIGN KEY (id_genero) REFERENCES genero(id),
    FOREIGN KEY (id_puesto) REFERENCES puesto_trabajo(id)
);

-- Tabla de Direcciones vinculada a los usuarios
CREATE TABLE IF NOT EXISTS direccion (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre_calle VARCHAR(255) NOT NULL,
    numero_calle INT NULL, -- NULLABLE 
    id_usuario INT NOT NULL,
    direccion_principal BIT(1) NOT NULL DEFAULT 0, -- 
    FOREIGN KEY (id_usuario) REFERENCES usuario(id) ON DELETE CASCADE
);