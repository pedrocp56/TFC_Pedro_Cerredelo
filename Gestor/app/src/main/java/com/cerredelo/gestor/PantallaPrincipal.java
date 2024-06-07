package com.cerredelo.gestor;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import Clases.ImagenUtils;

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
        Button armasBoton = findViewById(R.id.armas_button);
        Button personajesBoton = findViewById(R.id.personajes_button);

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
                            break;
                        case 1:
                            Toast.makeText(PantallaPrincipal.this, "Mondongo", Toast.LENGTH_SHORT).show();
                            // Ajustes: agregar la lógica necesaria para abrir la pantalla de ajustes
                            Intent intent = new Intent(PantallaPrincipal.this, Ajustes.class);
                            startActivity(intent);
                            finish();
                            break;
                        case 2:
                            // Perfil: agregar la lógica necesaria para abrir la pantalla de perfil
                            Intent intent2 = new Intent(PantallaPrincipal.this, Perfil.class);
                            startActivity(intent2);
                            finish();
                            break;
                        case 3:
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
        armasBoton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Abrir la actividad de Campañas
                Intent intent = new Intent(PantallaPrincipal.this, Armas.class);
                startActivity(intent);
                finish();
            }
        });

// Configurar el clic en el botón "Personajes"
        personajesBoton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Abrir la actividad de Personajes
                Intent intent = new Intent(PantallaPrincipal.this, Personajes.class);
                startActivity(intent);
                finish();
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
        ID=ControladorPref.obtenerUsuarioID(PantallaPrincipal.this);
        String userName = ControladorPref.obtenerUsuarioNombre(PantallaPrincipal.this);
        byte[] foto =ControladorPref.obtenerUsuarioFoto(PantallaPrincipal.this);

        // Asignar los datos recuperados a las vistas
        userNameTextView.setText(userName);

        // Verificar foto y ponerla
        if (foto==null){

            userIconImageView.setImageResource(R.drawable.camara);
        }
        else{
            userIconImageView.setImageBitmap(ImagenUtils.byteArrayToBitmap(foto));
        }
        userIconImageView.setVisibility(View.VISIBLE);
    }
}
