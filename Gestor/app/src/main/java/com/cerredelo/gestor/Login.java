package com.cerredelo.gestor;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import Clases.Usuario;
import Clases.UsuarioControlador;

public class Login extends AppCompatActivity {

    private EditText usernameEditText;
    private EditText passwordEditText;
    private Button loginButton, configButton;
    private Button registerButton;
    public static String idioma;
    private UsuarioControlador usuarioControlador;
    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        usuarioControlador = new UsuarioControlador(Login.this);

        cargarPreferences();

        usernameEditText = findViewById(R.id.username);
        passwordEditText = findViewById(R.id.password);
        loginButton = findViewById(R.id.login_button);
        registerButton = findViewById(R.id.register_button);
        configButton = findViewById(R.id.config_button);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Obtener los datos de usuario y contraseña ingresados por el usuario
                String username = usernameEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                // Realizar la solicitud a la API para verificar las credenciales del usuario
                usuarioControlador.loginUser(username, password, new UsuarioControlador.OnLoginListener() {
                    @Override
                    public void onLoginSuccess(Usuario usuario) {
                        mediaPlayer = MediaPlayer.create(Login.this, R.raw.login);
                        mediaPlayer.setVolume(10.f, 1.0f);
                        mediaPlayer.start();
                        Toast.makeText(Login.this, "Inicio de sesión exitoso", Toast.LENGTH_SHORT).show();
                        // Guardar los datos del usuario en SharedPreferences
                        saveUserData(usuario);
                        Intent intent3 = new Intent(Login.this, PantallaPrincipal.class);
                        startActivity(intent3);
                        finish(); // Cierra la actividad de inicio de sesión para evitar que el usuario regrese con el botón "Atrás"
                    }

                    @Override
                    public void onLoginError(String mensajeError) {
                        mediaPlayer = MediaPlayer.create(Login.this, R.raw.loginerror);
                        mediaPlayer.setVolume(1.0f, 1.0f);
                        mediaPlayer.start();
                        Toast.makeText(Login.this, mensajeError, Toast.LENGTH_SHORT).show();
                    }
                });
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
    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Liberar los recursos del MediaPlayer al destruir la actividad
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    private void cargarPreferences() {
        SharedPreferences sharedPreferences = getSharedPreferences("ConfigPreferences", Context.MODE_PRIVATE);
        idioma = ControladorPref.obtenerIdioma(Login.this);
    }

    private void saveUserData(Usuario usuario) {
        ControladorPref.guardarUsuarioID(Login.this, usuario.getId());
        ControladorPref.guardarUsuarioNombre(Login.this, usuario.getNombre());
        ControladorPref.guardarUsuarioEstado(Login.this, usuario.getEstado());
        ControladorPref.guardarUsuarioFoto(Login.this, usuario.getFoto());
    }
}

