package com.cerredelo.gestor;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import Clases.Personaje;
import Clases.PersonajeControlador;

public class VerPersonaje extends AppCompatActivity {

    // Declaración de TextView
    TextView txtNombreUsuario, txtNombre, txtFuerza, txtBonoFuerza, txtDestreza, txtBonoDestreza, txtConstitucion, txtBonoConstitucion,
            txtInteligencia, txtBonoInteligencia, txtSabiduria, txtBonoSabiduria, txtCarisma, txtBonoCarisma, txtCompetencia, txtFoto;

    Personaje p;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_personaje);

        // Inicialización de TextView
        txtNombreUsuario = findViewById(R.id.txtNombreUsuario);
        txtNombre = findViewById(R.id.txtNombre);
        txtFuerza = findViewById(R.id.txtFuerza);
        txtBonoFuerza = findViewById(R.id.txtBonoFuerza);
        txtDestreza = findViewById(R.id.txtDestreza);
        txtBonoDestreza = findViewById(R.id.txtBonoDestreza);
        txtConstitucion = findViewById(R.id.txtConstitucion);
        txtBonoConstitucion = findViewById(R.id.txtBonoConstitucion);
        txtInteligencia = findViewById(R.id.txtInteligencia);
        txtBonoInteligencia = findViewById(R.id.txtBonoInteligencia);
        txtSabiduria = findViewById(R.id.txtSabiduria);
        txtBonoSabiduria = findViewById(R.id.txtBonoSabiduria);
        txtCarisma = findViewById(R.id.txtCarisma);
        txtBonoCarisma = findViewById(R.id.txtBonoCarisma);
        txtCompetencia = findViewById(R.id.txtCompetencia);
        txtFoto = findViewById(R.id.txtFoto);

        // Recibir el ID del personaje enviado desde la actividad anterior
        Intent intent = getIntent();
        if (intent != null) {
            Long personajeId = intent.getLongExtra("personajeId", 0);
            if (personajeId != 0) {
                // Buscar y mostrar los datos del personaje
                buscarPersonaje(personajeId);
            }
        }

        // Configurar botón para Armas
        Button btnArmas = findViewById(R.id.btnArmas);
        btnArmas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(VerPersonaje.this, ArmasPersonaje.class);
                intent.putExtra("personajeId",p.getPersonajeId());
                startActivity(intent);
                finish();
            }
        });

        // Configurar botón para volver a la vista anterior
        Button btnVolver = findViewById(R.id.btnVolver);
        btnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Lógica para volver a la vista anterior
                Intent intent = new Intent(VerPersonaje.this, Personajes.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void buscarPersonaje(Long personajeId) {
        PersonajeControlador personajeControlador = new PersonajeControlador(this);
        personajeControlador.buscarPersonaje(personajeId, new PersonajeControlador.OnPersonajeEncontradoListener() {
            @Override
            public void onPersonajeEncontrado(Personaje personaje) {
                p=personaje;
                mostrarPersonaje();
            }

            @Override
            public void onError(String mensajeError) {
                // Manejar el error si no se puede encontrar el personaje
                Toast.makeText(VerPersonaje.this, mensajeError, Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void mostrarPersonaje() {
        txtNombreUsuario.setText(p.getUsuario().getNombre());
        txtNombre.setText(p.getNombre());
        txtFuerza.setText(String.valueOf(p.getFuerza()));
        txtBonoFuerza.setText(String.valueOf((int)Math.floor((p.getFuerza() - 10) / 2)));
        txtDestreza.setText(String.valueOf(p.getDestreza()));
        txtBonoDestreza.setText(String.valueOf((int)Math.floor((p.getDestreza() - 10) / 2)));
        txtConstitucion.setText(String.valueOf(p.getConstitucion()));
        txtBonoConstitucion.setText(String.valueOf((int)Math.floor((p.getConstitucion() - 10) / 2)));
        txtInteligencia.setText(String.valueOf(p.getInteligencia()));
        txtBonoInteligencia.setText(String.valueOf((int)Math.floor((p.getInteligencia() - 10) / 2)));
        txtSabiduria.setText(String.valueOf(p.getSabiduria()));
        txtBonoSabiduria.setText(String.valueOf((int)Math.floor((p.getSabiduria() - 10) / 2)));
        txtCarisma.setText(String.valueOf(p.getCarisma()));
        txtBonoCarisma.setText(String.valueOf((int)Math.floor((p.getCarisma() - 10) / 2)));
        txtCompetencia.setText(String.valueOf(p.getBonoCompetencia()));
        //txtFoto.setText(p.getFoto());
    }

}
