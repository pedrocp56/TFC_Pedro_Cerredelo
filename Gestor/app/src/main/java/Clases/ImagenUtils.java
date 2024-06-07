package Clases;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;

import java.util.Base64;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.io.ByteArrayOutputStream;
import java.lang.reflect.Type;

public class ImagenUtils implements JsonSerializer<byte[]>, JsonDeserializer<byte[]> {

    @Override
    public JsonElement serialize(byte[] src, Type typeOfSrc, JsonSerializationContext context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            return new JsonPrimitive(Base64.getEncoder().encodeToString(src));
        }
        return null;
    }

    @Override
    public byte[] deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            return Base64.getDecoder().decode(json.getAsString());
        }
        return new byte[0];
    }

    // Método para convertir un Bitmap a un array de bytes
    public static byte[] bitmapToByteArray(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, byteArrayOutputStream); // Ajustar calidad a 50%
        return byteArrayOutputStream.toByteArray();
    }

    // Método para convertir una cadena Base64 a un Bitmap
    public static Bitmap base64ToBitmap(String base64String) {
        // Decodificar la cadena Base64 en un byte array
        byte[] decodedString = new byte[0];
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            decodedString = Base64.getDecoder().decode(base64String);
        }
        // Convertir el byte array a un Bitmap
        return BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
    }
    public static Bitmap byteArrayToBitmap(byte[] byteArray) {
        return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
    }

}
