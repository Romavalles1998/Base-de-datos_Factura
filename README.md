## CRUD para comprobar datos:

### Crear tablas

- CREATE TABLE Usuario (
  usuario_id INTEGER PRIMARY KEY,
  nombre VARCHAR(255),
  apellido VARCHAR(255)
);

- CREATE TABLE Coche (
  marca TEXT,
  usuario_id INTEGER,
  FOREIGN KEY (usuario_id) REFERENCES Usuario(usuario_id)
);

### Insertar usuarios y coches

#### Añadir un usuario
- INSERT INTO Usuario (usuario_id, nombre, apellido) VALUES (1, 'Juan', 'Pérez');
- INSERT INTO Usuario (nombre, apellido) VALUES ('Laura', 'Martínez');
- INSERT INTO Usuario (nombre, apellido) VALUES ('Roberto', 'Fernández');
- INSERT INTO Usuario (nombre, apellido) VALUES ('Ana', 'Ramírez');

#### Añadir un coche asociado a un usuario
- INSERT INTO Coche (marca, usuario_id) VALUES ('Toyota', 1);
- INSERT INTO Coche (marca, usuario_id) VALUES ('Ford', 2);
- INSERT INTO Coche (marca, usuario_id) VALUES ('Chevrolet', 3);
- INSERT INTO Coche (marca, usuario_id) VALUES ('Nissan', 4);
--------------------------------------------------------
Seleccionar Datos (leer)

-- Seleccionar todos los usuarios
SELECT * FROM Usuario;

-- Seleccionar todos los coches
SELECT * FROM Coche;

-- Seleccionar todos los coches con la información del usuario
SELECT Coche.marca, Usuario.nombre, Usuario.apellido
FROM Coche
JOIN Usuario ON Coche.usuario_id = Usuario.usuario_id;

-----------------------------------------------------
Actualizar Datos

-- Actualizar el apellido de un usuario
UPDATE Usuario SET apellido = 'García' WHERE usuario_id = 1;

-- Cambiar la marca de un coche
UPDATE Coche SET marca = 'Honda' WHERE usuario_id = 1;
-----------------------------------------------------
Eliminar Datos

-- Eliminar un usuario
DELETE FROM Usuario WHERE usuario_id = 1;

-- Eliminar un coche
DELETE FROM Coche WHERE usuario_id = 1;
