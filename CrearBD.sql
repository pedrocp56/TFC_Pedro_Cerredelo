DROP DATABASE IF EXISTS GestorPartidas;
CREATE DATABASE IF NOT EXISTS GestorPartidas;
USE GestorPartidas;

CREATE TABLE Usuario (
    ID INT AUTO_INCREMENT PRIMARY KEY,
    Nombre VARCHAR(25) NOT NULL UNIQUE,
    Contrasenha VARCHAR(25) NOT NULL,
    Estado VARCHAR(100),
    Foto LONGBLOB
);

CREATE TABLE Arma (
    ID INT AUTO_INCREMENT PRIMARY KEY,
    Nombre VARCHAR(50) NOT NULL UNIQUE,
    Ataque INT NOT NULL,
    Danho VARCHAR(15) NOT NULL,
    Tipo VARCHAR(15) NOT NULL,
    Arrojadiza BOOLEAN,
    Car VARCHAR(15),
    Caracteristicas TEXT,
    Foto LONGBLOB
);

CREATE TABLE Personaje (
    Usuario_ID INT NOT NULL,
    Personaje_ID INT AUTO_INCREMENT NOT NULL UNIQUE,
    Personaje_Nombre VARCHAR(50) NOT NULL,
    Caracteristica_Fuerza INT NOT NULL,
    Caracteristica_Destreza INT NOT NULL,
    Caracteristica_Constitucion INT NOT NULL,
    Caracteristica_Inteligencia INT NOT NULL,
    Caracteristica_Sabiduria INT NOT NULL,
    Caracteristica_Carisma INT NOT NULL,
    Bono_Competencia INT NOT NULL,
    Foto LONGBLOB,
    PRIMARY KEY (Usuario_ID, Personaje_ID),
    FOREIGN KEY (Usuario_ID) REFERENCES Usuario(ID)
);

CREATE TABLE Arma_Personaje (
    Arma_ID INT NOT NULL,
    Personaje_ID INT NOT NULL,
    Usuario_ID INT NOT NULL,
    Ataque_Total INT NOT NULL,
    Bonificacion_Adicional INT NOT NULL,
    Competencia BOOLEAN NOT NULL,
    PRIMARY KEY (Arma_ID, Personaje_ID, Usuario_ID),
    FOREIGN KEY (Arma_ID) REFERENCES Arma(ID) ON DELETE CASCADE,
    FOREIGN KEY (Personaje_ID, Usuario_ID) REFERENCES Personaje(Personaje_ID, Usuario_ID) ON DELETE CASCADE
);




