package com.cerredelo.gestor;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Base64;

import java.util.Arrays;

public class ControladorPref {

    //nombres
    private static final String preferencias = "preferencias";
    private static final String Idioma = "Idioma";
    private static final String ip = "IP";
    private static final String userId = "userId";
    private static final String userName = "userName";
    private static final String userFoto = "userFoto";
    private static final String userEstado = "userEstado";

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

    public static void guardarUsuarioFoto(Context contexto, byte[] foto) {
        SharedPreferences sharedPreferences = contexto.getSharedPreferences(preferencias, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if (Arrays.equals(foto, new byte[]{110, 117, 108, 108})){
            editor.putString(userFoto, null);
        }else {
            String fotoStr = Base64.encodeToString(foto, Base64.DEFAULT);
            editor.putString(userFoto, fotoStr);
        }
        editor.apply();
    }

    public static byte[] obtenerUsuarioFoto(Context contexto) {
        SharedPreferences sharedPreferences = contexto.getSharedPreferences(preferencias, Context.MODE_PRIVATE);
        String fotoStr = sharedPreferences.getString(userFoto, null);
        if (fotoStr != null) {
            return Base64.decode(fotoStr, Base64.DEFAULT);
        } else {
            return null;
        }
    }

    public static void guardarUsuarioEstado(Context contexto, String estado) {
        SharedPreferences sharedPreferences = contexto.getSharedPreferences(preferencias, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(userEstado, estado);
        editor.apply();
    }

    public static String obtenerUsuarioEstado(Context contexto) {
        SharedPreferences sharedPreferences = contexto.getSharedPreferences(preferencias, Context.MODE_PRIVATE);
        String estado = sharedPreferences.getString(userEstado, "Mondongo");
        return estado;
    }

}
