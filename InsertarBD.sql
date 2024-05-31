USE GestorPartidas;

-- Insertar usuarios
INSERT INTO Usuario (Nombre, Contrasenha, Estado, Foto)
VALUES
    ('b@gmail.com', '123', 'feliz', NULL),
    ('usuario2', 'contraseña2', 'triste', NULL),
    ('usuario3', 'contraseña3', 'enojado', NULL);

-- Obtener los IDs de los usuarios insertados
SET @usuario1_id = LAST_INSERT_ID();

-- Insertar personajes para el usuario1
INSERT INTO Personaje (Usuario_ID, Personaje_Nombre, Caracteristica_Fuerza, Caracteristica_Destreza, Caracteristica_Constitución, Caracteristica_Inteligencia, Caracteristica_Sabiduria, Caracteristica_Carisma, Bono_Competencia, Foto)
VALUES
    ('1', 'Personaje1Usuario1', 10, 12, 15, 8, 10, 10, 2, NULL),
    ('1', 'Personaje2Usuario1', 8, 14, 12, 10, 8, 12, 3, NULL),
    ('1', 'Personaje3Usuario1', 12, 10, 14, 10, 10, 8, 2, NULL),
    ('2', 'Personaje1Usuario2', 10, 12, 15, 8, 10, 10, 2, NULL),
    ('2', 'Personaje2Usuario2', 8, 14, 12, 10, 8, 12, 3, NULL),
    ('2', 'Personaje3Usuario2', 12, 10, 14, 10, 10, 8, 2, NULL),
    ('3', 'Personaje1Usuario3', 10, 12, 15, 8, 10, 10, 2, NULL),
    ('3', 'Personaje2Usuario3', 8, 14, 12, 10, 8, 12, 3, NULL),
    ('3', 'Personaje3Usuario3', 12, 10, 14, 10, 10, 8, 2, NULL);


