USE GestorPartidas;
-- Insertar usuarios
INSERT INTO Usuario (Nombre, Contrasenha, Estado, Foto)
VALUES
    ('b@gmail.com', '123', 'feliz', NULL),
    ('prueba@gmail.com', 'contraseña2', 'triste', NULL),
    ('usuario3@gmail.com', 'contraseña3', 'enojado', NULL);

-- Insertar personajes para los usuarios
INSERT INTO Personaje (Usuario_ID, Personaje_Nombre, Caracteristica_Fuerza, Caracteristica_Destreza, Caracteristica_Constitucion, Caracteristica_Inteligencia, Caracteristica_Sabiduria, Caracteristica_Carisma, Bono_Competencia, Foto)
VALUES
	(1, 'Gareth el Valiente', 10, 12, 15, 8, 10, 10, 2, NULL),
    (1, 'Elara la Astuta', 8, 14, 12, 10, 8, 12, 3, NULL),
    (1, 'Thorn el Implacable', 12, 10, 14, 10, 10, 8, 2, NULL),
    (2, 'Lysander el Sabio', 10, 12, 15, 8, 10, 10, 2, NULL),
    (2, 'Aria la Veloz', 8, 14, 12, 10, 8, 12, 3, NULL),
    (2, 'Ezra el Justo', 12, 10, 14, 10, 10, 8, 2, NULL),
    (3, 'Kael el Poderoso', 10, 12, 15, 8, 10, 10, 2, NULL),
    (3, 'Isolde la Indomable', 8, 14, 12, 10, 8, 12, 3, NULL),
    (3, 'Rowan el Intrépido', 12, 10, 14, 10, 10, 8, 2, NULL);

-- Insertar armas
INSERT INTO Arma (Nombre, Ataque, Danho, Tipo, Arrojadiza, Car, Caracteristicas, Foto)
VALUES
    ('Espada larga', 8, '1d8', 'Cortante', FALSE, 'Fuerza', 'Versatil', NULL),
    ('Arco largo', 6, '1d10', 'Perforante', FALSE, 'Destreza', 'Destreza, A dos manos', NULL),
    ('Daga', 4, '1d4', 'Perforante', TRUE, 'Destreza', 'Arrojadiza, Ligera', NULL),
    ('Baston', 4, '1d6', 'Contundente', FALSE, 'Constitucion', 'Versatil', NULL),
    ('Hacha de batalla', 8, '1d8', 'Cortante', FALSE, 'Fuerza', 'Versatil', NULL);


-- Insertar armas para los personajes
INSERT INTO Arma_Personaje (Arma_ID, Personaje_ID, Usuario_ID, Ataque_Total, Bonificacion_Adicional, Competencia)
VALUES
    (1, 1, 1, 10, 2, TRUE),
    (2, 1, 1, 9, 0, TRUE),
    (1, 2, 1, 8, 0, FALSE),
    (3, 2, 1, 8, 0, TRUE),
    (4, 1, 1, 6, 2, TRUE),
    (5, 2, 1, 8, 0, FALSE),
    (1, 4, 2, 10, 2, TRUE),
    (2, 4, 2, 11, 2, TRUE),
    (1, 5, 2, 8, 0, FALSE),
    (3, 5, 2, 8, 0, TRUE),
    (4, 4, 2, 6, 2, TRUE),
    (5, 5, 2, 8, 0, FALSE),
    (1, 7, 3, 10, 2, TRUE),
    (2, 7, 3, 11, 2, TRUE),
    (1, 8, 3, 8, 0, FALSE),
    (3, 8, 3, 8, 0, TRUE),
    (4, 7, 3, 6, 2, TRUE),
    (5, 8, 3, 8, 0, FALSE);
