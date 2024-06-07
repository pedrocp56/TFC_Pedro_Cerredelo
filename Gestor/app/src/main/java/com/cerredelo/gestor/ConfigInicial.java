package com.cerredelo.gestor;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class ConfigInicial extends AppCompatActivity {

    private Spinner languageSpinner;
    private EditText ipInput;
    private Button confirmButton;
    private Button volverButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config_inicial);

        // Initialize views
        languageSpinner = findViewById(R.id.languageSpinner);
        ipInput = findViewById(R.id.ipInput);
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
                String idioma="es";
                switch (selectedLanguage) {
                    case "Español":
                        idioma = "es";
                        break;
                    case "Gallego":
                        idioma = "gl";
                        break;
                    default:
                        idioma = "es";
                        break;
                }
                ControladorPref.guardarIdioma(ConfigInicial.this, idioma);
                setLocale(idioma);

                if(!ipInput.getText().toString().isEmpty()) {
                    String ipAddress = ipInput.getText().toString();
                    ControladorPref.guardarIP(ConfigInicial.this, ipAddress);
                }


                // Optional: Show a confirmation message
                Toast.makeText(ConfigInicial.this, ConfigInicial.this.getString(R.string.confGuardada), Toast.LENGTH_SHORT).show();
            }
        });

        // Set up the volver button click listener
        volverButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Go back to the previous activity
                Intent intent = new Intent(ConfigInicial.this, Login.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void loadPreferences() {

        String savedLanguage = ControladorPref.obtenerIdioma(ConfigInicial.this);
        String savedIp = ControladorPref.obtenerIP(ConfigInicial.this);

        // Set the IP input field
        ipInput.setText(savedIp);

        // Set the selected item in the language spinner
        ArrayAdapter<CharSequence> adapter = (ArrayAdapter<CharSequence>) languageSpinner.getAdapter();
        if (adapter != null) {
            int position;
            switch (savedLanguage) {
                case "es":
                    position =0 ;
                    break;
                case "gl":
                    position = 1;
                    break;
                default:
                    position = 0;
                    break;
            }
            languageSpinner.setSelection(position);
        }
    }
    public void setLocale(String lang) {
        Locale locale = new Locale(lang);
        Locale.setDefault(locale);
        Resources res = getResources();
        Configuration config = res.getConfiguration();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            config.setLocale(locale);
        } else {
            config.locale = locale;
        }
        res.updateConfiguration(config, res.getDisplayMetrics());

        // Save the language in SharedPreferences
        ControladorPref.guardarIdioma(this,lang);

        // Reload the activity to apply the language
        Intent refresh = new Intent(this, ConfigInicial.class);
        startActivity(refresh);
        finish();
    }
}


