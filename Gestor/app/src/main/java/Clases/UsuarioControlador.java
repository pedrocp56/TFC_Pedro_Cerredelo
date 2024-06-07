package Clases;

import android.content.Context;
import android.util.Base64;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.cerredelo.gestor.ControladorPref;
import com.cerredelo.gestor.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;

public class UsuarioControlador {
    private RequestQueue queue;
    private static String URL_Usuario;
    private Context context;

    public UsuarioControlador(Context context) {
        this.context = context;
        String IP = ControladorPref.obtenerIP(context);
        URL_Usuario = IP + "Usuarios/";
        queue = Volley.newRequestQueue(context.getApplicationContext());
    }

    public void buscarUsuario(final Long usuarioID, final OnUsuarioEncontradoListener listener) {
        String url = URL_Usuario + "buscarUsuario/" + usuarioID;

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                response -> {
                    try {
                        JSONObject usuarioObj = new JSONObject(response);
                        Usuario usuario = new Usuario(usuarioObj);
                        listener.onUsuarioEncontrado(usuario);
                    } catch (JSONException e) {
                        e.printStackTrace();
                        String errorMessage = context.getString(R.string.error_Parsear);
                        listener.onError(errorMessage);
                    }
                },
                error -> {
                    String errorMessage = context.getString(R.string.error_Buscar_Usuario);
                    listener.onError(errorMessage);
                }
        );

        queue.add(stringRequest);
    }

    public interface OnUsuarioEncontradoListener {
        void onUsuarioEncontrado(Usuario usuario);
        void onError(String mensajeError);
    }

    public void loginUser(String username, String password, final OnLoginListener listener) {
        String loginUrl = URL_Usuario + "Login/" + username + "/" + password;

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, loginUrl, null,
                response -> {
                    try {
                        if (response.has("id")) {
                            Usuario login = new Usuario(response);
                            listener.onLoginSuccess(login);
                        } else {
                            String errorMessage = context.getString(R.string.error_Respuesta_Servidor);
                            listener.onLoginError(errorMessage);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        String errorMessage = context.getString(R.string.error_Procesar_Datos);
                        listener.onLoginError(errorMessage);
                    }
                },
                error -> {
                    String errorMessage;
                    if (error instanceof com.android.volley.ClientError) {
                        errorMessage = context.getString(R.string.error_LoginIncorecto_Usuario);
                    } else {
                        errorMessage = context.getString(R.string.error_Desconocido);
                    }
                    listener.onLoginError(errorMessage);
                }
        );

        queue.add(request);
    }

    public interface OnLoginListener {
        void onLoginSuccess(Usuario usuario);
        void onLoginError(String mensajeError);
    }

    public void crearNuevoUsuario(String username, String password, byte[] imagen, final OnUsuarioRegistradoListener listener) {
        String registerUrl = URL_Usuario + "guardarUsuario";
        JSONObject userData = new JSONObject();
        try {
            userData.put("nombre", username);
            userData.put("contrasenha", password);
            userData.put("estado", "Nueva cuenta");
            if (imagen != null) {
                String imagenBase64 = Base64.encodeToString(imagen, Base64.DEFAULT);
                userData.put("foto", imagenBase64);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            String errorMessage = context.getString(R.string.error_CrearDatos_Usuario);
            listener.onRegistroError(errorMessage);
            return;
        }

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, registerUrl, userData,
                response -> {
                    try {
                        boolean success = response.getBoolean("success");
                        if (success) {
                            listener.onUsuarioRegistrado();
                        } else {
                            String errorMessage = context.getString(R.string.error_Crear_Usuario);
                            listener.onRegistroError(errorMessage);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        String errorMessage = context.getString(R.string.error_Procesar_Datos);
                        listener.onRegistroError(errorMessage);
                    }
                },
                error -> {
                    String errorMessage = context.getString(R.string.error_Desconocido);
                    listener.onRegistroError(errorMessage);
                }
        );

        queue.add(request);
    }

    public interface OnUsuarioRegistradoListener {
        void onUsuarioRegistrado();
        void onRegistroError(String mensajeError);
    }

    public void actualizarDatosUsuario(Long userId,String username, String password, String estado, byte[] foto, final OnDatosActualizadosListener listener) {
        String updateUrl = URL_Usuario + "actualizarUsuario/" + userId;

        JSONObject userData = new JSONObject();
        try {
            userData.put("nombre", username);
            userData.put("contrasenha", password);
            userData.put("estado", estado);
            if (foto != null) {
                Gson gson = new GsonBuilder()
                        .registerTypeAdapter(byte[].class, new ImagenUtils())
                        .create();
                String fotoBase64 = gson.toJson(foto);
                userData.put("foto", fotoBase64);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            String errorMessage = context.getString(R.string.error_CrearDatos_Usuario);
            listener.onError(errorMessage);
            return;
        }

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.PUT, updateUrl, userData,
                response -> {
                    try {
                        String message = response.getString("message");
                        if ("success".equals(message)) {
                            listener.onDatosActualizados();
                        } else {
                            String errorMessage = context.getString(R.string.error_Actualizar_Usuario);
                            listener.onError(errorMessage);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        String errorMessage = context.getString(R.string.error_Respuesta_Servidor);
                        listener.onError(errorMessage);

                    }
                },
                error -> {
                    String errorMessage = context.getString(R.string.error_Conectar_Servidor);
                    listener.onError(errorMessage);
                }
        );

        queue.add(request);
    }

    public interface OnDatosActualizadosListener {
        void onDatosActualizados();
        void onError(String mensajeError);
    }


}

