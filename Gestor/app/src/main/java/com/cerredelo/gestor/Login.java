package com.cerredelo.gestor;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.ClientError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import Clases.Usuario;
import Helper.Variables;

public class Login extends AppCompatActivity {

    private EditText usernameEditText;
    private EditText passwordEditText;
    private Button loginButton, configButton;
    private Button registerButton;
    private RequestQueue queue;

    public static String IP;
    public static String idioma;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        cargarPreferences();

        usernameEditText = findViewById(R.id.username);
        passwordEditText = findViewById(R.id.password);
        loginButton = findViewById(R.id.login_button);
        registerButton = findViewById(R.id.register_button);
        configButton = findViewById(R.id.config_button);
        queue = Volley.newRequestQueue(this);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Obtener los datos de usuario y contraseña ingresados por el usuario
                String username = usernameEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                // Realizar la solicitud a la API para verificar las credenciales del usuario
                loginUser(username, password);
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navegar a la actividad de registro
                Intent intent = new Intent(Login.this, Registro.class);
                startActivity(intent);
                finish();
            }
        });


        configButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navegar a la actividad de ConfigInicial
                Intent intent2 = new Intent(Login.this, ConfigInicial.class);
                startActivity(intent2);
                finish();
            }
        });

    }
    private void cargarPreferences() {
        SharedPreferences sharedPreferences = getSharedPreferences("ConfigPreferences", Context.MODE_PRIVATE);
        idioma = sharedPreferences.getString("Idioma", "Español");
        IP = sharedPreferences.getString("IP", "http://192.168.56.1:8080/");
    }

    private void loginUser(String username, String password) {
        // URL del endpoint de tu API para verificar las credenciales del usuario
        String loginUrl = IP+ "Usuarios/Login/" + username + "/" + password;

        // Crear la solicitud JSON para realizar la verificación de las credenciales del usuario
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, loginUrl, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            // Comprobar si la respuesta contiene datos pertinentes
                            if (response.has("id")) {
                                // Usuario válido, permitir que el usuario inicie sesión y navegue a la pantalla principal
                                Toast.makeText(Login.this, "Inicio de sesión exitoso", Toast.LENGTH_SHORT).show();
                                // Convertir el JSONObject de la respuesta en un objeto Usuario
                                Usuario login = new Usuario(response);
                                // Guardar los datos del usuario en SharedPreferences
                                saveUserData(login);
                                Intent intent3 = new Intent(Login.this, PantallaPrincipal.class);
                                startActivity(intent3);
                                finish(); // Cierra la actividad de inicio de sesión para evitar que el usuario regrese con el botón "Atrás"
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            // Error al procesar la respuesta JSON
                            Toast.makeText(Login.this, "Error de procesamiento de datos", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Manejar los errores de la solicitud aquí
                        if (error instanceof ClientError) {
                            // Error de cliente (por ejemplo, 404 Not Found), mostrar el mensaje adecuado al usuario
                            Toast.makeText(Login.this, "Nombre de usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show();
                        } else if (error instanceof NoConnectionError) {
                            Toast.makeText(Login.this, "No se puede conectar al servidor", Toast.LENGTH_SHORT).show();
                        } else if (error instanceof TimeoutError) {
                            Toast.makeText(Login.this, "Tiempo de espera agotado", Toast.LENGTH_SHORT).show();
                        } else if (error instanceof ServerError || error instanceof NetworkError) {
                            Toast.makeText(Login.this, "Error del servidor, inténtelo de nuevo más tarde", Toast.LENGTH_SHORT).show();
                        } else if (error instanceof AuthFailureError) {
                            Toast.makeText(Login.this, "Error de autenticación", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(Login.this, "Error desconocido", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        // Agregar la solicitud a la cola de solicitudes
        queue.add(request);
    }

    private void saveUserData(Usuario usuario) {
        // Obtener el objeto SharedPreferences
        SharedPreferences sharedPref = getSharedPreferences("UserPref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();

        // Guardar los datos del usuario en SharedPreferences
        editor.putLong("userId", usuario.getId());
        editor.putString("userName", usuario.getNombre());
        editor.putString("userContrasenha", usuario.getContrasenha());
        editor.putString("userEstado", usuario.getEstado());
        String fotoBase64 = Base64.encodeToString(usuario.getFoto(), Base64.DEFAULT);
        editor.putString("userFoto", fotoBase64);
        editor.apply();
    }

}
