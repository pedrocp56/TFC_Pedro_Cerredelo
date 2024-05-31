package com.cerredelo.gestor;

import static Clases.ImagenUtils.base64ToBitmap;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.cerredelo.gestor.Personajes;
import com.cerredelo.gestor.R;

import java.io.Serializable;

import Clases.Personaje;
import Clases.PersonajeControlador;
import Clases.Usuario;
import Clases.UsuarioControlador;

public class configPersonaje extends AppCompatActivity {

    // Declaración de EditText
    EditText editNombre, editFuerza, editDestreza, editConstitucion, editInteligencia, editSabiduria, editCarisma, editCompetencia, editFoto;
    TextView textNombreUsuario;
    boolean DatosPrecargados;
    Usuario user;
    Personaje personaje;

    private UsuarioControlador usuarioControlador;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config_personaje);

        usuarioControlador = new UsuarioControlador(this);
        DatosPrecargados = false;

        textNombreUsuario = findViewById(R.id.textNombreUsuario);
        cargarDatosUsuario();

        // Inicialización de EditText
        editNombre = findViewById(R.id.editNombre);
        editFuerza = findViewById(R.id.editFuerza);
        editDestreza = findViewById(R.id.editDestreza);
        editConstitucion = findViewById(R.id.editConstitucion);
        editInteligencia = findViewById(R.id.editInteligencia);
        editSabiduria = findViewById(R.id.editSabiduria);
        editCarisma = findViewById(R.id.editCarisma);
        editCompetencia = findViewById(R.id.editCompetencia);
        editFoto = findViewById(R.id.editFoto);

        // Recibir el objeto Personaje enviado desde la actividad anterior
        Intent intent = getIntent();
        if (intent != null) {
            Long pId = intent.getLongExtra("personajeId", 0);
            if (pId != 0) {
                // Buscar personaje
                buscarPersonaje(pId);
            }
        }

        // Configurar botón para guardar
        Button btnGuardar = findViewById(R.id.btnConfirmar);
        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(configPersonaje.this,"boton pulsado",Toast.LENGTH_SHORT);
                if (DatosPrecargados) {
                    // Actualizar
                    Toast.makeText(configPersonaje.this,"Actualizar",Toast.LENGTH_SHORT);
                    actualizarPersonaje();
                } else {
                    // Crear
                    Toast.makeText(configPersonaje.this,"Crear",Toast.LENGTH_SHORT);
                    crearPersonaje();
                }
            }
        });

        // Configurar botón para volver a la vista de Personajes
        Button btnVolver = findViewById(R.id.btnVolver);
        btnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Lógica para volver a la vista de Personajes
                Intent intent = new Intent(configPersonaje.this, Personajes.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void buscarPersonaje(Long personajeId) {
        PersonajeControlador personajeControlador = new PersonajeControlador(this);
        personajeControlador.buscarPersonaje(personajeId, new PersonajeControlador.OnPersonajeEncontradoListener() {
            @Override
            public void onPersonajeEncontrado(Personaje p) {
                DatosPrecargados = true;
                personaje=p;
                cargarDatosPersonaje(personaje);
            }

            @Override
            public void onError(String mensajeError) {
                Toast.makeText(configPersonaje.this, mensajeError, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void cargarDatosPersonaje(Personaje personaje) {
        editNombre.setText(personaje.getNombre());
        editFuerza.setText(String.valueOf(personaje.getFuerza()));
        editDestreza.setText(String.valueOf(personaje.getDestreza()));
        editConstitucion.setText(String.valueOf(personaje.getConstitucion()));
        editInteligencia.setText(String.valueOf(personaje.getInteligencia()));
        editSabiduria.setText(String.valueOf(personaje.getSabiduria()));
        editCarisma.setText(String.valueOf(personaje.getCarisma()));
        editCompetencia.setText(String.valueOf(personaje.getBonoCompetencia()));
        editFoto.setText(personaje.getFoto());
    }

    private void actualizarPersonaje() {
        Personaje personaje = new Personaje();
        personaje.setPersonajeId(personaje.getPersonajeId());
        personaje.setNombre(editNombre.getText().toString());
        personaje.setUsuario(user);
        personaje.setFuerza(Integer.parseInt(editFuerza.getText().toString()));
        personaje.setDestreza(Integer.parseInt(editDestreza.getText().toString()));
        personaje.setConstitucion(Integer.parseInt(editConstitucion.getText().toString()));
        personaje.setInteligencia(Integer.parseInt(editInteligencia.getText().toString()));
        personaje.setSabiduria(Integer.parseInt(editSabiduria.getText().toString()));
        personaje.setCarisma(Integer.parseInt(editCarisma.getText().toString()));
        personaje.setBonoCompetencia(Integer.parseInt(editCompetencia.getText().toString()));
        personaje.setFoto(editFoto.getText().toString());

        PersonajeControlador personajeControlador = new PersonajeControlador(this);
        Toast.makeText(configPersonaje.this, personaje.toString(), Toast.LENGTH_LONG).show();
        personajeControlador.actualizarPersonaje(personaje.getPersonajeId(), personaje, new PersonajeControlador.OnPersonajeActualizadoListener() {
            @Override
            public void onPersonajeActualizado(String mensaje) {
                Toast.makeText(configPersonaje.this, mensaje, Toast.LENGTH_SHORT).show();
                finish();
            }

            @Override
            public void onError(String mensajeError) {
                Toast.makeText(configPersonaje.this, mensajeError, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void crearPersonaje() {
        Personaje personaje = new Personaje();
        //personaje.setPersonajeId();
        personaje.setNombre(editNombre.getText().toString());
        personaje.setUsuario(user);
        personaje.setFuerza(Integer.parseInt(editFuerza.getText().toString()));
        personaje.setDestreza(Integer.parseInt(editDestreza.getText().toString()));
        personaje.setConstitucion(Integer.parseInt(editConstitucion.getText().toString()));
        personaje.setInteligencia(Integer.parseInt(editInteligencia.getText().toString()));
        personaje.setSabiduria(Integer.parseInt(editSabiduria.getText().toString()));
        personaje.setCarisma(Integer.parseInt(editCarisma.getText().toString()));
        personaje.setBonoCompetencia(Integer.parseInt(editCompetencia.getText().toString()));
        personaje.setFoto(editFoto.getText().toString());

        PersonajeControlador personajeControlador = new PersonajeControlador(this);
        personajeControlador.crearPersonaje(personaje, new PersonajeControlador.OnPersonajeCreadoListener() {
            @Override
            public void onPersonajeCreado(String mensaje) {
                Toast.makeText(configPersonaje.this, mensaje, Toast.LENGTH_SHORT).show();
                finish();
            }

            @Override
            public void onError(String mensajeError) {
                Toast.makeText(configPersonaje.this, mensajeError, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void cargarDatosUsuario() {
        // Obtener el objeto SharedPreferences
        SharedPreferences sharedPref = getSharedPreferences("UserPref", Context.MODE_PRIVATE);

        // Recuperar los datos del usuario
        Long userID = sharedPref.getLong("userId", 0);
        String userName = sharedPref.getString("userName", "");

        // Asignar el nombre de usuario recuperado a la vista (esto se puede hacer inmediatamente)
        textNombreUsuario.setText(userName);

        // Buscar el usuario completo en el servidor
        usuarioControlador.buscarUsuario(userID, new UsuarioControlador.OnUsuarioEncontradoListener() {
            @Override
            public void onUsuarioEncontrado(Usuario usuario) {
                user = usuario;
                textNombreUsuario.setText(user.getNombre());
            }

            @Override
            public void onError(String mensajeError) {
                // Manejar el error, mostrar un mensaje al usuario, etc.
                Toast.makeText(getApplicationContext(), mensajeError, Toast.LENGTH_SHORT).show();
            }
        });
    }

}
