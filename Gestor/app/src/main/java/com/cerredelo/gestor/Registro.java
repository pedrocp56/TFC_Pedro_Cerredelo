package com.cerredelo.gestor;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.util.Log;


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

public class Registro extends AppCompatActivity {

    private EditText usernameEditText;
    private EditText passwordEditText;
    private Button registerButton;
    private RequestQueue queue;
    private Button backToLoginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        // Inicializar las vistas y la cola de solicitudes
        usernameEditText = findViewById(R.id.register_username);
        passwordEditText = findViewById(R.id.register_password);
        registerButton = findViewById(R.id.register_confirm_button);
        backToLoginButton = findViewById(R.id.back_to_login_button);
        queue = Volley.newRequestQueue(this);

        // Configurar el botón "Volver a Inicio" para ir a la pantalla de inicio de sesión
        backToLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navegar a la pantalla de inicio de sesión (Login)
                Intent intent = new Intent(Registro.this, Login.class);
                startActivity(intent);
                finish(); // Cerrar la actividad actual
            }
        });

        // Configurar el botón de registro para manejar el clic del usuario
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Obtener los datos de usuario y contraseña ingresados por el usuario
                String username = usernameEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                // Realizar la solicitud a la API para registrar un nuevo usuario
                buscarUsuario(username, password);
            }
        });
    }

    private void buscarUsuario(String username,String password) {
        // Verificar si el usuario es un correo electrónico válido
        if (!isValidEmail(username)) {
            Toast.makeText(Registro.this, "El usuario debe ser un correo electrónico válido", Toast.LENGTH_SHORT).show();
            return;
        }
        // Verificar si la contraseña tiene entre 5 y 13 caracteres
        if (password.length() < 5 || password.length() > 13) {
            Toast.makeText(Registro.this, "La contraseña debe tener entre 5 y 13 caracteres", Toast.LENGTH_SHORT).show();
            return;
        }


        // URL del endpoint de tu API para verificar si el usuario ya está registrado
        String RegistroUrl = "http://192.168.1.33:8080/Usuarios/buscarUsuarioNombre/" + username;

        // Crear la solicitud JSON para verificar si el usuario ya está registrado
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, RegistroUrl, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            // Comprobar si la respuesta contiene datos pertinentes
                            if (response.has("id")) {
                                // Usuario encontrado, mostrar un mensaje al usuario
                                Toast.makeText(Registro.this, "El usuario ya está registrado", Toast.LENGTH_SHORT).show();
                            } else {
                                // Usuario no encontrado, permitir que el usuario se registre
                                crearNuevoUsuario(username,password);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            // Error al procesar la respuesta JSON
                            Toast.makeText(Registro.this, "Error de procesamiento de datos", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Manejar los errores de la solicitud aquí
                        if (error instanceof ClientError) {
                            // Error de cliente (por ejemplo, 404 Not Found), el usuario no existe, permitir que el usuario se registre
                            crearNuevoUsuario(username,password);
                        } else if (error instanceof NoConnectionError) {
                            Toast.makeText(Registro.this, "No se puede conectar al servidor", Toast.LENGTH_SHORT).show();
                        } else if (error instanceof TimeoutError) {
                            Toast.makeText(Registro.this, "Tiempo de espera agotado", Toast.LENGTH_SHORT).show();
                        } else if (error instanceof NetworkError || error instanceof ServerError) {
                            Toast.makeText(Registro.this, "Error del servidor, inténtelo de nuevo más tarde", Toast.LENGTH_SHORT).show();
                        } else if (error instanceof AuthFailureError) {
                            Toast.makeText(Registro.this, "Error de autenticación", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(Registro.this, "Error desconocido", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        // Agregar la solicitud a la cola de solicitudes
        queue.add(request);
    }


    private void crearNuevoUsuario(String username, String password) {
        // URL del endpoint de tu API para registrar un nuevo usuario
        String registerUrl = "http://192.168.1.33:8080/Usuarios/guardarUsuario";
        // Crear un JSONObject con los datos del nuevo usuario
        JSONObject userData = new JSONObject();
        try {
            userData.put("nombre", username);
            userData.put("contrasenha", password);
            userData.put("estado","Nueva cuenta");
            userData.put("foto",null);
            // Puedes agregar más campos según los requisitos de tu API
        } catch (JSONException e) {
            e.printStackTrace();
            // Error al crear los datos del usuario
            Toast.makeText(Registro.this, "Error al crear los datos del usuario", Toast.LENGTH_SHORT).show();
            return;
        }

        // Crear la solicitud JSON para registrar un nuevo usuario
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, registerUrl, userData,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // Verificar si la respuesta indica que el usuario se ha registrado correctamente
                        try {
                            boolean success = response.getBoolean("success");
                            if (success) {
                                // Usuario registrado con éxito, mostrar un mensaje de éxito al usuario
                                Toast.makeText(Registro.this, "Usuario registrado exitosamente", Toast.LENGTH_SHORT).show();
                                // Puedes agregar lógica adicional aquí, como navegar a la pantalla de inicio de sesión
                                // Por ejemplo, navegar a la pantalla de registro
                                Intent intent = new Intent(Registro.this, PantallaPrincipal.class);
                                startActivity(intent);
                            } else {
                                // Fallo al registrar el usuario, mostrar un mensaje de error al usuario
                                Toast.makeText(Registro.this, "Fallo al registrar el usuario", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            // Error al procesar la respuesta JSON
                            Toast.makeText(Registro.this, "Error de procesamiento de datos", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Manejar los errores de la solicitud aquí
                        // Por ejemplo, mostrar un mensaje de error al usuario indicando que ocurrió un error de red
                        Toast.makeText(Registro.this, error.toString(), Toast.LENGTH_LONG).show();
                        Log.d("TAG", error.toString());
                    }
                });

        // Agregar la solicitud a la cola de solicitudes
        queue.add(request);
    }

    private boolean isValidEmail(String email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
}
