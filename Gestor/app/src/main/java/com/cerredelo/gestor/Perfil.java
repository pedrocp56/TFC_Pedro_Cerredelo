package com.cerredelo.gestor;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import Clases.ImagenUtils;
import Clases.Usuario;
import Clases.UsuarioControlador;

public class Perfil extends AppCompatActivity {

    private EditText etPerfilEstado;
    private ImageView ivPerfilFoto;
    private EditText etPerfilContrasena;
    private EditText etPerfilContrasenaConfirmar;
    private TextView tvPerfilNombre;
    private Button btnGuardarCambios;
    private Button btnCambiarFoto;
    private ActivityResultLauncher<Intent> someActivityResultLauncher;
    private byte[] imagen;
    Long ID;
    String contrasenha;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        // Inicializar las vistas
        etPerfilEstado = findViewById(R.id.etPerfilEstado);
        ivPerfilFoto = findViewById(R.id.ivPerfilFoto);
        etPerfilContrasena = findViewById(R.id.etPerfilContrasena);
        etPerfilContrasenaConfirmar = findViewById(R.id.etPerfilContrasenaConfirmar);
        tvPerfilNombre = findViewById(R.id.tvPerfilNombre);
        btnGuardarCambios = findViewById(R.id.btnGuardarCambios);
        btnCambiarFoto = findViewById(R.id.btnCambiarFoto);

        // Configurar el botón "Volver"
        Button btnVolver = findViewById(R.id.btnVolver);
        btnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Crear un Intent para volver a la PantallaPrincipal
                Intent intent = new Intent(Perfil.this, PantallaPrincipal.class);
                startActivity(intent);
                finish();
            }
        });

        // Configurar el ActivityResultLauncher para tomar una foto
        someActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            Intent data = result.getData();
                            if (data != null) {
                                Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                                ivPerfilFoto.setImageBitmap(bitmap);
                                imagen = ImagenUtils.bitmapToByteArray(bitmap);
                            }
                        }
                    }
                }
        );

        // Cargar los datos del usuario desde SharedPreferences
        cargarDatosUsuario();

        // Configurar el botón "Cambiar Foto"
        btnCambiarFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent takePictureIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                someActivityResultLauncher.launch(takePictureIntent);
            }
        });

        // Configurar el botón "Guardar Cambios"
        btnGuardarCambios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guardarCambios();
            }
        });
    }

    private void cargarDatosUsuario() {

        ID = ControladorPref.obtenerUsuarioID(Perfil.this);
        contrasenha = ControladorPref.obtenerUsuarioContrasenha(Perfil.this);
        tvPerfilNombre.setText(ControladorPref.obtenerUsuarioNombre(Perfil.this));
        etPerfilEstado.setText(ControladorPref.obtenerUsuarioEstado(Perfil.this));
        byte[] foto =ControladorPref.obtenerUsuarioFoto(Perfil.this);
        if (foto==null){
            ivPerfilFoto.setImageResource(R.drawable.camara);
        }
        else{
            ivPerfilFoto.setImageBitmap(ImagenUtils.byteArrayToBitmap(foto));
        }
        ivPerfilFoto.setVisibility(View.VISIBLE);

    }

    private void guardarCambios() {
        String estado = etPerfilEstado.getText().toString();
        String nuevaContrasena = etPerfilContrasena.getText().toString();
        String confirmarContrasena = etPerfilContrasenaConfirmar.getText().toString();

        if (!nuevaContrasena.isEmpty() && (nuevaContrasena.length() < 5 || nuevaContrasena.length() > 13)) {
            Toast.makeText(Perfil.this, Perfil.this.getString(R.string.contraseñaMalTamanho), Toast.LENGTH_SHORT).show();
            return;
        }

        if (!nuevaContrasena.equals(confirmarContrasena)) {
            Toast.makeText(Perfil.this, Perfil.this.getString(R.string.contraseñaMalCoincidencia), Toast.LENGTH_SHORT).show();
            return;
        }

        String username = ControladorPref.obtenerUsuarioNombre(Perfil.this);
        if(nuevaContrasena.isEmpty()){
            nuevaContrasena = contrasenha;
        }
        byte[] foto = imagen; // Si la imagen no ha cambiado, este será el valor actual

        UsuarioControlador usuarioControlador = new UsuarioControlador(Perfil.this);
        String finalNuevaContrasena = nuevaContrasena;
        usuarioControlador.actualizarDatosUsuario(ID,username, nuevaContrasena, estado, foto, new UsuarioControlador.OnDatosActualizadosListener() {
            @Override
            public void onDatosActualizados() {
                // Los datos se han actualizado correctamente, puedes mostrar un mensaje de éxito o realizar otras acciones necesarias
                Toast.makeText(Perfil.this, Perfil.this.getString(R.string.datosActualizados), Toast.LENGTH_SHORT).show();
                ControladorPref.guardarUsuarioContrasenha(Perfil.this, finalNuevaContrasena);
                ControladorPref.guardarUsuarioEstado(Perfil.this, estado);
                //error al guardar la foto
                ControladorPref.guardarUsuarioFoto(Perfil.this,foto);
            }

            @Override
            public void onError(String mensajeError) {
                // Se produjo un error al actualizar los datos, puedes mostrar un mensaje de error o realizar otras acciones necesarias
                Toast.makeText(Perfil.this, mensajeError, Toast.LENGTH_SHORT).show();
            }
        });
    }
}


