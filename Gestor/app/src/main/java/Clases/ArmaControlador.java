package Clases;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.cerredelo.gestor.ControladorPref;
import com.cerredelo.gestor.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ArmaControlador {

    private RequestQueue queue;

    private static String IP;
    private static String URL_Arma;
    private Context context;

    public ArmaControlador(Context context) {
        // Inicializar la cola de solicitudes Volley en el constructor
        this.context = context;
        IP= ControladorPref.obtenerIP(context);
        URL_Arma= IP+"Armas/";
        queue = Volley.newRequestQueue(context.getApplicationContext());
    }

    public void cargarListaArmas(final ArmaControlador.OnListaArmasCargadaListener listener) {
        String url = URL_Arma+"verArmas";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        List<Arma> armas = new ArrayList<>();
                        Log.d("Response", response.toString());
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject armaObj = response.getJSONObject(i);
                                Arma a = new Arma(armaObj);
                                armas.add(a);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        // Llamar al método callback para notificar que la lista de Armas ha sido cargada
                        listener.onListaArmasCargada(armas);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Llamar al método callback para manejar el error
                        String errorMessage = context.getString(R.string.error_Arma);
                        listener.onError(errorMessage);
                    }
                });

        queue.add(jsonArrayRequest);
    }

    // Interfaz para manejar la devolución de llamada cuando la lista de armas ha sido cargada
    public interface OnListaArmasCargadaListener {
        void onListaArmasCargada(List<Arma> armas);
        void onError(String mensajeError);
    }


    public void buscarArma(final Long armaId, final ArmaControlador.OnArmaEncontradaListener listener) {
        String url = URL_Arma+"buscarArma/" + armaId;

        // Crear una solicitud GET para buscar el personaje
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            // Parsear la respuesta JSON
                            JSONObject armaObj = new JSONObject(response);
                            Arma a = new Arma(armaObj);
                            // Llamar al listener con el arma encontrado
                            listener.onArmaEncontrada(a);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            // Llamar al listener con un mensaje de error
                            String errorMessage = context.getString(R.string.error_Parsear);
                            listener.onError(errorMessage);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Mostrar mensaje de error al no poder realizar la solicitud
                        String errorMessage = context.getString(R.string.error_Buscar_Arma);
                        listener.onError(errorMessage);
                    }
                });

        // Agregar la solicitud a la cola de solicitudes de Volley
        queue.add(stringRequest);
    }

    public interface OnArmaEncontradaListener {
        void onArmaEncontrada(Arma arma);
        void onError(String mensajeError);
    }

}
