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

import java.util.List;

import Clases.Arma;
import Clases.ArmaControlador;
import Clases.ArmaPersonaje;
import Clases.ArmaPersonajeControlador;

public class ArmasPersonaje extends AppCompatActivity {
    private ArmaPersonajeControlador armaPersonajeControlador;
    private ListView listViewArmasPersonaje;
    private long personajeId;
    private String personajeNombre, armaNombre;

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
                /*
                Intent intent = new Intent(ArmasPersonaje.this, NuevaArmaPersonaje.class);
                intent.putExtra("personajeId", personajeId);
                startActivity(intent);
                */
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
        ArrayAdapter<ArmaPersonaje> adapter = new ArrayAdapter<ArmaPersonaje>(this, android.R.layout.simple_list_item_1, armasPersonaje) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                if (convertView == null) {
                    convertView = getLayoutInflater().inflate(R.layout.activity_item_arma_personaje, parent, false);
                }

                TextView nombreTextView = convertView.findViewById(R.id.nombreArmaPersonaje);
                Button btnVer = convertView.findViewById(R.id.btnVerArmaPersonaje);
                Button btnEditar = convertView.findViewById(R.id.btnEditarArmaPersonaje);
                Button btnEliminar = convertView.findViewById(R.id.btnEliminarArmaPersonaje);

                final ArmaPersonaje armaPersonaje = getItem(position);
                //busacar nombre del arma
                //no funciona bien
                buscarArma(armaPersonaje.getArmaId());
                nombreTextView.setText(armaNombre);

                btnVer.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Lógica para ver la información detallada del arma
                        Intent intent = new Intent(ArmasPersonaje.this, VerArmaPersonaje.class);
                        intent.putExtra("personajeId",personajeId);
                        intent.putExtra("armaId", armaPersonaje.getArmaId());
                        intent.putExtra("personajeNombre",personajeNombre);
                        intent.putExtra("armaNombre",armaNombre);
                        startActivity(intent);
                    }
                });

                btnEditar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Lógica para editar el arma del personaje
                        // (Podrías implementarlo si es necesario)
                    }
                });

                btnEliminar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Lógica para eliminar el arma del personaje
                        mostrarDialogoEliminar(armaPersonaje);
                    }
                });

                return convertView;
            }
        };

        listViewArmasPersonaje.setAdapter(adapter);
    }

    private void buscarArma(Long armaId) {
        ArmaControlador armaControlador = new ArmaControlador(this);
        armaControlador.buscarArma(armaId, new ArmaControlador.OnArmaEncontradaListener() {

            @Override
            public void onArmaEncontrada(Arma arma) {
                armaNombre=arma.getNombre();
            }

            @Override
            public void onError(String mensajeError) {
                // Manejar el error si no se puede encontrar el personaje
                Toast.makeText(ArmasPersonaje.this, mensajeError, Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Método para mostrar el diálogo de confirmación de eliminación
    private void mostrarDialogoEliminar(final ArmaPersonaje armaPersonaje) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Eliminar Arma del Personaje");
        //implementar buscar nombre
        builder.setMessage("¿Estás seguro de que quieres eliminar el arma '" + armaPersonaje.getArmaId() + "' del personaje?");

        // Agregar el botón "Sí" para confirmar la eliminación
        builder.setPositiveButton("Sí", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Lógica para eliminar el arma del personaje
                eliminarArmaPersonaje(armaPersonaje);
            }
        });

        // Agregar el botón "Cancelar" para cancelar la eliminación
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Cerrar el diálogo y no hacer nada
                dialog.dismiss();
            }
        });

        // Mostrar el diálogo
        builder.show();
    }

    // Método para eliminar el arma del personaje
    private void eliminarArmaPersonaje(final ArmaPersonaje armaPersonaje) {
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

