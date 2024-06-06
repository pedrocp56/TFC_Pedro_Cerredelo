package com.cerredelo.gestor;

import android.content.Context;
import android.content.SharedPreferences;

public class ControladorPref {

    //nombres
    private static final String preferencias = "preferencias";
    private static final String Idioma = "Idioma";
    private static final String ip = "IP";
    private static final String userId = "userId";
    private static final String userName = "userName";
    private static final String userFoto = "userFoto";

    public static void guardarIP(Context contexto, String IP) {
        SharedPreferences sharedPreferences = contexto.getSharedPreferences(preferencias, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(ip, IP);
        editor.apply();
    }

    public static String obtenerIP(Context contexto) {
        SharedPreferences sharedPreferences = contexto.getSharedPreferences(preferencias, Context.MODE_PRIVATE);
        String IP = sharedPreferences.getString(ip, "http://192.168.1.33:8080/");
        return IP;
    }

    public static void guardarIdioma(Context contexto, String Idioma) {
        SharedPreferences sharedPreferences = contexto.getSharedPreferences(preferencias, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(Idioma, Idioma);
        editor.apply();
    }

    public static String obtenerIdioma(Context contexto) {
        SharedPreferences sharedPreferences = contexto.getSharedPreferences(preferencias, Context.MODE_PRIVATE);
        String idioma = sharedPreferences.getString(Idioma, "es");
        return idioma;
    }

    public static void guardarUsuarioID(Context contexto, Long id) {
        SharedPreferences sharedPreferences = contexto.getSharedPreferences(preferencias, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putLong(userId, id);
        editor.apply();
    }

    public static Long obtenerUsuarioID(Context contexto) {
        SharedPreferences sharedPreferences = contexto.getSharedPreferences(preferencias, Context.MODE_PRIVATE);
        Long id = sharedPreferences.getLong(userId, 0);
        return id;
    }

    public static void guardarUsuarioNombre(Context contexto, String nombre) {
        SharedPreferences sharedPreferences = contexto.getSharedPreferences(preferencias, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(userName, nombre);
        editor.apply();
    }

    public static String obtenerUsuarioNombre(Context contexto) {
        SharedPreferences sharedPreferences = contexto.getSharedPreferences(preferencias, Context.MODE_PRIVATE);
        String nombre = sharedPreferences.getString(userName, "Mondongo");
        return nombre;
    }

    public static void guardarUsuarioFoto(Context contexto, String foto) {
        SharedPreferences sharedPreferences = contexto.getSharedPreferences(preferencias, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(userFoto, foto);
        editor.apply();
    }

    public static String obtenerUsuarioFoto(Context contexto) {
        SharedPreferences sharedPreferences = contexto.getSharedPreferences(preferencias, Context.MODE_PRIVATE);
        String foto = sharedPreferences.getString(userFoto, null);
        return foto;
    }

}
