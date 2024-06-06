package com.cerredelo.gestor;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Ajustes extends AppCompatActivity {

    private Spinner languageSpinner;
    private Button confirmButton;
    private Button volverButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajustes);

        // Initialize views
        languageSpinner = findViewById(R.id.languageSpinner);
        confirmButton = findViewById(R.id.confirmButton);
        volverButton = findViewById(R.id.volverButton);

        // Set up the spinner with language options
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.idiomas, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        languageSpinner.setAdapter(adapter);

        // Load saved preferences and set views
        loadPreferences();

        // Set up the confirm button click listener
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get selected language and IP
                String selectedLanguage = languageSpinner.getSelectedItem().toString();

                // Save selected language and IP to SharedPreferences
                SharedPreferences sharedPreferences = getSharedPreferences("ConfigPreferences", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("Idioma", selectedLanguage);
                editor.apply();

                // Optional: Show a confirmation message
                Toast.makeText(Ajustes.this, "Configuración guardada", Toast.LENGTH_SHORT).show();
            }
        });

        // Set up the volver button click listener
        volverButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Go back to the previous activity
                Intent intent = new Intent(Ajustes.this, PantallaPrincipal.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void loadPreferences() {
        SharedPreferences sharedPreferences = getSharedPreferences("ConfigPreferences", Context.MODE_PRIVATE);
        String savedLanguage = sharedPreferences.getString("Idioma", "Español");

        // Set the selected item in the language spinner
        ArrayAdapter<CharSequence> adapter = (ArrayAdapter<CharSequence>) languageSpinner.getAdapter();
        if (adapter != null) {
            int position = adapter.getPosition(savedLanguage);
            languageSpinner.setSelection(position);
        }
    }
}