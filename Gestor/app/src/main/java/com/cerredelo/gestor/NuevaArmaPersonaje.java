package com.cerredelo.gestor;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import Clases.Arma;
import Clases.ArmaControlador;
import Clases.ArmaPersonaje;
import Clases.ArmaPersonajeControlador;
import Clases.Personaje;
import Clases.PersonajeControlador;

public class NuevaArmaPersonaje extends AppCompatActivity {

    private EditText  txtBoni;
    private CheckBox chkCompetencia;
    private TextView txtAtaque,txtNombrePersonaje;
    Long personajeId, usuarioId;
    String personajeNombre;
    Spinner spinnerArmas;
    Arma arma;
    Personaje personaje;


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
        spinnerArmas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Aquí se llama a la lógica para calcular el ataque cuando se selecciona un arma
                Arma armaSeleccionada = (Arma) parent.getItemAtPosition(position);
                buscarArma(armaSeleccionada.getId());
                calcularAtaque();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Método llamado cuando no se ha seleccionado ningún elemento
            }
        });

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
                if(comprobarDatos()) {
                    crearArmaPersonaje();
                }
            }
        });
        chkCompetencia.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // Ejecutar mostrarAtaque cuando el estado del CheckBox cambie
                calcularAtaque();
            }
        });

        txtBoni.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // No se necesita implementar
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Ejecutar mostrarAtaque cuando el texto cambie
                calcularAtaque();
            }

            @Override
            public void afterTextChanged(Editable s) {
                // No se necesita implementar
            }
        });



        // Obtener los datos del Intent
        Intent intent = getIntent();
        if (intent != null) {
            personajeId = intent.getLongExtra("personajeId", 0);
            personajeNombre = intent.getStringExtra("personajeNombre");
            txtNombrePersonaje.setText(personajeNombre);
            buscarPersonaje(personajeId);
        }
        cargarDatosUsuario();

        // Obtener la lista de armas
        obtenerListaArmas();

        //recuperamos la primera arma para la primera ejecucion
        txtBoni.setText("0");
        calcularAtaque();
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
    private boolean comprobarDatos() {
        String ataqueStr = txtAtaque.getText().toString().trim();
        String boniStr = txtBoni.getText().toString().trim();

        if (ataqueStr.isEmpty()) {
            Toast.makeText(NuevaArmaPersonaje.this, "El valor de ataque no puede estar vacío", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (!isNumeric(ataqueStr)) {
            Toast.makeText(NuevaArmaPersonaje.this, "El valor de ataque debe ser numérico", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (boniStr.isEmpty()) {
            Toast.makeText(NuevaArmaPersonaje.this, "El valor de bonificación adicional no puede estar vacío", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (!isNumeric(boniStr)) {
            Toast.makeText(NuevaArmaPersonaje.this, "El valor de bonificación adicional debe ser numérico", Toast.LENGTH_SHORT).show();
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

    private void buscarArma(Long armaId) {
        ArmaControlador armaControlador = new ArmaControlador(this);
        armaControlador.buscarArma(armaId, new ArmaControlador.OnArmaEncontradaListener() {

            @Override
            public void onArmaEncontrada(Arma arm) {
                arma = arm;
            }

            @Override
            public void onError(String mensajeError) {
                // Manejar el error si no se puede encontrar el personaje
                Toast.makeText(NuevaArmaPersonaje.this, mensajeError, Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void buscarPersonaje(Long personajeId) {
        PersonajeControlador personajeControlador = new PersonajeControlador(this);
        personajeControlador.buscarPersonaje(personajeId, new PersonajeControlador.OnPersonajeEncontradoListener() {
            @Override
            public void onPersonajeEncontrado(Personaje p) {
                personaje=p;
            }

            @Override
            public void onError(String mensajeError) {
                // Manejar el error si no se puede encontrar el personaje
                Toast.makeText(NuevaArmaPersonaje.this, mensajeError, Toast.LENGTH_SHORT).show();
            }
        });
    }
    private int calcularAtaque() {
        if (personaje == null || arma == null) {
            return 0; // Salir si los datos no están cargados
        }
        int ataqueT = 0;
        switch (arma.getCar()) {
            case "Fuerza":
                ataqueT = (int) Math.floor((personaje.getFuerza() - 10) / 2);
                break;
            case "Destreza":
                ataqueT = (int) Math.floor((personaje.getDestreza() - 10) / 2);
                break;
            case "Constitucion":
                ataqueT = (int) Math.floor((personaje.getConstitucion() - 10) / 2);
                break;
            case "Inteligencia":
                ataqueT = (int) Math.floor((personaje.getInteligencia() - 10) / 2);
                break;
            case "Sabiduria":
                ataqueT = (int) Math.floor((personaje.getSabiduria() - 10) / 2);
                break;
            case "Carisma":
                ataqueT = (int) Math.floor((personaje.getCarisma() - 10) / 2);
                break;
            default:
                // Manejo de caso por defecto si el atributo no coincide con ninguno
                ataqueT = 0;
                break;
        }
        ataqueT+= arma.getAtaque();

        if(chkCompetencia.isChecked()){
            ataqueT+=personaje.getBonoCompetencia();
        }
        if(!txtBoni.getText().toString().isEmpty()){
            ataqueT+=Integer.parseInt(txtBoni.getText().toString().trim());
        }
        txtAtaque.setText(String.valueOf(ataqueT));
        return  ataqueT;
    }
}
