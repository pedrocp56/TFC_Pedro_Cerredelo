package com.cerredelo.gestor;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;


import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import Clases.Usuario;
import Helper.Variables;

public class MainActivity extends AppCompatActivity {

    RequestQueue queue;
    String baseUrl =  Variables.IP+"Usuarios/verUsuarios";

    List<String> usuarios = new ArrayList<String>();
    ListView listUsuarios;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        queue = Volley.newRequestQueue(this);
        GetUsuarios();
        listUsuarios = (ListView) findViewById(R.id.listUsuarios);

    }

    private void GetUsuarios() {
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET,baseUrl,null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if (response.length() > 0) {
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            JSONObject obj = response.getJSONObject(i);
                                    /*
                                    Usuario u = new Usuario();
                                    u.setId( Long.parseLong(obj.get("id").toString()));
                                    u.setNombre(obj.get("nombre").toString());
                                    u.setContrasenha(obj.get("contrasenha").toString());
                                    u.setEstado(obj.get("estado").toString());
                                    u.setFoto(obj.get("foto").toString());
                                    */
                            Usuario u = new Usuario(obj);

                            usuarios.add(u.getId().toString() + " - " + u.getNombre());

                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, usuarios);
                            listUsuarios.setAdapter(adapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error instanceof ServerError) {
                    Log.i("TAG", "Fallo en los servidores");
                }
                if (error instanceof NoConnectionError) {
                    Log.i("TAG", "Fallo en la conexion a interner");
                }
            }
        });
        queue.add(request);
    }
}