package com.cerredelo.gestor;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.cerredelo.gestor.item.ArmasPersonajeAdapter;

import java.util.List;

import Clases.Arma;
import Clases.ArmaControlador;
import Clases.ArmaPersonaje;
import Clases.ArmaPersonajeControlador;

public class ArmasPersonaje extends AppCompatActivity {
    private ArmaPersonajeControlador armaPersonajeControlador;
    private ListView listViewArmasPersonaje;
    private long personajeId;
    public String personajeNombre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_armas_personaje);

        armaPersonajeControlador = new ArmaPersonajeControlador(this);

        // Obtener referencia a la lista de armas del personaje
        listViewArmasPersonaje = findViewById(R.id.listaArmaPersonaje);

        // Obtener el ID del personaje almacenado en SharedPreferences
        Intent intent = getIntent();
        if (intent != null) {
            Long perId = intent.getLongExtra("personajeId", 0);
            personajeNombre = intent.getStringExtra("personajeNombre");
            if (perId != 0) {
                // Buscar y mostrar los datos del personaje
                personajeId = perId;
            } else {
                Toast.makeText(ArmasPersonaje.this, "Error: personaje no encontrado", Toast.LENGTH_SHORT).show();
                Intent intent2 = new Intent(ArmasPersonaje.this, Personajes.class);
                startActivity(intent2);
                finish();
            }
        }

        // Configurar botón para agregar una nueva arma al personaje
        Button btnNuevaArmaPersonaje = findViewById(R.id.btnNuevaArmaPersonaje);
        btnNuevaArmaPersonaje.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Lógica para abrir la actividad para crear una nueva arma para el personaje
                Intent intent = new Intent(ArmasPersonaje.this, NuevaArmaPersonaje.class);
                intent.putExtra("personajeId", personajeId);
                intent.putExtra("personajeNombre", personajeNombre);
                startActivity(intent);
                finish();
            }
        });

        // Configurar botón para volver
        Button btnVolver = findViewById(R.id.btnVolver);
        btnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Finalizar esta actividad y volver al perfil del personaje
                Intent intent = new Intent(ArmasPersonaje.this, VerPersonaje.class);
                intent.putExtra("personajeId", personajeId);
                startActivity(intent);
                finish();
            }
        });

        // Cargar la lista de armas del personaje
        cargarListaArmasPersonaje();
    }

    // Método para cargar la lista de armas del personaje
    private void cargarListaArmasPersonaje() {
        armaPersonajeControlador.cargarListaArmasPersonaje(personajeId, new ArmaPersonajeControlador.OnListaArmasPersonajeCargadaListener() {
            @Override
            public void onListaArmasPersonajeCargada(List<ArmaPersonaje> armasPersonaje) {
                actualizarListView(armasPersonaje);
            }

            @Override
            public void onError(String mensajeError) {
                Toast.makeText(ArmasPersonaje.this, mensajeError, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void actualizarListView(List<ArmaPersonaje> armasPersonaje) {
        ArmasPersonajeAdapter adapter = new ArmasPersonajeAdapter(this, armasPersonaje);
        listViewArmasPersonaje.setAdapter(adapter);
    }



    // Método para eliminar el arma del personaje
    public void eliminarArmaPersonaje(final ArmaPersonaje armaPersonaje) {
        armaPersonajeControlador.eliminarArmaPersonaje(armaPersonaje.getArmaId(), armaPersonaje.getPersonajeId(), armaPersonaje.getUsuarioId(), new ArmaPersonajeControlador.OnResponseListener() {
            @Override
            public void onSuccess(String mensaje) {
                Toast.makeText(ArmasPersonaje.this, mensaje, Toast.LENGTH_SHORT).show();
                cargarListaArmasPersonaje();
            }

            @Override
            public void onError(String mensajeError) {
                // Mostrar mensaje de error al eliminar el arma del personaje
                Toast.makeText(ArmasPersonaje.this, mensajeError, Toast.LENGTH_SHORT).show();
            }
        });
    }
}

