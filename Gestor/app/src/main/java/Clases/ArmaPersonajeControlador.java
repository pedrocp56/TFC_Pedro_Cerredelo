package Clases;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.cerredelo.gestor.Login;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import Helper.Variables;

public class ArmaPersonajeControlador {
    private RequestQueue queue;
    private static final String URL_ArmaPersonaje = Login.IP+"ArmaPersonaje/";

    public ArmaPersonajeControlador(Context context) {
        queue = Volley.newRequestQueue(context.getApplicationContext());
    }

    public void cargarListaArmasPersonaje(long personajeId, final OnListaArmasPersonajeCargadaListener listener) {
        String url = URL_ArmaPersonaje +"buscarArmaPersonajePorPersonaje/" + personajeId;

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
        String url = URL_ArmaPersonaje +"buscarArma/" + armaId + "/" + personajeId + "/" + usuarioId;

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


    public void crearArmaPersonaje(final long armaId, final long personajeId, final long usuarioId, final ArmaPersonaje armaPersonaje, final OnResponseListener listener) {
        buscarArmaPorIds(armaId, personajeId, usuarioId, new OnArmaEncontradaListener() {
            @Override
            public void onArmaEncontrada(ArmaPersonaje arma) {
                // Si se encuentra un arma para el personaje, notificar al listener de error
                listener.onError("El personaje ya tiene equipada el arma");
            }

            @Override
            public void onError(String mensajeError) {
                // Si no se encuentra un arma para el personaje, crear el arma
                String url = URL_ArmaPersonaje + "guardarArmaPersonaje/" + armaId + "/" + personajeId + "/" + usuarioId;

                // Convertir el objeto ArmaPersonaje a JSON
                JSONObject armaPersonajeJson = new JSONObject();
                try {
                    armaPersonajeJson.put("ataqueTotal", armaPersonaje.getAtaqueTotal());
                    armaPersonajeJson.put("bonificacionAdicional", armaPersonaje.getBonificacionAdicional());
                    armaPersonajeJson.put("competencia", armaPersonaje.isCompetencia());
                } catch (JSONException e) {
                    e.printStackTrace();
                    listener.onError("Error al crear los parámetros del arma-personaje");
                    return;
                }

                // Crear la solicitud de JSON
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                        Request.Method.POST,
                        url,
                        armaPersonajeJson,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                listener.onSuccess("Arma equipada exitosamente");

                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                // Manejar el error de la solicitud HTTP
                                listener.onError(error.getMessage());
                            }
                        }
                );

                // Añadir la solicitud a la cola de solicitudes
                queue.add(jsonObjectRequest);
            }
        });
    }


    public void actualizarArmaPersonaje(final long armaId, final long personajeId, final long usuarioId, final ArmaPersonaje armaPersonaje, final OnResponseListener listener) {
        String url = URL_ArmaPersonaje + "actualizarArmaPersonaje/" + armaId + "/" + personajeId + "/" + usuarioId;

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

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PUT, url, postParams,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        listener.onSuccess(response.toString());
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        listener.onError(error.getMessage());
                    }
                });

        queue.add(jsonObjectRequest);
    }

    public void eliminarArmaPersonaje(final long armaId, final long personajeId, final long usuarioId, final OnResponseListener listener) {
        String url = URL_ArmaPersonaje + "eliminarArmaPersonaje/" + armaId + "/" + personajeId + "/" + usuarioId;

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


