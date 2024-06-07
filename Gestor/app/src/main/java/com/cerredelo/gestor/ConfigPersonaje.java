package com.cerredelo.gestor;

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

import Clases.Personaje;
import Clases.PersonajeControlador;
import Clases.Usuario;
import Clases.UsuarioControlador;

public class ConfigPersonaje extends AppCompatActivity {

    // Declaración de EditText
    EditText editNombre, editFuerza, editDestreza, editConstitucion, editInteligencia, editSabiduria, editCarisma, editCompetencia, editFoto;
    TextView textNombreUsuario;
    boolean DatosPrecargados;
    Usuario user;
    Long userId;
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
                if (comprobarDatos()) {
                    if (DatosPrecargados) {
                        // Actualizar
                        actualizarPersonaje();
                    } else {
                        // Crear
                        crearPersonaje();
                    }
                }
            }
        });

        // Configurar botón para volver a la vista de Personajes
        Button btnVolver = findViewById(R.id.btnVolver);
        btnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Lógica para volver a la vista de Personajes
                Intent intent = new Intent(ConfigPersonaje.this, Personajes.class);
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
                Toast.makeText(ConfigPersonaje.this, mensajeError, Toast.LENGTH_SHORT).show();
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

    }

    private void actualizarPersonaje() {
        Personaje p = new Personaje();
        p.setNombre(editNombre.getText().toString());
        p.setFuerza(Integer.parseInt(editFuerza.getText().toString()));
        p.setDestreza(Integer.parseInt(editDestreza.getText().toString()));
        p.setConstitucion(Integer.parseInt(editConstitucion.getText().toString()));
        p.setInteligencia(Integer.parseInt(editInteligencia.getText().toString()));
        p.setSabiduria(Integer.parseInt(editSabiduria.getText().toString()));
        p.setCarisma(Integer.parseInt(editCarisma.getText().toString()));
        p.setBonoCompetencia(Integer.parseInt(editCompetencia.getText().toString()));

        PersonajeControlador personajeControlador = new PersonajeControlador(this);
        Toast.makeText(ConfigPersonaje.this, p.toString(), Toast.LENGTH_LONG).show();
        personajeControlador.actualizarPersonaje(personaje.getPersonajeId(),p, new PersonajeControlador.OnPersonajeActualizadoListener() {
            @Override
            public void onPersonajeActualizado(String mensaje) {
                Toast.makeText(ConfigPersonaje.this, mensaje, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(ConfigPersonaje.this, Personajes.class);
                startActivity(intent);
                finish();
            }

            @Override
            public void onError(String mensajeError) {
                Toast.makeText(ConfigPersonaje.this, mensajeError, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void crearPersonaje() {
        Personaje personaje = new Personaje();
        //personaje.setPersonajeId();
        personaje.setNombre(editNombre.getText().toString());
        personaje.setFuerza(Integer.parseInt(editFuerza.getText().toString().trim()));
        personaje.setDestreza(Integer.parseInt(editDestreza.getText().toString().trim()));
        personaje.setConstitucion(Integer.parseInt(editConstitucion.getText().toString().trim()));
        personaje.setInteligencia(Integer.parseInt(editInteligencia.getText().toString().trim()));
        personaje.setSabiduria(Integer.parseInt(editSabiduria.getText().toString().trim()));
        personaje.setCarisma(Integer.parseInt(editCarisma.getText().toString().trim()));
        personaje.setBonoCompetencia(Integer.parseInt(editCompetencia.getText().toString().trim()));

        PersonajeControlador personajeControlador = new PersonajeControlador(this);
        personajeControlador.crearPersonaje(userId,personaje, new PersonajeControlador.OnPersonajeCreadoListener() {
            @Override
            public void onPersonajeCreado(String mensaje) {
                Toast.makeText(ConfigPersonaje.this, mensaje, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(ConfigPersonaje.this, Personajes.class);
                startActivity(intent);
                finish();
            }

            @Override
            public void onError(String mensajeError) {
                Toast.makeText(ConfigPersonaje.this, mensajeError, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void cargarDatosUsuario() {
        // Obtener el objeto SharedPreferences
        SharedPreferences sharedPref = getSharedPreferences("UserPref", Context.MODE_PRIVATE);

        // Recuperar los datos del usuario
        userId = sharedPref.getLong("userId", 0);
        String userName = sharedPref.getString("userName", "");

        // Asignar el nombre de usuario recuperado a la vista (esto se puede hacer inmediatamente)
        textNombreUsuario.setText(userName);

        // Buscar el usuario completo en el servidor
        usuarioControlador.buscarUsuario(userId, new UsuarioControlador.OnUsuarioEncontradoListener() {
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
    private boolean comprobarDatos() {
        String nombre = editNombre.getText().toString().trim();
        String fuerzaStr = editFuerza.getText().toString().trim();
        String destrezaStr = editDestreza.getText().toString().trim();
        String constitucionStr = editConstitucion.getText().toString().trim();
        String inteligenciaStr = editInteligencia.getText().toString().trim();
        String sabiduriaStr = editSabiduria.getText().toString().trim();
        String carismaStr = editCarisma.getText().toString().trim();
        String competenciaStr = editCompetencia.getText().toString().trim();

        if (nombre.isEmpty()) {
            Toast.makeText(ConfigPersonaje.this, ConfigPersonaje.this.getString(R.string.nombreVacio), Toast.LENGTH_SHORT).show();
            return false;
        }

        if (fuerzaStr.isEmpty() || !isValidRange(fuerzaStr, 1, 30)) {
            Toast.makeText(ConfigPersonaje.this, ConfigPersonaje.this.getString(R.string.fuerzaMal), Toast.LENGTH_SHORT).show();
            return false;
        }

        if (destrezaStr.isEmpty() || !isValidRange(destrezaStr, 1, 30)) {
            Toast.makeText(ConfigPersonaje.this, ConfigPersonaje.this.getString(R.string.destrezaMal), Toast.LENGTH_SHORT).show();
            return false;
        }

        if (constitucionStr.isEmpty() || !isValidRange(constitucionStr, 1, 30)) {
            Toast.makeText(ConfigPersonaje.this, ConfigPersonaje.this.getString(R.string.constitucionMal), Toast.LENGTH_SHORT).show();
            return false;
        }

        if (inteligenciaStr.isEmpty() || !isValidRange(inteligenciaStr, 1, 30)) {
            Toast.makeText(ConfigPersonaje.this, ConfigPersonaje.this.getString(R.string.inteligenciaMal), Toast.LENGTH_SHORT).show();
            return false;
        }

        if (sabiduriaStr.isEmpty() || !isValidRange(sabiduriaStr, 1, 30)) {
            Toast.makeText(ConfigPersonaje.this, ConfigPersonaje.this.getString(R.string.sabiduriaMal), Toast.LENGTH_SHORT).show();
            return false;
        }

        if (carismaStr.isEmpty() || !isValidRange(carismaStr, 1, 30)) {
            Toast.makeText(ConfigPersonaje.this, ConfigPersonaje.this.getString(R.string.carismaMal), Toast.LENGTH_SHORT).show();
            return false;
        }

        if (competenciaStr.isEmpty() || !isValidRange(competenciaStr, 2, 6)) {
            Toast.makeText(ConfigPersonaje.this, ConfigPersonaje.this.getString(R.string.competenciaMal), Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    private boolean isValidRange(String value, int min, int max) {
        try {
            int num = Integer.parseInt(value);
            return num >= min && num <= max;
        } catch (NumberFormatException e) {
            return false;
        }
    }


}
