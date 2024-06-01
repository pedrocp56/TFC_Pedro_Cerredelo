package Clases;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class ArmaPersonajeControlador {
    private RequestQueue queue;

    public ArmaPersonajeControlador(Context context) {
        // Inicializar la cola de solicitudes Volley en el constructor
        queue = Volley.newRequestQueue(context.getApplicationContext());
    }
}
