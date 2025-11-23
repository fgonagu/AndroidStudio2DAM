package com.example.tresenraya;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class ResultadoActivity extends Activity {

    public static final String EXTRA_RESULTADO = "com.example.tresenraya.resultado";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultado); // Asegúrate de crear este layout

        TextView tvResultado = findViewById(R.id.tvResultadoFinal); // Asumiendo este ID

        // Recibir el mensaje de resultado (ej: "You Win!")
        String resultado = getIntent().getStringExtra(EXTRA_RESULTADO);

        if (resultado != null) {
            tvResultado.setText(resultado);
        } else {
            tvResultado.setText("Partida finalizada.");
        }

        Button btnJugarNuevo = findViewById(R.id.btnJugarNuevo);

        // Implementación del OnClickListener
        btnJugarNuevo.setOnClickListener(v -> {
            // Regresar al inicio del flujo: la pantalla principal del juego.
            // Opción 1: Volver directamente a MainActivity
            Intent intent = new Intent(ResultadoActivity.this, MainActivity.class);

            // Flags para limpiar la pila de Activities y que el usuario no pueda volver al resultado.
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);

            startActivity(intent);
            finish(); // Cierra ResultadoActivity
        });
    }
}