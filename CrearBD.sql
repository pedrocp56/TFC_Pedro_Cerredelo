DROP DATABASE IF EXISTS GestorPartidas;
CREATE DATABASE IF NOT EXISTS GestorPartidas;
USE GestorPartidas;

CREATE TABLE Usuario (
    ID INT AUTO_INCREMENT PRIMARY KEY,
    Nombre VARCHAR(25) NOT NULL UNIQUE,
    Contrasenha VARCHAR(25) NOT NULL,
    Estado VARCHAR(100),
    Foto VARCHAR(255)
);

CREATE TABLE Campaña (
    ID INT AUTO_INCREMENT PRIMARY KEY,
    Nombre VARCHAR(50) NOT NULL,
    Descripcion TEXT,
    Fecha DATE,
    Foto VARCHAR(255),
    ID_Creador INT,
    FOREIGN KEY (ID_Creador) REFERENCES Usuario(ID)
);

CREATE TABLE Arma (
    ID INT AUTO_INCREMENT PRIMARY KEY,
    Nombre VARCHAR(50) NOT NULL UNIQUE,
    Ataque INT NOT NULL,
    Daño INT NOT NULL,
    Tipo VARCHAR(15) NOT NULL,
    Arrojadiza BOOLEAN,
    Car VARCHAR(15),
    Caracteristicas TEXT,
    Foto VARCHAR(255)
);

CREATE TABLE Personaje (
    ID INT AUTO_INCREMENT,
    Usuario_ID INT NOT NULL,
    Campaña_ID INT NOT NULL,
    Personaje_Nombre VARCHAR(50) NOT NULL UNIQUE,
    Caracteristica_Fuerza INT NOT NULL,
    Caracteristica_Destreza INT NOT NULL,
    Caracteristica_Constitución INT NOT NULL,
    Caracteristica_Inteligencia INT NOT NULL,
    Caracteristica_Sabiduria INT NOT NULL,
    Caracteristica_Carisma INT NOT NULL,
    Bono_Competencia INT NOT NULL,
    Foto VARCHAR(255),
    PRIMARY KEY (ID),
    FOREIGN KEY (Usuario_ID) REFERENCES Usuario(ID),
    FOREIGN KEY (Campaña_ID) REFERENCES Campaña(ID)
);

CREATE TABLE Arma_Personaje (
    Arma_ID INT NOT NULL,
    Usuario_ID INT NOT NULL,
    Personaje_ID INT NOT NULL,
    Ataque_Total INT NOT NULL,
    Bonificación_Adicional INT NOT NULL,
    Competencia BOOLEAN NOT NULL,
    PRIMARY KEY (Arma_ID, Usuario_ID, Personaje_ID),
    FOREIGN KEY (Arma_ID) REFERENCES Arma(ID),
    FOREIGN KEY (Usuario_ID) REFERENCES Usuario(ID),
    FOREIGN KEY (Personaje_ID) REFERENCES Personaje(ID)
);
