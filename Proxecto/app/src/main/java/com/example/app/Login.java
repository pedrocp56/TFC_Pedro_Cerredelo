package com.example.app;

import android.os.Bundle;
import android.util.Log;


import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Login extends AppCompatActivity {

    private static final String TAG = "Login";
    private ApiRequest apiRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        apiRequest = ApiRequest.getInstance(this);

        // Realizar la solicitud con ApiRequest
        String url = "http://localhost:8080/Usuarios/verUsuarios";

        apiRequest.performRequest(url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Procesar la respuesta
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                String nombre = jsonObject.getString("nombre");
                                String contrasenha = jsonObject.getString("contrasenha");
                                // Mostrar los usuarios por consola
                                Log.d(TAG, "Usuario #" + i + ": Nombre: " + nombre + ", Contraseña: " + contrasenha);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Manejar errores de la solicitud
                        Log.e(TAG, "Error en la solicitud: " + error.getMessage());
                    }
                });
    }
}
