package com.cerredelo.gestor;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class PantallaPrincipal extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_principal);

        // Obtener referencias a las vistas
        TextView userNameTextView = findViewById(R.id.user_name);
        ImageView userIconImageView = findViewById(R.id.user_icon);
        Spinner dropdownMenu = findViewById(R.id.menuusuario);

        // Configurar el menú desplegable
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.opcionesPerfil, android.R.layout.simple_spinner_dropdown_item);
        dropdownMenu.setAdapter(adapter);

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
    }

    private void cerrarSesion() {
        // Aquí puedes agregar cualquier lógica necesaria para cerrar la sesión
        // Por ejemplo, limpiar datos de sesión o cerrar la sesión en el servidor

        // Luego, navega de vuelta a la pantalla de inicio de sesión
        Intent intent = new Intent(PantallaPrincipal.this, Login.class);
        startActivity(intent);
        finish(); // Cerrar la actividad actual
    }
}
