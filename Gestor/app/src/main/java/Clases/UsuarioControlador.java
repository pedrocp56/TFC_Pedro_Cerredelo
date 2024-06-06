package Clases;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.cerredelo.gestor.Login;

import org.json.JSONException;
import org.json.JSONObject;

import Helper.Variables;

public class UsuarioControlador {
    private RequestQueue queue;
    private static final String URL_Usuario = Login.IP+"Usuarios/";


    public UsuarioControlador(Context context) {
        // Inicializar la cola de solicitudes Volley en el constructor
        queue = Volley.newRequestQueue(context.getApplicationContext());
    }
    public void buscarUsuario(final Long usuarioID, final OnUsuarioEncontradoListener listener) {
        String url = URL_Usuario+"buscarUsuario/" + usuarioID;

        // Crear una solicitud GET para buscar el usuario
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            // Parsear la respuesta JSON
                            JSONObject usuarioObj = new JSONObject(response);
                            Usuario usuario = new Usuario(usuarioObj);
                            // Llamar al listener con el usuario encontrado
                            listener.onUsuarioEncontrado(usuario);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            // Llamar al listener con un mensaje de error
                            listener.onError("Error al parsear la respuesta del servidor");
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Mostrar mensaje de error al no poder realizar la solicitud
                        listener.onError("Error al buscar el usuario");
                    }
                });

        // Agregar la solicitud a la cola de solicitudes de Volley
        queue.add(stringRequest);
    }

    public interface OnUsuarioEncontradoListener {
        void onUsuarioEncontrado(Usuario usuario);
        void onError(String mensajeError);
    }

}
