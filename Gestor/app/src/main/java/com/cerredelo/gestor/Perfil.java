package com.cerredelo.gestor;

import static Clases.ImagenUtils.base64ToBitmap;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
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
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import Clases.ImagenUtils;
import Clases.Usuario;

public class Perfil extends AppCompatActivity {

    private EditText etPerfilEstado;
    private ImageView ivPerfilFoto;
    private EditText etPerfilContrasena;
    private EditText etPerfilContrasenaConfirmar;
    private TextView tvPerfilNombre;
    private Button btnGuardarCambios;
    private Button btnCambiarFoto;
    private RequestQueue queue;
    private ActivityResultLauncher<Intent> someActivityResultLauncher;
    private byte[] imagen;

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
        queue = Volley.newRequestQueue(this);

        // Configurar el botón "Volver"
        Button btnVolver = findViewById(R.id.btnVolver);
        btnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Crear un Intent para volver a la PantallaPrincipal
                Intent intent = new Intent(Perfil.this, PantallaPrincipal.class);
                // Iniciar la actividad de la PantallaPrincipal
                startActivity(intent);
                // Cerrar la actividad actual (Perfil)
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
        SharedPreferences sharedPref = getSharedPreferences("UserPref", Context.MODE_PRIVATE);
        String userName = sharedPref.getString("userName", "");
        String userEstado = sharedPref.getString("userEstado", "");
        String userFoto = sharedPref.getString("userFoto", "");

        tvPerfilNombre.setText(userName);
        etPerfilEstado.setText(userEstado);

        if (!userFoto.isEmpty()) {
            ivPerfilFoto.setImageBitmap(base64ToBitmap(userFoto));
        }
    }

    private void guardarCambios() {
        String estado = etPerfilEstado.getText().toString();
        String nuevaContrasena = etPerfilContrasena.getText().toString();
        String confirmarContrasena = etPerfilContrasenaConfirmar.getText().toString();
        if (!nuevaContrasena.isEmpty()&&(nuevaContrasena.length() < 5 || nuevaContrasena.length() > 13 )) {
            Toast.makeText(Perfil.this, "La contraseña debe tener entre 5 y 13 caracteres", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!nuevaContrasena.equals(confirmarContrasena)) {
            Toast.makeText(Perfil.this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
            return;
        }
        if (nuevaContrasena.isEmpty()){
            Toast.makeText(Perfil.this, "Se mantendra la antigua contraseña", Toast.LENGTH_SHORT).show();
        }

        // Actualizar los datos en el servidor
        actualizarDatosUsuario(estado, nuevaContrasena, imagen);
    }

    private void actualizarDatosUsuario(String estado, String contrasena, byte[] foto) {
        // Obtener el ID del usuario almacenado en SharedPreferences
        SharedPreferences sharedPref = getSharedPreferences("UserPref", Context.MODE_PRIVATE);
        long userId = sharedPref.getLong("userId", -1);

        // Construir la URL de actualización del usuario con su ID
        String updateUrl = "http://192.168.1.33:8080/Usuarios/actualizarUsuario/" + userId;

        // Crear un objeto JSONObject que contendrá los datos actualizados del usuario
        JSONObject userData = new JSONObject();
        try {
            // Agregar los datos actualizados al objeto JSONObject
            userData.put("nombre", tvPerfilNombre.getText().toString()); // Nombre obtenido del TextView
            if(contrasena.isEmpty() ){
                userData.put("contrasenha",sharedPref.getString("userContrasenha", ""));// Antigua contraseña
            } else {
                userData.put("contrasenha", contrasena); // Nueva contraseña
            }
            userData.put("estado", estado); // Nuevo estado
            userData.put("foto", foto); // Nueva foto
        } catch (JSONException e) {
            e.printStackTrace();
            // Manejar cualquier error al crear el objeto JSONObject
            Toast.makeText(Perfil.this, "Error al crear los datos del usuario", Toast.LENGTH_SHORT).show();
            return;
        }

        // Crear una solicitud JsonObjectRequest para enviar los datos actualizados al servidor
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.PUT, updateUrl, userData,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // La respuesta se manejará como un String
                        // Toast.makeText(Perfil.this, "Datos actualizados exitosamente", Toast.LENGTH_SHORT).show();
                        try {
                            String message = response.getString("message");
                            if (message.equals("success")) {
                                // Si la actualización fue exitosa
                                Toast.makeText(Perfil.this, "Datos actualizados exitosamente", Toast.LENGTH_SHORT).show();
                                // Actualizar los datos en SharedPreferences
                                guardarDatosEnSharedPreferences(estado, userData.getString("contrasenha"));
                            } else {
                                // Si hubo un error en la actualización
                                Toast.makeText(Perfil.this, "Error al actualizar los datos", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(Perfil.this, "Error de respuesta del servidor", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Manejar errores de la solicitud al servidor
                        Toast.makeText(Perfil.this, "Error al conectar con el servidor", Toast.LENGTH_SHORT).show();
                    }
                });

        // Agregar la solicitud a la cola de solicitudes
        queue.add(request);
    }



    private void guardarDatosEnSharedPreferences(String estado, String contrasena) {
        SharedPreferences sharedPref = getSharedPreferences("UserPref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("userEstado", estado);
        editor.apply();
    }
}

