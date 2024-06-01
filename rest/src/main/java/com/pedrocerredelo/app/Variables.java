package com.pedrocerredelo.app;

public class Variables {
    // Definición de las rutas de la API para las operaciones relacionadas con Armas
    public static final String ARMA_BASE_PATH = "/Armas";
    public static final String ARMA_GET_ALL = "/verArmas";
    public static final String ARMA_SEARCH = "/buscarArma/{id}";
    public static final String ARMA_SEARCH_BY_NAME = "/buscarArmaByName/{name}";
    public static final String ARMA_SAVE = "/guardarArma";
    public static final String ARMA_UPDATE = "/actualizarArma/{id}";
    public static final String ARMA_DELETE = "/eliminarArma/{id}";



    // Definición de las rutas de la API para las operaciones relacionadas con Campanhas
    public static final String CAMPANHA_BASE_PATH = "/Campanhas";
    public static final String CAMPANHA_GET_ALL = "/verCampanhas";
    public static final String CAMPANHA_SEARCH = "/buscarCamp/{id}";
    public static final String CAMPANHA_SEARCH_BY_NAME = "/buscarCampByName";
    public static final String CAMPANHA_SAVE = "/guardarCamp";
    public static final String CAMPANHA_UPDATE = "/actualizarCamp/{id}";
    public static final String CAMPANHA_DELETE = "/eliminarCamp/{id}";

    // Definición de las rutas de la API para las operaciones relacionadas con Usuarios
    public static final String USUARIO_BASE_PATH = "/Usuarios";
    public static final String USUARIO_GET_ALL = "/verUsuarios";
    public static final String USUARIO_SEARCH = "/buscarUsuario/{id}";
    public static final String USUARIO_SEARCH_BY_NOMBRE = "/buscarUsuarioNombre/{nombre}";
    public static final String USUARIO_SEARCH_BY_LOGIN = "/Login/{nombre}/{contrasenha}";
    public static final String USUARIO_SAVE = "/guardarUsuario";
    public static final String USUARIO_UPDATE = "/actualizarUsuario/{id}";
    public static final String USUARIO_DELETE = "/eliminarUsuario/{id}";

    // Definición de las rutas de la API para las operaciones relacionadas con Personajes
    public static final String PERSONAJE_BASE_PATH = "/Personajes";
    public static final String PERSONAJE_GET_ALL = "/verPersonajes";
    public static final String PERSONAJE_SEARCH = "/buscarPersonaje/{usuarioId}/{personajeId}";
    public static final String PERSONAJE_SEARCH_BY_USER = "/buscarPersonajesPorUsuario/{usuarioId}";
    public static final String PERSONAJE_SEARCH_BY_NAME = "/buscarPersonajeByName";
    public static final String PERSONAJE_SEARCH_BY_ID = "/buscarPersonajeById/{personajeId}";
    public static final String PERSONAJE_SAVE = "/guardarPersonaje";
    public static final String PERSONAJE_UPDATE = "/actualizarPersonaje";
    public static final String PERSONAJE_DELETE = "/eliminarPersonaje/{usuarioId}/{personajeId}";

    // Definición de las rutas de la API para las operaciones relacionadas con la relación Arma-Personaje
    public static final String ARMA_PERSONAJE_BASE_PATH = "/ArmaPersonaje";
    public static final String ARMA_PERSONAJE_GET_ALL = "/verArmaPersonajes";
    public static final String ARMA_PERSONAJE_SAVE = "/guardarArmaPersonaje";
    public static final String ARMA_PERSONAJE_UPDATE = "/actualizarArmaPersonaje";
    public static final String ARMA_PERSONAJE_SEARCH_BY_IDS = "/buscarArma/{armaId}/{personajeId}/{usuarioId}";
    public static final String ARMA_PERSONAJE_SEARCH_BY_PERSONAJE = "/buscarArmaPersonajePorPersonaje/{personajeId}";
    public static final String ARMA_PERSONAJE_DELETE = "/eliminarArmaPersonaje/{armaId}/{personajeId}/{usuarioId}";


}
