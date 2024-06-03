package com.cerredelo.gestor;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import Clases.Arma;
import Clases.ArmaControlador;
import Clases.ArmaPersonaje;
import Clases.ArmaPersonajeControlador;

public class VerArmaPersonaje extends AppCompatActivity {

    private TextView txtNombrePersonaje, txtNombreArma, txtAtaque, txtBoni, txtCompetencia;
    Long personajeId, armaId, usuarioId;
    String personajeNombre, armaNombre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_ver_arma_personaje);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Inicializar los TextViews
        txtNombrePersonaje = findViewById(R.id.txtNombrePersonaje);
        txtNombreArma = findViewById(R.id.txtNombreArma);
        txtAtaque = findViewById(R.id.txtAtaque);
        txtBoni = findViewById(R.id.txtBoni);
        txtCompetencia = findViewById(R.id.txtCompetencia);

        // Configurar el botón "Volver"
        Button btnVolver = findViewById(R.id.btnVolver);
        btnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(VerArmaPersonaje.this, ArmasPersonaje.class);
                intent.putExtra("personajeId", personajeId);
                intent.putExtra("personajeNombre", personajeNombre);
                startActivity(intent);
                finish(); // Cierra esta actividad y vuelve a la actividad anterior en la pila
            }
        });

        // Configurar el botón "Editar"
        Button btnEditar = findViewById(R.id.btnEditar);
        btnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(VerArmaPersonaje.this, EditarArmaPersonaje.class);
                intent.putExtra("armaId", armaId);
                intent.putExtra("personajeId", personajeId);
                intent.putExtra("usuarioId", usuarioId);
                intent.putExtra("personajeNombre", personajeNombre);
                intent.putExtra("armaNombre", armaNombre);
                startActivity(intent);
                finish();
            }
        });

        // Obtener el ID del personaje almacenado en SharedPreferences
        Intent intent = getIntent();
        if (intent != null) {
            personajeId = intent.getLongExtra("personajeId", 0);
            armaId = intent.getLongExtra("armaId", 0);
            personajeNombre = intent.getStringExtra("personajeNombre");
            armaNombre = intent.getStringExtra("armaNombre");

        }

        // Obtener y mostrar los datos del arma del personaje
        cargarDatosUsuario();
    }

    private void cargarDatosUsuario() {
        // Obtener el objeto SharedPreferences
        SharedPreferences sharedPref = getSharedPreferences("UserPref", Context.MODE_PRIVATE);
        // Recuperar los datos del usuario
        usuarioId = sharedPref.getLong("userId", -1);

        buscarArmapersonaje(armaId, personajeId, usuarioId);
    }

    // Método para obtener y mostrar los datos del arma del personaje
    private void buscarArmapersonaje(Long armId, Long persId, Long userId) {
        ArmaPersonajeControlador armaPersonajeControlador = new ArmaPersonajeControlador(this);
        armaPersonajeControlador.buscarArmaPorIds(armId, persId, userId, new ArmaPersonajeControlador.OnArmaEncontradaListener() {
            @Override
            public void onArmaEncontrada(ArmaPersonaje armaper) {
                mostrarArmapersonaje(armaper);
            }

            @Override
            public void onError(String mensajeError) {
                // Manejar el error si no se puede encontrar el personaje
                Toast.makeText(VerArmaPersonaje.this, mensajeError, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void mostrarArmapersonaje(ArmaPersonaje armaper) {
        txtNombrePersonaje.setText(personajeNombre);
        txtNombreArma.setText(armaNombre);
        txtAtaque.setText(String.valueOf(armaper.getAtaqueTotal()));
        txtBoni.setText(String.valueOf(armaper.getBonificacionAdicional()));
        txtCompetencia.setText(armaper.isCompetencia() ? "Sí" : "No");
    }
}
