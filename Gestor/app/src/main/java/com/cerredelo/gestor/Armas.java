package com.cerredelo.gestor;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
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

public class Armas extends AppCompatActivity {
    private ArmaControlador armaControlador;
    private ListView listViewArmas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_armas);

        armaControlador = new ArmaControlador(this);

        // Obtener referencia a la lista de armas
        listViewArmas = findViewById(R.id.listaArmas);


        // Configurar botón para volver
        Button btnVolver = findViewById(R.id.btnVolver);
        btnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Armas.this, PantallaPrincipal.class);
                startActivity(intent);
                finish();
            }
        });

        // Cargar la lista de armas correspondientes al personaje
        cargarListaArmas();
    }

    // Método para cargar la lista de armas correspondientes al personaje
    private void cargarListaArmas() {
        armaControlador.cargarListaArmas( new ArmaControlador.OnListaArmasCargadaListener() {
            @Override
            public void onListaArmasCargada(List<Arma> armas) {
                Log.d("Response", armas.toString());
                actualizarListView(armas);
            }
            @Override
            public void onError(String mensajeError) {
                Toast.makeText(Armas.this, mensajeError, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void actualizarListView(List<Arma> armas) {
        ArrayAdapter<Arma> adapter = new ArrayAdapter<Arma>(this, android.R.layout.simple_list_item_1, armas) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                if (convertView == null) {
                    convertView = getLayoutInflater().inflate(R.layout.activity_item_arma, parent, false);
                }

                TextView nombreTextView = convertView.findViewById(R.id.nombreArma);
                final Arma arma = getItem(position);
                nombreTextView.setText(arma.getNombre());

                Button btnVer = convertView.findViewById(R.id.btnVerArma);
                btnVer.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Lógica para ver el detalle del arma
                        Intent intent = new Intent(Armas.this, VerArma.class);
                        intent.putExtra("armaId", arma.getId());
                        startActivity(intent);
                    }
                });

                return convertView;
            }
        };

        listViewArmas.setAdapter(adapter);
    }
}
