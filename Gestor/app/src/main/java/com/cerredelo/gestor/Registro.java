package com.cerredelo.gestor;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;
import android.util.Log;


import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
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

import Clases.ImagenUtils;
import Clases.Usuario;

public class Registro extends AppCompatActivity {

    private EditText usernameEditText;
    private EditText passwordEditText;
    private Button registerButton;
    private Button backToLoginButton;
    private ImageView vistaFoto;
    private ImageButton tomarFotoButton;
    private RequestQueue queue;
    private ActivityResultLauncher<Intent> someActivityResultLauncher;
    private String imagen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        imagen = null;

        // Inicializar las vistas
        usernameEditText = findViewById(R.id.register_username);
        passwordEditText = findViewById(R.id.register_password);
        registerButton = findViewById(R.id.register_confirm_button);
        backToLoginButton = findViewById(R.id.back_to_login_button);
        vistaFoto = findViewById(R.id.vistaFoto);
        tomarFotoButton = findViewById(R.id.tomarFoto);

        // Inicializar la cola de solicitudes
        queue = Volley.newRequestQueue(this);

        // Configurar el ActivityResultLauncher para tomar una foto
        ActivityResultLauncher<Intent> someActivityResultLauncher =
                registerForActivityResult(
                        new ActivityResultContracts.StartActivityForResult(),
                        new ActivityResultCallback<ActivityResult>() {
                            @Override
                            public void onActivityResult(ActivityResult result) {
                                if (result.getResultCode() == Activity.RESULT_OK) {
                                    Intent data = result.getData();

                                    if (data != null) {
                                        Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                                        vistaFoto.setImageBitmap(bitmap);
                                        vistaFoto.setVisibility(View.VISIBLE);
                                        imagen= ImagenUtils.bitmapToBase64(bitmap);
                                    }
                                }

                            }
                        }
                );

        // Configurar el botón "Tomar Foto"
        tomarFotoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                someActivityResultLauncher.launch(takePictureIntent);
            }
        });

        // Configurar el botón "Volver a Inicio"
        backToLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Registro.this, Login.class);
                startActivity(intent);
                finish();
            }
        });

        // Configurar el botón de registro
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                buscarUsuario(username, password);
            }
        });
    }

    private void buscarUsuario(String username, String password) {
        if (!isValidEmail(username)) {
            Toast.makeText(Registro.this, "El usuario debe ser un correo electrónico válido", Toast.LENGTH_SHORT).show();
            return;
        }

        if (password.length() < 5 || password.length() > 13) {
            Toast.makeText(Registro.this, "La contraseña debe tener entre 5 y 13 caracteres", Toast.LENGTH_SHORT).show();
            return;
        }

        String RegistroUrl = "http://192.168.1.33:8080/Usuarios/buscarUsuarioNombre/" + username;

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, RegistroUrl, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if (response.has("id")) {
                                Toast.makeText(Registro.this, "El usuario ya está registrado", Toast.LENGTH_SHORT).show();
                            } else {
                                crearNuevoUsuario(username, password);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast.makeText(Registro.this, "Error de procesamiento de datos", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if (error instanceof ClientError) {
                            crearNuevoUsuario(username, password);
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

        queue.add(request);
    }

    private void crearNuevoUsuario(String username, String password) {
        String registerUrl = "http://192.168.1.33:8080/Usuarios/guardarUsuario";
        JSONObject userData = new JSONObject();
        try {
            userData.put("nombre", username);
            userData.put("contrasenha", password);
            userData.put("estado", "Nueva cuenta");
            userData.put("foto", imagen);
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(Registro.this, "Error al crear los datos del usuario", Toast.LENGTH_SHORT).show();
            return;
        }

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, registerUrl, userData,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            boolean success = response.getBoolean("success");
                            if (success) {
                                Toast.makeText(Registro.this, "Usuario registrado exitosamente", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(Registro.this, PantallaPrincipal.class);
                                startActivity(intent);
                            } else {
                                Toast.makeText(Registro.this, "Fallo al registrar el usuario", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(Registro.this, "Error de procesamiento de datos", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Registro.this, error.toString(), Toast.LENGTH_LONG).show();
                        Log.d("TAG", error.toString());
                    }
                });

        queue.add(request);
    }

    private boolean isValidEmail(String email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
}

