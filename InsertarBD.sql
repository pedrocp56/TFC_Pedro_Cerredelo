USE GestorPartidas;
-- Insertar usuarios
INSERT INTO Usuario (Nombre, Contrasenha, Estado, Foto)
VALUES
    ('b@gmail.com', '123', 'feliz', NULL),
    ('usuario2', 'contraseña2', 'triste', NULL),
    ('usuario3', 'contraseña3', 'enojado', NULL);

-- Insertar personajes para los usuarios
INSERT INTO Personaje (Usuario_ID, Personaje_Nombre, Caracteristica_Fuerza, Caracteristica_Destreza, Caracteristica_Constitución, Caracteristica_Inteligencia, Caracteristica_Sabiduria, Caracteristica_Carisma, Bono_Competencia, Foto)
VALUES
    (1, 'Personaje1Usuario1', 10, 12, 15, 8, 10, 10, 2, NULL),
    (1, 'Personaje2Usuario1', 8, 14, 12, 10, 8, 12, 3, NULL),
    (1, 'Personaje3Usuario1', 12, 10, 14, 10, 10, 8, 2, NULL),
    (2, 'Personaje1Usuario2', 10, 12, 15, 8, 10, 10, 2, NULL),
    (2, 'Personaje2Usuario2', 8, 14, 12, 10, 8, 12, 3, NULL)
    (2, 'Personaje3Usuario2', 12, 10, 14, 10, 10, 8, 2, NULL),
    (3, 'Personaje1Usuario3', 10, 12, 15, 8, 10, 10, 2, NULL),
    (3, 'Personaje2Usuario3', 8, 14, 12, 10, 8, 12, 3, NULL),
    (3, 'Personaje3Usuario3', 12, 10, 14, 10, 10, 8, 2, NULL);

-- Insertar armas
INSERT INTO Arma (Nombre, Ataque, Danho, Tipo, Arrojadiza, Car, Caracteristicas, Foto)
VALUES
    ('Espada larga', 8, '1d8', 'Corte', FALSE, 'A una mano', 'Versatil', NULL),
    ('Arco largo', 6, '1d10', 'Perforante', FALSE, NULL, 'Alcance, A dos manos', NULL),
    ('Daga', 4, '1d4', 'Perforante', TRUE, 'A una mano', 'Arrojadiza, Ligera', NULL),
    ('Baston', 4, '1d6', 'Contundente', FALSE, 'A dos manos', 'Versatil', NULL),
    ('Hacha de batalla', 8, '1d8', 'Corte', FALSE, 'A una mano', 'Versatil', NULL);

-- Insertar armas para los personajes
INSERT INTO Arma_Personaje (Arma_ID, Personaje_ID, Usuario_ID, Ataque_Total, Bonificación_Adicional, Competencia)
VALUES
    (1, 1, 1, 10, 2, TRUE),
    (2, 1, 1, 10, 2, TRUE),
    (1, 2, 1, 6, 0, FALSE),
    (3, 2, 1, 8, 0, TRUE),
    (4, 1, 1, 4, 2, TRUE),
    (5, 2, 1, 8, 0, FALSE),
    (1, 4, 2, 10, 2, TRUE),
    (2, 4, 2, 10, 2, TRUE),
    (1, 5, 2, 6, 0, FALSE),
    (3, 5, 2, 8, 0, TRUE),
    (4, 4, 2, 4, 2, TRUE),
    (5, 5, 2, 8, 0, FALSE),
    (1, 7, 3, 10, 2, TRUE),
    (2, 7, 3, 10, 2, TRUE),
    (1, 8, 3, 6, 0, FALSE),
    (3, 8, 3, 8, 0, TRUE),
    (4, 7, 3, 4, 2, TRUE),
    (5, 8, 3, 8, 0, FALSE);