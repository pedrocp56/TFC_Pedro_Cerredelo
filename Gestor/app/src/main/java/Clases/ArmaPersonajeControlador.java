package Clases;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import android.content.Context;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ArmaPersonajeControlador {
    private RequestQueue queue;
    private static final String BASE_URL = "http://192.168.1.33:8080/ArmaPersonaje/";

    public ArmaPersonajeControlador(Context context) {
        queue = Volley.newRequestQueue(context.getApplicationContext());
    }

    public void cargarListaArmasPersonaje(long personajeId, final OnListaArmasPersonajeCargadaListener listener) {
        String url = BASE_URL+"buscarArmaPersonajePorPersonaje/" + personajeId;

        // Crear una solicitud GET para obtener la lista de armas del personaje
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            // Parsear la respuesta JSON
                            List<ArmaPersonaje> armasPersonaje = new ArrayList<>();
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject jsonObject = response.getJSONObject(i);
                                ArmaPersonaje armaPersonaje = new ArmaPersonaje(jsonObject);
                                armasPersonaje.add(armaPersonaje);
                            }
                            // Llamar al listener con la lista de armas del personaje cargada
                            listener.onListaArmasPersonajeCargada(armasPersonaje);
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
                        listener.onError("Error al cargar la lista de armas del personaje");
                    }
                });

        // Agregar la solicitud a la cola de solicitudes de Volley
        queue.add(jsonArrayRequest);
    }

    public interface OnListaArmasPersonajeCargadaListener {
        void onListaArmasPersonajeCargada(List<ArmaPersonaje> armasPersonaje);
        void onError(String mensajeError);
    }

    public void buscarArmaPorIds(long armaId, long personajeId, long usuarioId, final OnArmaEncontradaListener listener) {
        String url = BASE_URL+"buscarArma/" + armaId + "/" + personajeId + "/" + usuarioId;

        // Crear una solicitud GET para buscar el arma por sus IDs
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            // Parsear la respuesta JSON
                            JSONObject armaObj = new JSONObject(response);
                            ArmaPersonaje arma = new ArmaPersonaje(armaObj);
                            // Llamar al listener con el arma encontrado
                            listener.onArmaEncontrada(arma);
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
                        listener.onError("Error al buscar el arma por sus IDs");
                    }
                });

        // Agregar la solicitud a la cola de solicitudes de Volley
        queue.add(stringRequest);
    }

    public interface OnArmaEncontradaListener {
        void onArmaEncontrada(ArmaPersonaje arma);
        void onError(String mensajeError);
    }


    public void agregarArmaPersonaje(final long armaId, final long personajeId, final long usuarioId, final ArmaPersonaje armaPersonaje, final OnResponseListener listener) {
        String url = BASE_URL + "guardarArmaPersonaje/" + armaId + "/" + personajeId + "/" + usuarioId;

        JSONObject postParams = new JSONObject();
        try {
            postParams.put("ataqueTotal", armaPersonaje.getAtaqueTotal());
            postParams.put("bonificacionAdicional", armaPersonaje.getBonificacionAdicional());
            postParams.put("competencia", armaPersonaje.isCompetencia());
        } catch (JSONException e) {
            e.printStackTrace();
            listener.onError("Error al crear los parámetros del arma-personaje");
            return;
        }

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        listener.onSuccess("Arma asignada al personaje exitosamente");
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        listener.onError("Error al asignar el arma al personaje: " + error.getMessage());
                    }
                }) {
            @Override
            public byte[] getBody() {
                return postParams.toString().getBytes();
            }

            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }
        };

        queue.add(stringRequest);
    }

    public void actualizarArmaPersonaje(final long armaId, final long personajeId, final long usuarioId, final ArmaPersonaje armaPersonaje, final OnResponseListener listener) {
        String url = BASE_URL + "actualizarArmaPersonaje/" + armaId + "/" + personajeId + "/" + usuarioId;

        JSONObject postParams = new JSONObject();
        try {
            postParams.put("ataqueTotal", armaPersonaje.getAtaqueTotal());
            postParams.put("bonificacionAdicional", armaPersonaje.getBonificacionAdicional());
            postParams.put("competencia", armaPersonaje.isCompetencia());
        } catch (JSONException e) {
            e.printStackTrace();
            listener.onError("Error al crear los parámetros del arma-personaje");
            return;
        }

        StringRequest stringRequest = new StringRequest(Request.Method.PUT, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        listener.onSuccess("Arma actualizada exitosamente");
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        listener.onError("Error al actualizar el arma del personaje: " + error.getMessage());
                    }
                }) {
            @Override
            public byte[] getBody() {
                return postParams.toString().getBytes();
            }

            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }
        };

        queue.add(stringRequest);
    }

    public void eliminarArmaPersonaje(final long armaId, final long personajeId, final long usuarioId, final OnResponseListener listener) {
        String url = BASE_URL + "eliminarArmaPersonaje/" + armaId + "/" + personajeId + "/" + usuarioId;

        StringRequest stringRequest = new StringRequest(Request.Method.DELETE, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        listener.onSuccess("Arma desequipada");
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        listener.onError("Error al desequipar el arma del personaje: " + error.getMessage());
                    }
                });

        queue.add(stringRequest);
    }

    public interface OnResponseListener {
        void onSuccess(String message);
        void onError(String errorMessage);
    }
}


