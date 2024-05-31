package com.cerredelo.gestor;

import static Clases.ImagenUtils.base64ToBitmap;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class PantallaPrincipal extends AppCompatActivity {
    private TextView userNameTextView;
    private ImageView userIconImageView;
    private Spinner dropdownMenu;
    private Long ID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_principal);

        // Obtener referencias a las vistas
        userNameTextView = findViewById(R.id.user_name);
        userIconImageView = findViewById(R.id.user_icon);
        dropdownMenu = findViewById(R.id.menuusuario);
        Button campaignsButton = findViewById(R.id.campaigns_button);
        Button charactersButton = findViewById(R.id.characters_button);

        // Configurar el menú desplegable
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.opcionesPerfil, android.R.layout.simple_spinner_dropdown_item);
        dropdownMenu.setAdapter(adapter);

        cargarDatosUsuario();
        dropdownMenu.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Manejar la selección del elemento del menú aquí
                switch (position) {
                    case 0:
                        // Ajustes: agregar la lógica necesaria para abrir la pantalla de ajustes
                        break;
                    case 1:
                        // Perfil: agregar la lógica necesaria para abrir la pantalla de perfil
                        Intent intent = new Intent(PantallaPrincipal.this, Perfil.class);
                        startActivity(intent);
                        finish();
                        break;
                    case 2:
                        // Cerrar Sesión: agregar la lógica necesaria para cerrar la sesión
                        cerrarSesion();
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Opcional: manejar caso en que no se selecciona nada
            }
        });

// Configurar el clic en el botón "Campañas"
        campaignsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Abrir la actividad de Campañas
                Intent intent = new Intent(PantallaPrincipal.this, Campanhas.class);
                startActivity(intent);
            }
        });

// Configurar el clic en el botón "Personajes"
        charactersButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Abrir la actividad de Personajes
                Intent intent = new Intent(PantallaPrincipal.this, Personajes.class);
                startActivity(intent);
            }
        });
    }


    private void cerrarSesion() {
        // Aquí puedes agregar cualquier lógica necesaria para cerrar la sesión
        // Por ejemplo, limpiar datos de sesión o cerrar la sesión en el servidor

        // Luego, navega de vuelta a la pantalla de inicio de sesión
        Intent intent = new Intent(PantallaPrincipal.this, Login.class);
        startActivity(intent);
        finish(); // Cerrar la actividad actual
    }
    private void cargarDatosUsuario() {
        // Obtener el objeto SharedPreferences
        SharedPreferences sharedPref = getSharedPreferences("UserPref", Context.MODE_PRIVATE);

        // Recuperar los datos del usuario
        long userId = sharedPref.getLong("userId", -1);
        String userName = sharedPref.getString("userName", "");
        String userEstado = sharedPref.getString("userEstado", "");
        String userFoto = sharedPref.getString("userFoto", "");

        // Asignar los datos recuperados a las vistas
        userNameTextView.setText(userName);
        ID=userId;

        // Decodificar la imagen de Base64 a Bitmap
        if (!userFoto.isEmpty()) {
            userIconImageView.setImageBitmap(base64ToBitmap(userFoto));
        }
    }
}
