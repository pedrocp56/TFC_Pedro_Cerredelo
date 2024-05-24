package com.cerredelo.gestor;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class PantallaPrincipal extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_principal);

        Button logoutButton = findViewById(R.id.logout_button);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Aquí puedes agregar cualquier lógica necesaria para cerrar la sesión
                // Por ejemplo, limpiar datos de sesión o cerrar la sesión en el servidor

                // Luego, navega de vuelta a la pantalla de inicio
                Intent intent = new Intent(PantallaPrincipal.this, Login.class);
                startActivity(intent);
                finish(); // Cerrar la actividad actual
            }
        });
    }
}
