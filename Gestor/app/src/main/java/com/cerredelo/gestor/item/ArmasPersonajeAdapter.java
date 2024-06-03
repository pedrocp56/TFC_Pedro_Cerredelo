package com.cerredelo.gestor.item;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.cerredelo.gestor.ArmasPersonaje;
import com.cerredelo.gestor.EditarArmaPersonaje;
import com.cerredelo.gestor.R;
import com.cerredelo.gestor.VerArmaPersonaje;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Clases.Arma;
import Clases.ArmaControlador;
import Clases.ArmaPersonaje;

public class ArmasPersonajeAdapter extends ArrayAdapter<ArmaPersonaje> {
    private Map<Long, String> nombresArmas = new HashMap<>();
    private Context context;

    public ArmasPersonajeAdapter(Context context, List<ArmaPersonaje> armasPersonaje) {
        super(context, 0, armasPersonaje);
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.activity_item_arma_personaje, parent, false);
        }

        TextView nombreTextView = convertView.findViewById(R.id.nombreArmaPersonaje);
        Button btnVer = convertView.findViewById(R.id.btnVerArmaPersonaje);
        Button btnEditar = convertView.findViewById(R.id.btnEditarArmaPersonaje);
        Button btnEliminar = convertView.findViewById(R.id.btnEliminarArmaPersonaje);

        final ArmaPersonaje armaPersonaje = getItem(position);

        if (armaPersonaje != null) {
            // Verificar si ya tenemos el nombre del arma en el caché
            if (nombresArmas.containsKey(armaPersonaje.getArmaId())) {
                nombreTextView.setText(nombresArmas.get(armaPersonaje.getArmaId()));
            } else {
                // Buscar el nombre del arma si no está en el caché
                buscarArma(armaPersonaje.getArmaId(), nombreTextView);
            }

            btnVer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Lógica para ver la información detallada del arma
                    Intent intent = new Intent(context, VerArmaPersonaje.class);
                    intent.putExtra("personajeId", armaPersonaje.getPersonajeId());
                    intent.putExtra("armaId", armaPersonaje.getArmaId());
                    intent.putExtra("personajeNombre", ((ArmasPersonaje) context).personajeNombre);
                    intent.putExtra("armaNombre", nombresArmas.get(armaPersonaje.getArmaId()));
                    context.startActivity(intent);
                    ((ArmasPersonaje) context).finish();
                }
            });

            btnEditar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Lógica para editar el arma del personaje
                    // Lógica para ver la información detallada del arma
                    Intent intent = new Intent(context, EditarArmaPersonaje.class);
                    intent.putExtra("armaId", armaPersonaje.getArmaId());
                    intent.putExtra("personajeId", armaPersonaje.getPersonajeId());
                    intent.putExtra("personajeNombre", ((ArmasPersonaje) context).personajeNombre);
                    intent.putExtra("armaNombre", nombresArmas.get(armaPersonaje.getArmaId()));
                    context.startActivity(intent);
                    ((ArmasPersonaje) context).finish();
                }
            });

            btnEliminar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Lógica para eliminar el arma del personaje
                    mostrarDialogoEliminar(armaPersonaje,nombresArmas.get(armaPersonaje.getArmaId()));
                }
            });
        }

        return convertView;
    }

    private void buscarArma(Long armaId, final TextView nombreTextView) {
        ArmaControlador armaControlador = new ArmaControlador(context);
        armaControlador.buscarArma(armaId, new ArmaControlador.OnArmaEncontradaListener() {
            @Override
            public void onArmaEncontrada(Arma arma) {
                nombresArmas.put(armaId, arma.getNombre());
                nombreTextView.setText(arma.getNombre());
            }

            @Override
            public void onError(String mensajeError) {
                // Manejar el error si no se puede encontrar el arma
                Toast.makeText(context, mensajeError, Toast.LENGTH_SHORT).show();
            }
        });
    }
    // Método para mostrar el diálogo de confirmación de eliminación
    private void mostrarDialogoEliminar(final ArmaPersonaje armaPersonaje, final String nombreArma) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this.getContext());
        builder.setTitle("Eliminar Arma del Personaje");
        //implementar buscar nombre
        builder.setMessage("¿Estás seguro de que quieres eliminar el arma '" + nombreArma+ "' del personaje?");

        // Agregar el botón "Sí" para confirmar la eliminación
        builder.setPositiveButton("Sí", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Lógica para eliminar el arma del personaje
                ((ArmasPersonaje) context).eliminarArmaPersonaje(armaPersonaje);
            }
        });

        // Agregar el botón "Cancelar" para cancelar la eliminación
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Cerrar el diálogo y no hacer nada
                dialog.dismiss();
            }
        });

        // Mostrar el diálogo
        builder.show();
    }

}

