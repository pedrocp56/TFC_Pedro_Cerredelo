package com.cerredelo.gestor;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import Clases.Personaje;
import Clases.PersonajeControlador;


public class Personajes extends AppCompatActivity {
    private PersonajeControlador personajeControlador;
    private ListView listViewPersonajes;
    private long userId;
    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personajes);

        personajeControlador = new PersonajeControlador(this);

        // Obtener referencia a la lista de personajes
        listViewPersonajes = findViewById(R.id.listaPersonajes);


        // Obtener la ID del usuario almacenada en SharedPreferences
        userId=ControladorPref.obtenerUsuarioID(Personajes.this);

        // Configurar botón para agregar un nuevo personaje
        Button btnNuevoPersonaje = findViewById(R.id.btnNuevoPersonaje);
        btnNuevoPersonaje.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Agregar lógica para abrir la actividad para crear un nuevo personaje
                Intent intent = new Intent(Personajes.this, ConfigPersonaje.class);
                startActivity(intent);
                finish();
            }
        });

        // Configurar botón para volver a la pantalla principal
        Button btnVolverPantallaPrincipal = findViewById(R.id.btnVolverPantallaPrincipal);
        btnVolverPantallaPrincipal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Finalizar esta actividad y volver a la pantalla principal
                Intent intent = new Intent(Personajes.this, PantallaPrincipal.class);
                startActivity(intent);
                finish();
            }
        });

        // Cargar la lista de personajes correspondientes al usuario
        cargarListaPersonajes();
    }

    // Método para cargar la lista de personajes correspondientes al usuario
    private void cargarListaPersonajes() {
        personajeControlador.cargarListaPersonajes(userId, new PersonajeControlador.OnListaPersonajesCargadaListener() {
            @Override
            public void onListaPersonajesCargada(List<Personaje> personajes) {
                actualizarListView(personajes);
            }
            @Override
            public void onError(String mensajeError) {
                Toast.makeText(Personajes.this, mensajeError, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void actualizarListView(List<Personaje> personajes) {
        ArrayAdapter<Personaje> adapter = new ArrayAdapter<Personaje>(this, android.R.layout.simple_list_item_1, personajes) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                if (convertView == null) {
                    convertView = getLayoutInflater().inflate(R.layout.activity_item_personaje, parent, false);
                }

                TextView nombreTextView = convertView.findViewById(R.id.nombrePersonaje);
                Button btnVer = convertView.findViewById(R.id.btnVer);
                Button btnEditar = convertView.findViewById(R.id.btnEditar);
                Button btnEliminar = convertView.findViewById(R.id.btnEliminar);

                final Personaje personaje = getItem(position);
                nombreTextView.setText(personaje.getNombre());

                btnVer.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Lógica para ver el personaje
                        Intent intent = new Intent(Personajes.this, VerPersonaje.class);
                        intent.putExtra("personajeId", personaje.getPersonajeId());
                        startActivity(intent);
                        finish();
                    }
                });

                btnEditar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Lógica para editar el personaje
                        Intent intent = new Intent(Personajes.this, ConfigPersonaje.class);
                        // Pasar el objeto Personaje a la actividad ConfigPersonaje
                        intent.putExtra("personajeId", personaje.getPersonajeId());
                        startActivity(intent);
                        finish();
                    }
                });

                btnEliminar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Lógica para eliminar el personaje
                        mostrarDialogoEliminar(personaje);
                    }
                });

                return convertView;
            }
        };

        listViewPersonajes.setAdapter(adapter);
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

    // Dentro de tu clase Personajes, agrega un método para mostrar el diálogo de confirmación de eliminación
    private void mostrarDialogoEliminar(final Personaje personaje) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(Personajes.this.getString(R.string.eliminar_Personaje));
        builder.setMessage(Personajes.this.getString(R.string.mensajeEliminar_Personaje1) + personaje.getNombre() +Personajes.this.getString(R.string.mensajeEliminar_Personaje2));

        // Agregar el botón "Sí" para confirmar la eliminación
        builder.setPositiveButton(Personajes.this.getString(R.string.si), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Lógica para eliminar el personaje
                mediaPlayer = MediaPlayer.create(Personajes.this, R.raw.eliminar);
                mediaPlayer.setVolume(1.0f, 1.0f);
                mediaPlayer.start();
                eliminarPersonaje(personaje);
            }
        });

        // Agregar el botón "No" para cancelar la eliminación
        builder.setNegativeButton(Personajes.this.getString(R.string.no), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Cerrar el diálogo y no hacer nada
                dialog.dismiss();
            }
        });

        // Mostrar el diálogo
        builder.show();
    }

    // Método para eliminar el personaje
    private void eliminarPersonaje(final Personaje personaje) {
        personajeControlador.eliminarPersonaje(userId, personaje.getPersonajeId(), new PersonajeControlador.OnPersonajeEliminadoListener() {
            @Override
            public void onPersonajeEliminado(String mensaje) {
                Toast.makeText(Personajes.this, mensaje, Toast.LENGTH_SHORT).show();
                cargarListaPersonajes();
            }
            @Override
            public void onError(String mensajeError) {
                // Mostrar mensaje de error al eliminar el personaje
                Toast.makeText(Personajes.this, mensajeError, Toast.LENGTH_SHORT).show();
            }
        });
    }
}


