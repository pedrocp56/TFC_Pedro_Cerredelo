package com.cerredelo.gestor;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.List;

import Clases.Arma;
import Clases.ArmaControlador;
import Clases.ArmaPersonaje;
import Clases.ArmaPersonajeControlador;
import Clases.Personaje;
import Clases.PersonajeControlador;

public class NuevaArmaPersonaje extends AppCompatActivity {

    private EditText txtAtaque, txtBoni;
    private CheckBox chkCompetencia;
    private TextView txtNombrePersonaje;
    Long personajeId, usuarioId;
    String personajeNombre;
    Spinner spinnerArmas;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_nueva_arma_personaje);

        // Inicializar los EditTexts, CheckBox y TextView
        txtAtaque = findViewById(R.id.txtAtaque);
        txtBoni = findViewById(R.id.txtBoni);
        chkCompetencia = findViewById(R.id.chkCompetencia);
        txtNombrePersonaje = findViewById(R.id.txtNombrePersonaje);
        spinnerArmas = findViewById(R.id.spinnerArmas);

        // Configurar el botón "Volver"
        Button btnVolver = findViewById(R.id.btnVolver);
        btnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Agrega aquí la lógica para volver a la actividad anterior
                Intent intent = new Intent(NuevaArmaPersonaje.this, ArmasPersonaje.class);
                intent.putExtra("personajeId", personajeId);
                intent.putExtra("personajeNombre", personajeNombre);
                startActivity(intent);
                finish(); // Cierra esta actividad y vuelve a la actividad anterior en la pila
            }
        });

        // Configurar el botón "Confirmar"
        Button btnConfirmar = findViewById(R.id.btnConfirmar);
        btnConfirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Lógica para confirmar la creacion de arma
                crearArmaPersonaje();
            }
        });

        // Obtener los datos del Intent
        Intent intent = getIntent();
        if (intent != null) {
            personajeId = intent.getLongExtra("personajeId", 0);
            personajeNombre = intent.getStringExtra("personajeNombre");
            txtNombrePersonaje.setText(personajeNombre);
        }
        cargarDatosUsuario();

        // Obtener la lista de armas
        obtenerListaArmas();
    }

    private void obtenerListaArmas() {
        ArmaControlador armaControlador = new ArmaControlador(this);
        armaControlador.cargarListaArmas(new ArmaControlador.OnListaArmasCargadaListener() {
            @Override
            public void onListaArmasCargada(List<Arma> armas) {
                // Crear un ArrayAdapter personalizado usando la lista de objetos Arma
                ArrayAdapter<Arma> adapter = new ArrayAdapter<Arma>(NuevaArmaPersonaje.this, android.R.layout.simple_spinner_item, armas) {
                    @Override
                    public View getView(int position, View convertView, ViewGroup parent) {
                        // Inflate the layout for each list row
                        if (convertView == null) {
                            convertView = LayoutInflater.from(getContext()).inflate(android.R.layout.simple_spinner_item, parent, false);
                        }

                        // Get the data item for this position
                        Arma arma = getItem(position);

                        // Set the name of the Arma as the text for the Spinner item
                        TextView textView = convertView.findViewById(android.R.id.text1);
                        textView.setText(arma.getNombre());

                        return convertView;
                    }

                    @Override
                    public View getDropDownView(int position, View convertView, ViewGroup parent) {
                        return getView(position, convertView, parent);
                    }
                };

                // Especificar el diseño del dropdown del Spinner
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                // Asociar el ArrayAdapter personalizado con el Spinner
                spinnerArmas.setAdapter(adapter);
            }

            @Override
            public void onError(String mensajeError) {
                Toast.makeText(NuevaArmaPersonaje.this, mensajeError, Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void cargarDatosUsuario() {
        // Obtener el objeto SharedPreferences
        SharedPreferences sharedPref = getSharedPreferences("UserPref", Context.MODE_PRIVATE);
        // Recuperar los datos del usuario
        usuarioId = sharedPref.getLong("userId", -1);
    }

    private void crearArmaPersonaje() {
        ArmaPersonaje armaPersonaje = new ArmaPersonaje();
        armaPersonaje.setAtaqueTotal(Integer.parseInt(txtAtaque.getText().toString()));
        armaPersonaje.setBonificacionAdicional(Integer.parseInt(txtBoni.getText().toString()));
        armaPersonaje.setCompetencia(chkCompetencia.isChecked());
        Arma armaSeleccionada = (Arma) spinnerArmas.getSelectedItem();
        Long armaId = armaSeleccionada.getId();
        ;

        ArmaPersonajeControlador armaPersonajeControlador = new ArmaPersonajeControlador(this);
        armaPersonajeControlador.crearArmaPersonaje(armaId,personajeId,usuarioId,armaPersonaje, new ArmaPersonajeControlador.OnResponseListener() {

            @Override
            public void onSuccess(String message) {
                Toast.makeText(NuevaArmaPersonaje.this, message, Toast.LENGTH_LONG).show();
                Intent intent = new Intent(NuevaArmaPersonaje.this, ArmasPersonaje.class);
                intent.putExtra("personajeId", personajeId);
                intent.putExtra("personajeNombre", personajeNombre);
                startActivity(intent);
                finish(); // Cierra esta actividad y vuelve a la actividad anterior en la pila
            }

            @Override
            public void onError(String mensajeError) {
                Toast.makeText(NuevaArmaPersonaje.this, mensajeError, Toast.LENGTH_LONG).show();
                Intent intent = new Intent(NuevaArmaPersonaje.this, ArmasPersonaje.class);
                intent.putExtra("personajeId", personajeId);
                intent.putExtra("personajeNombre", personajeNombre);
                startActivity(intent);
                finish(); // Cierra esta actividad y vuelve a la actividad anterior en la pila
            }
        });
    }
}
