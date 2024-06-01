package com.cerredelo.gestor;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import Clases.Arma;
import Clases.ArmaControlador;
import Clases.Personaje;
import Clases.PersonajeControlador;

public class VerArma extends AppCompatActivity {
    TextView txtNombre,txtAtaque, txtDanho, txtTipo, txtArrojadiza, txtCaracteristica, txtCaracteristicas, txtFoto;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_arma);

        // Obtener referencia a los TextViews en el layout
        txtNombre = findViewById(R.id.txtNombre);
        txtAtaque = findViewById(R.id.txtAtaque);
        txtDanho = findViewById(R.id.txtDanho);
        txtTipo = findViewById(R.id.txtTipo);
        txtArrojadiza = findViewById(R.id.txtArrojadiza);
        txtCaracteristica = findViewById(R.id.txtCaracteristica);
        txtCaracteristicas = findViewById(R.id.txtCaracteristicas);
        txtFoto = findViewById(R.id.txtFoto);

        // Configurar botón para volver a la vista anterior
        Button btnVolver = findViewById(R.id.btnVolver);
        btnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Lógica para volver a la vista anterior
                Intent intent = new Intent(VerArma.this, Armas.class);
                startActivity(intent);
                finish();
            }
        });
        Intent intent = getIntent();
        if (intent != null) {
            Long ArmaId = intent.getLongExtra("armaId", 0);
            if (ArmaId != 0) {
                // Buscar y mostrar los datos del personaje
                buscarArma(ArmaId);
            }
        }
    }
    private void buscarArma(Long armaId) {
        ArmaControlador armaControlador = new ArmaControlador(this);
        armaControlador.buscarArma(armaId, new ArmaControlador.OnArmaEncontradaListener() {

            @Override
            public void onArmaEncontrada(Arma arma) {
                mostrarArma(arma);
            }

            @Override
            public void onError(String mensajeError) {
                // Manejar el error si no se puede encontrar el personaje
                Toast.makeText(VerArma.this, mensajeError, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void mostrarArma(Arma arma) {
        txtNombre.setText(arma.getNombre());
        txtAtaque.setText(String.valueOf(arma.getAtaque()));
        txtDanho.setText(arma.getDanho());
        txtTipo.setText(arma.getTipo());
        txtArrojadiza.setText(arma.isArrojadiza() ? "Sí" : "No");
        txtCaracteristica.setText(arma.getCar());
        txtCaracteristicas.setText(arma.getCaracteristicas());
        // Si deseas manejar la foto, puedes hacerlo aquí
        // txtFoto.setText(arma.getFoto());
    }
}
