package Clases;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import java.io.ByteArrayOutputStream;

public class ImagenUtils {

    // Método para convertir un Bitmap a una cadena Base64
    public static String bitmapToBase64(Bitmap bitmap) {
        // Convertir la imagen a una cadena Base64
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(byteArray, Base64.DEFAULT);
    }

    // Método para convertir una cadena Base64 a un Bitmap
    public static Bitmap base64ToBitmap(String base64String) {
        // Decodificar la cadena Base64 en un byte array
        byte[] decodedString = Base64.decode(base64String, Base64.DEFAULT);
        // Convertir el byte array a un Bitmap
        return BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
    }
}
