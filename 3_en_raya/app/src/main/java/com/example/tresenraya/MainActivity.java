package com.example.tresenraya;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    //Variables Vista
    private ImageButton[] casillas; //Array para manejar los 9 botones
    private Button btnStart;
    private RadioButton rbFacil, rbDificil;
    private RadioButton radioGroup;
    private TextView tvTitulo, tvTurno;

    //Variables Lógica del juego
    private boolean juegoIniciado = false; //Controla si el juego está activo
    private int[] tablero = new int[9];
    private int turno = 1; //1 -> Turno del usuario (empieza el usuario por defecto)
    private int nivelDificultad = 0; // 0 -> no seleccionado, 1 -> Fácil, 2 -> Difícil

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Vinculación de vistas
        btnStart = findViewById(R.id.btn_Start);








    }
}