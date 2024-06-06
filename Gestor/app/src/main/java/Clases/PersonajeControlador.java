package Clases;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.cerredelo.gestor.Login;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import Helper.Variables;

public class PersonajeControlador {

    private RequestQueue queue;
    private static final String URL_Personaje = Login.IP+"Personajes/";


    public PersonajeControlador(Context context) {
        // Inicializar la cola de solicitudes Volley en el constructor
        queue = Volley.newRequestQueue(context.getApplicationContext());
    }

    public void cargarListaPersonajes(Long userID, final OnListaPersonajesCargadaListener listener) {
        String url = URL_Personaje+"buscarPersonajesPorUsuario/" + userID;

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        List<Personaje> personajes = new ArrayList<>();
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject personajeObj = response.getJSONObject(i);
                                Personaje pj = new Personaje(personajeObj);
                                personajes.add(pj);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        // Llamar al método callback para notificar que la lista de personajes ha sido cargada
                        listener.onListaPersonajesCargada(personajes);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Llamar al método callback para manejar el error
                        listener.onError("Error al obtener personajes");
                    }
                });

        queue.add(jsonArrayRequest);
    }

    public interface OnListaPersonajesCargadaListener {
        void onListaPersonajesCargada(List<Personaje> personajes);
        void onError(String mensajeError);
    }

    public void eliminarPersonaje(final long usuarioId, final long personajeId, final OnPersonajeEliminadoListener listener) {
        String url = URL_Personaje+"eliminarPersonaje/" + usuarioId + "/" + personajeId;

        // Crear una solicitud DELETE para eliminar el personaje
        StringRequest stringRequest = new StringRequest(Request.Method.DELETE, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Mostrar mensaje
                        listener.onPersonajeEliminado(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Mostrar mensaje de error al no poder realizar la solicitud
                        listener.onError("Error al eliminar el personaje");
                    }
                });

        // Agregar la solicitud a la cola de solicitudes de Volley
        queue.add(stringRequest);
    }

    public interface OnPersonajeEliminadoListener {
        void onPersonajeEliminado(String mensaje);
        void onError(String mensajeError);
    }

    public void buscarPersonaje(final long personajeId, final OnPersonajeEncontradoListener listener) {
        String url = URL_Personaje+"buscarPersonajeById/" + personajeId;

        // Crear una solicitud GET para buscar el personaje
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            // Parsear la respuesta JSON
                            JSONObject personajeObj = new JSONObject(response);
                            Personaje pj = new Personaje(personajeObj);
                            // Llamar al listener con el personaje encontrado
                            listener.onPersonajeEncontrado(pj);
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
                        listener.onError("Error al buscar el personaje");
                    }
                });

        // Agregar la solicitud a la cola de solicitudes de Volley
        queue.add(stringRequest);
    }

    public interface OnPersonajeEncontradoListener {
        void onPersonajeEncontrado(Personaje personaje);
        void onError(String mensajeError);
    }

    public void actualizarPersonaje(final long personajeId,final Personaje personaje, final OnPersonajeActualizadoListener listener) {
        String url = URL_Personaje+"actualizarPersonaje/"+ personajeId;

        // Convertir el objeto Personaje a JSON
        JSONObject personajeJson = new JSONObject();
        try {
            personajeJson.put("personajeNombre", personaje.getNombre());
            personajeJson.put("caracteristicaFuerza", personaje.getFuerza());
            personajeJson.put("caracteristicaDestreza", personaje.getDestreza());
            personajeJson.put("caracteristicaConstitucion", personaje.getConstitucion());
            personajeJson.put("caracteristicaInteligencia", personaje.getInteligencia());
            personajeJson.put("caracteristicaSabiduria", personaje.getSabiduria());
            personajeJson.put("caracteristicaCarisma", personaje.getCarisma());
            personajeJson.put("bonoCompetencia", personaje.getBonoCompetencia());
            if (personaje.getFoto() != null) {
                //personajeJson.put("foto", new String(personaje.getFoto()));
            }
        } catch (JSONException e) {
            e.printStackTrace();
            listener.onError("Error al crear JSON para el personaje");
            return;
        }

        // Crear una solicitud PUT para actualizar el personaje
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PUT, url, personajeJson,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        listener.onPersonajeActualizado("Personaje " + personaje.getNombre() + " actualizado exitosamente");
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        listener.onError("Error al actualizar el personaje " + personaje.getNombre());
                    }
                });

        // Agregar la solicitud a la cola de solicitudes de Volley
        queue.add(jsonObjectRequest);
    }

    public interface OnPersonajeActualizadoListener {
        void onPersonajeActualizado(String mensaje);
        void onError(String mensajeError);
    }

    public void crearPersonaje(final Long usuarioId,final Personaje personaje, final OnPersonajeCreadoListener listener) {
        String url = URL_Personaje+"guardarPersonaje/"+usuarioId;

        // Convertir el objeto Personaje a JSON
        JSONObject personajeJson = new JSONObject();
        try {
            personajeJson.put("personajeNombre", personaje.getNombre());
            personajeJson.put("caracteristicaFuerza", personaje.getFuerza());
            personajeJson.put("caracteristicaDestreza", personaje.getDestreza());
            personajeJson.put("caracteristicaConstitucion", personaje.getConstitucion());
            personajeJson.put("caracteristicaInteligencia", personaje.getInteligencia());
            personajeJson.put("caracteristicaSabiduria", personaje.getSabiduria());
            personajeJson.put("caracteristicaCarisma", personaje.getCarisma());
            personajeJson.put("bonoCompetencia", personaje.getBonoCompetencia());
            if (personaje.getFoto() != null) {
                //personajeJson.put("foto", new String(personaje.getFoto()));
            }
        } catch (JSONException e) {
            e.printStackTrace();
            listener.onError("Error al crear JSON para el personaje");
            return;
        }

        // Crear una solicitud POST para crear el personaje
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, personajeJson,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        listener.onPersonajeCreado("Personaje creado exitosamente");
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        listener.onError("Error al crear el personaje");
                    }
                });

        // Agregar la solicitud a la cola de solicitudes de Volley
        queue.add(jsonObjectRequest);
    }

    public interface OnPersonajeCreadoListener {
        void onPersonajeCreado(String mensaje);
        void onError(String mensajeError);
    }
}



