package com.cerredelo.gestor;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import Clases.ArmaPersonaje;
import Clases.ArmaPersonajeControlador;

public class EditarArmaPersonaje extends AppCompatActivity {

    private EditText txtAtaque, txtBoni;
    private CheckBox chkCompetencia;
    private TextView txtNombrePersonaje,txtNombreArma;
    Long personajeId, armaId, usuarioId;
    String personajeNombre, armaNombre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_editar_arma_personaje);

        // Inicializar los EditTexts
        txtAtaque = findViewById(R.id.txtAtaque);
        txtBoni = findViewById(R.id.txtBoni);
        chkCompetencia = findViewById(R.id.chkCompetencia);
        txtNombrePersonaje= findViewById(R.id.txtNombrePersonaje);
        txtNombreArma= findViewById(R.id.txtNombreArma);

        // Configurar el botón "Volver"
        Button btnVolver = findViewById(R.id.btnVolver);
        btnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Agrega aquí la lógica para volver a la actividad anterior
                Intent intent = new Intent(EditarArmaPersonaje.this, VerArmaPersonaje.class);
                intent.putExtra("personajeId", personajeId);
                intent.putExtra("armaId", armaId);
                intent.putExtra("personajeNombre", personajeNombre);
                intent.putExtra("armaNombre", armaNombre);
                startActivity(intent);
                finish(); // Cierra esta actividad y vuelve a la actividad anterior en la pila
            }
        });

        // Configurar el botón "Confirmar"
        Button btnConfirmar = findViewById(R.id.btnConfirmar);
        btnConfirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(comprobarDatos()) {
                    // Obtener los datos editados del EditText
                    int ataqueTotal = Integer.parseInt(txtAtaque.getText().toString());
                    int bonificacionAdicional = Integer.parseInt(txtBoni.getText().toString());
                    boolean competencia = chkCompetencia.isChecked();

                    // Crear un nuevo objeto ArmaPersonaje con los datos editados
                    ArmaPersonaje armaEditado = new ArmaPersonaje();
                    armaEditado.setAtaqueTotal(ataqueTotal);
                    armaEditado.setBonificacionAdicional(bonificacionAdicional);
                    armaEditado.setCompetencia(competencia);

                    // Verificar si armaEditado es nulo antes de llamar al método actualizarArmaPersonaje
                    if (armaEditado != null) {
                        // Actualizar el arma del personaje en el servidor
                        ArmaPersonajeControlador armaPersonajeControlador = new ArmaPersonajeControlador(EditarArmaPersonaje.this);
                        armaPersonajeControlador.actualizarArmaPersonaje(armaId, personajeId, usuarioId, armaEditado, new ArmaPersonajeControlador.OnResponseListener() {
                            @Override
                            public void onSuccess(String message) {
                                Toast.makeText(EditarArmaPersonaje.this, message, Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(EditarArmaPersonaje.this, VerArmaPersonaje.class);
                                intent.putExtra("personajeId", personajeId);
                                intent.putExtra("armaId", armaId);
                                intent.putExtra("personajeNombre", personajeNombre);
                                intent.putExtra("armaNombre", armaNombre);
                                startActivity(intent);
                                finish();
                            }

                            @Override
                            public void onError(String error) {
                                Toast.makeText(EditarArmaPersonaje.this, error, Toast.LENGTH_SHORT).show();
                            }
                        });
                    } else {
                        Toast.makeText(EditarArmaPersonaje.this, "Error: Arma editado es nulo", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });


        // Obtener los datos del Intent
        Intent intent = getIntent();
        if (intent != null) {
            armaId = intent.getLongExtra("armaId", 0);
            personajeId = intent.getLongExtra("personajeId", 0);
            personajeNombre = intent.getStringExtra("personajeNombre");
            armaNombre = intent.getStringExtra("armaNombre");

        }

        cargarDatosUsuario();
    }
    private void cargarDatosUsuario() {
        // Obtener el objeto SharedPreferences
        SharedPreferences sharedPref = getSharedPreferences("UserPref", Context.MODE_PRIVATE);

        // Recuperar los datos del usuario
        usuarioId = sharedPref.getLong("userId", -1);

        // Cargar los datos del arma del personaje
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
                Toast.makeText(EditarArmaPersonaje.this, mensajeError, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void mostrarArmapersonaje(ArmaPersonaje armaper) {
        txtNombrePersonaje.setText(personajeNombre);
        txtNombreArma.setText(armaNombre);
        txtAtaque.setText(String.valueOf(armaper.getAtaqueTotal()));
        txtBoni.setText(String.valueOf(armaper.getBonificacionAdicional()));
        chkCompetencia.setChecked(armaper.isCompetencia());
    }
    private boolean comprobarDatos() {
        String ataqueStr = txtAtaque.getText().toString().trim();
        String boniStr = txtBoni.getText().toString().trim();

        if (ataqueStr.isEmpty()) {
            Toast.makeText(EditarArmaPersonaje.this, "El valor de ataque no puede estar vacío", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (!isNumeric(ataqueStr)) {
            Toast.makeText(EditarArmaPersonaje.this, "El valor de ataque debe ser numérico", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (boniStr.isEmpty()) {
            Toast.makeText(EditarArmaPersonaje.this, "El valor de bonificación adicional no puede estar vacío", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (!isNumeric(boniStr)) {
            Toast.makeText(EditarArmaPersonaje.this, "El valor de bonificación adicional debe ser numérico", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    private boolean isNumeric(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}

