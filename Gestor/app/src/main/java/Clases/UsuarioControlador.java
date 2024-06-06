package Clases;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.ClientError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.cerredelo.gestor.ControladorPref;
import com.cerredelo.gestor.Login;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import Helper.Variables;

public class UsuarioControlador {
    private RequestQueue queue;
    private static String URL_Usuario ;

    public UsuarioControlador(Context context) {
        // Inicializar la cola de solicitudes Volley en el constructor
        String IP= ControladorPref.obtenerIP(context);
        URL_Usuario= IP+"Usuarios/";
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

    public void loginUser(String username, String password, final OnLoginListener listener) {
        // URL del endpoint de tu API para verificar las credenciales del usuario
        String loginUrl = URL_Usuario+"Login/" + username + "/" + password;

        // Crear la solicitud JSON para realizar la verificación de las credenciales del usuario
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, loginUrl, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            // Comprobar si la respuesta contiene datos pertinentes
                            if (response.has("id")) {
                                // Convertir el JSONObject de la respuesta en un objeto Usuario
                                Usuario login = new Usuario(response);
                                // Llamar al listener con el usuario encontrado
                                listener.onLoginSuccess(login);
                            } else {
                                // Si no hay "id", considerar como error
                                listener.onLoginError("Respuesta del servidor no válida");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            // Error al procesar la respuesta JSON
                            listener.onLoginError("Error de procesamiento de datos");
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Manejar los errores de la solicitud aquí
                        String errorMessage;
                        if (error instanceof ClientError) {
                            errorMessage = "Nombre de usuario o contraseña incorrectos";
                        } else if (error instanceof NoConnectionError) {
                            errorMessage = "No se puede conectar al servidor";
                        } else if (error instanceof TimeoutError) {
                            errorMessage = "Tiempo de espera agotado";
                        } else if (error instanceof ServerError || error instanceof NetworkError) {
                            errorMessage = "Error del servidor, inténtelo de nuevo más tarde";
                        } else if (error instanceof AuthFailureError) {
                            errorMessage = "Error de autenticación";
                        } else {
                            errorMessage = "Error desconocido";
                        }
                        listener.onLoginError(errorMessage);
                    }
                });

        // Agregar la solicitud a la cola de solicitudes
        queue.add(request);
    }

    public interface OnLoginListener {
        void onLoginSuccess(Usuario usuario);
        void onLoginError(String mensajeError);
    }


    public void crearNuevoUsuario(String username, String password,byte[] imagen, final OnUsuarioRegistradoListener listener) {
        String registerUrl = URL_Usuario+"guardarUsuario";
        JSONObject userData = new JSONObject();
        try {
            userData.put("nombre", username);
            userData.put("contrasenha", password);
            userData.put("estado", "Nueva cuenta");
            userData.put("foto", imagen != null ? new JSONArray(imagen) : null); // Convertir byte[] a JSONArray
        } catch (JSONException e) {
            e.printStackTrace();
            listener.onRegistroError("Error al crear los datos del usuario");
            return;
        }

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, registerUrl, userData,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            boolean success = response.getBoolean("success");
                            if (success) {
                                listener.onUsuarioRegistrado();
                            } else {
                                listener.onRegistroError("Fallo al registrar el usuario");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            listener.onRegistroError("Error de procesamiento de datos");
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        listener.onRegistroError("Error: " + error.toString());
                    }
                });

        queue.add(request);
    }


    public interface OnUsuarioRegistradoListener {
        void onUsuarioRegistrado();
        void onRegistroError(String mensajeError);
    }


}
