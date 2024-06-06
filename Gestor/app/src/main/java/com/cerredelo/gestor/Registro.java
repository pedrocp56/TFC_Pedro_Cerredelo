package com.cerredelo.gestor;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Patterns;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;

import Clases.ImagenUtils;
import Clases.Usuario;
import Clases.UsuarioControlador;
import Helper.Variables;

public class Registro extends AppCompatActivity {

    private EditText usernameEditText;
    private EditText passwordEditText;
    private Button registerButton;
    private Button backToLoginButton;
    private ImageView vistaFoto;
    private ImageButton tomarFotoButton;
    private byte[] imagen;
    private UsuarioControlador usuarioControlador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        usuarioControlador = new UsuarioControlador(Registro.this);
        imagen = null;

        // Inicializar las vistas
        usernameEditText = findViewById(R.id.register_username);
        passwordEditText = findViewById(R.id.register_password);
        registerButton = findViewById(R.id.register_confirm_button);
        backToLoginButton = findViewById(R.id.back_to_login_button);
        vistaFoto = findViewById(R.id.vistaFoto);
        tomarFotoButton = findViewById(R.id.tomarFoto);

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
                                        imagen = ImagenUtils.bitmapToByteArray(bitmap);
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

        // Configurar el botón de registro
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (comprobarDatos()) {
                    String username = usernameEditText.getText().toString();
                    String password = passwordEditText.getText().toString();
                    usuarioControlador.crearNuevoUsuario(username, password,imagen, new UsuarioControlador.OnUsuarioRegistradoListener() {
                        @Override
                        public void onUsuarioRegistrado() {
                            Notificacion noti = new Notificacion();
                            noti.mostrarNotificacion(Registro.this, getString(R.string.notification_title_registro), usernameEditText.getText().toString() + getString(R.string.notification_message_registro));
                            Intent intent = new Intent(Registro.this, Login.class);
                            startActivity(intent);
                            finish();
                        }

                        @Override
                        public void onRegistroError(String mensajeError) {
                            Toast.makeText(Registro.this, mensajeError, Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    Toast.makeText(Registro.this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show();
                }
            }
        });


        // Configurar el botón "Volver al login"
        backToLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navegar a la actividad de inicio de sesión
                Intent intent = new Intent(Registro.this, Login.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private boolean comprobarDatos() {
        String username = usernameEditText.getText().toString();
        String password = passwordEditText.getText().toString();

        // Validar que el username sea un correo electrónico
        if (!isValidEmail(username)) {
            Toast.makeText(Registro.this, "Por favor, ingrese un correo electrónico válido", Toast.LENGTH_SHORT).show();
            return false;
        }

        // Validar que la contraseña tenga entre 5 y 12 caracteres alfanuméricos
        if (!isValidPassword(password)) {
            Toast.makeText(Registro.this, "La contraseña debe tener entre 5 y 13 caracteres alfanuméricos", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    // Función para validar si el username es un correo electrónico
    private boolean isValidEmail(String email) {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    // Función para validar la contraseña
    private boolean isValidPassword(String password) {
        return password.length() >= 5 && password.length() <= 13 && password.matches("[a-zA-Z0-9]+");
    }

}

