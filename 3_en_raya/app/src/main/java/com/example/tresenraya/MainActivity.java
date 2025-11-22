package com.example.tresenraya;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

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
    private RadioGroup radioGroup;
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
        rbFacil = findViewById(R.id.rbFacil);
        rbDificil = findViewById(R.id.rbDificil);
        radioGroup = findViewById(R.id.radioGroup);
        tvTitulo = findViewById(R.id.tvTitulo);
        tvTurno = findViewById(R.id.tvTurno);

        //Inicializamos Array de casillas (ImageButtons)
        casillas = new ImageButton[9];
        // Asignamos los ids a cada posición del array
        casillas[0] = findViewById(R.id.imageButton1);
        casillas[1] = findViewById(R.id.imageButton2);
        casillas[2] = findViewById(R.id.imageButton3);
        casillas[3] = findViewById(R.id.imageButton4);
        casillas[4] = findViewById(R.id.imageButton5);
        casillas[5] = findViewById(R.id.imageButton6);
        casillas[6] = findViewById(R.id.imageButton7);
        casillas[7] = findViewById(R.id.imageButton8);
        casillas[8] = findViewById(R.id.imageButton9);

        // Configuramos el estado inicial (tablero deshabilitado)
        for (int i = 0; i < 9; i++) {
            tablero[i] = 0; // Inicialmente todas las casillas están vacías
            casillas[i].setEnabled(false); // Deshabilitamos todas las casillas
            casillas[i].setImageDrawable(null); // Quita el icono de estrellas inicial
        }

        // Implementamos los Listeners para los controles
        setupRadioButtonsListener();
        //setupStartStopButton();

    }

    // Metodos
    //Metodo para elegir el nivel de dificultad del juego
    private void setupRadioButtonsListener() {
        //Solo se puede seleccionar la dificultad si el juego No ha iniciado
        rbFacil.setOnClickListener(v -> {
            if (!juegoIniciado) {
                nivelDificultad = 1;
                //Mostramos mensaje flotante (toast)
                Toast.makeText(this, "Modo de juego: Fácil", Toast.LENGTH_SHORT).show();
            }
        });
        rbDificil.setOnClickListener(v -> {
            if (!juegoIniciado) {
                nivelDificultad = 2;
                //Mostramos mensaje flotante (toast)
                Toast.makeText(this, "Modo de juego: Difícil", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Metodo para iniciar el juego y pararlo
    private void setupStartStopButton() {
        btnStart.setOnClickListener(v -> {
            if (!juegoIniciado) {
                // Iniciamos el juego - MODO START
                if (nivelDificultad == 0) {
                    //Mostramos mensaje flotante (toast)
                    Toast.makeText(this, "Seleccione un nivel de dificultad primero", Toast.LENGTH_SHORT).show();
                    return;
                }
                iniciarPartida();
                // Mensaje de inicio
                Toast.makeText(this, "The game is running...", Toast.LENGTH_SHORT).show();
            } else {
                // Modo STOP / Reinicio
                resetearPartida();
                // Mensaje de finalización
                Toast.makeText(this, "The game is stopped", Toast.LENGTH_SHORT).show();

            }


        });
    }

    // Metodo para iniciar partida
    private void iniciarPartida() {
        juegoIniciado = true;
        btnStart.setText("STOP"); // Cambiamos el texto del botón de Start a Stop

        // 1. Resetear el tablero y habilitar casillas
        for (int i = 0; i < 9; i++) {
            tablero[i] = 0; // Limpiar la matriz lógica (0 = vacía)
            casillas[i].setEnabled(true); // Habilitar interacción con el tablero
            casillas[i].setImageDrawable(null); // Limpiar las imágenes
        }

        // 2. Deshabilitar la selección de dificultad
        rbFacil.setEnabled(false);
        rbDificil.setEnabled(false);

        // 3. Configurar el turno inicial y UI
        turno = 1; // El usuario (1) siempre empieza
        tvTitulo.setText(R.string.app_name);
        // Necesitaremos un metodo para actualizar el ícono de turno (ivTurno) más tarde.
    }

    private void resetearPartida() {
        juegoIniciado = false;
        btnStart.setText("START"); // Cambiamos el texto del botón de Stop a Start


        // 1. Deshabilitar el tablero y limpiar casillas
        for (int i = 0; i < 9; i++) {
            tablero[i] = 0;
            casillas[i].setEnabled(false);
            casillas[i].setImageDrawable(null);
        }

        // 2. Habilitar la selección de dificultad y limpiar la selección
        rbFacil.setEnabled(true);
        rbDificil.setEnabled(true);
        radioGroup.clearCheck();
        nivelDificultad = 0;
        tvTitulo.setText("3 en Raya");

        // NOTA: Aquí se deben detener los sonidos finales si estuvieran sonando (Req 10).
    }
}

