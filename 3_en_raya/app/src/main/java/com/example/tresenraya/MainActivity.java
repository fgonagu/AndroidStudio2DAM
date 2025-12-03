package com.example.tresenraya;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    //Variables Vista
    private ImageButton[] casillas; //Array para manejar los 9 botones
    private Button btnStart;
    private RadioButton rbFacil, rbDificil;
    private RadioGroup radioGroup;
    private TextView tvTitulo, tvTurno;
    private ImageView ivTurno;

    //Variables Lógica del juego
    private boolean juegoIniciado = false; //Controla si el juego está activo
    private final int[] tablero = new int[9];
    private int turno = 1; //1 -> Turno del usuario (empieza el usuario por defecto)
    private int nivelDificultad = 0; // 0 -> no seleccionado, 1 -> Fácil, 2 -> Difícil
    private Sounds gameSounds;
    // Combinaciones ganadoras del tablero
    private final int[][] LINEAS_GANADORAS = {
            {0,1,2}, {3,4,5}, {6,7,8}, // Filas Horizontales
            {0,3,6}, {1,4,7}, {2,5,8}, // Columnas Verticales
            {0,4,8}, {2,4,6}            // Diagonales
    };
    // Variable Handler para gestionar el mecanismo de retraso para el movimiento de la máquina (2 a 5 segundos)
    private final Handler handler = new Handler(Looper.getMainLooper());
    //Variable para guardar la última jugada del usuario
    private int ultimaJugadaUsuario = -1; // Almacena el índice de la última casilla marcada por el usuario

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Inicializamos el Gestor de Sonidos
        gameSounds = new Sounds(this);

        // Vinculación de vistas
        btnStart = findViewById(R.id.btn_Start);
        rbFacil = findViewById(R.id.rbFacil);
        rbDificil = findViewById(R.id.rbDificil);
        radioGroup = findViewById(R.id.radioGroup);
        tvTitulo = findViewById(R.id.tvTitulo);
        tvTurno = findViewById(R.id.tvTurno);
        ivTurno = findViewById(R.id.ivTurno);

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
        setupStartStopButton();
        setupBoardListeners();
    }

    // METODOS
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
    @SuppressLint("SetTextI18n")
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
    }

    @SuppressLint("SetTextI18n")
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

        // Detenemos la música final si está sonando
        if(gameSounds != null){
            gameSounds.stopMusic();
        }
    }

    // Metodo para almacenar el índice del array (i) en el tag de cada botón (para saber qué casilla fue pulsada cuando se dispara el evento onClick)
    private void setupBoardListeners(){
        for (int posicion = 0; posicion < 9; posicion++) {

            // Usamos el tag para asociar la vista con el índice del tablero lógico
            casillas[posicion].setTag(posicion);
            casillas[posicion].setOnClickListener(v -> {
                // Solo permitimos la jugada si el juego está iniciado y es el turno del usuario (1)
                if(juegoIniciado && turno == 1){
                    // Obtenemos la posición guardada en el tag de la vista pulsada
                    int pos = (int) v.getTag();
                    jugadaUsuario(pos);
                }
            });
        }
    }

    // Metodo para registrar el movimiento del usuario, actualizar la UI, reproducir el sonido y pasar el turno
    private void jugadaUsuario(int posicion){
        // 1. Verificamos que la casilla esté vacía
        if(tablero[posicion] == 0){
            // 2. Actualizamos el tablero lógico
            tablero[posicion] = 1; // 1 = Usuario

            // Registramos la jugada del usuario para su posterior uso en la IA (nivel dificil del juego)
            ultimaJugadaUsuario = posicion;


            //3. Actualizamos la UI
            casillas[posicion].setBackgroundColor(android.graphics.Color.TRANSPARENT); // Establecemos el fondo del botón a transparente
            casillas[posicion].setImageResource(R.drawable.usericon);
            casillas[posicion].setEnabled(false); // Deshabilitamos la casilla pulsada

            // 4. Reproducimos el sonido
            gameSounds.playUserSound();

            // 5. Comprobamos estado del juego y cambiamos el turno
            if(comprobarEstadoJuego()){
                turno = 2; // Cambiamos el turno de Android
                actualizarIndicadorTurno(); // Actualizamos ivTurno

                // Ejecutamos la jugada de Android
                ejecutarTurnoAndroid();
            }
        }
    }
    // Metodo para comprobar si ha hecho 3 en raya (GANADOR)
    private boolean comprobarGanador(int jugador){
        for(int[] linea : LINEAS_GANADORAS){
            if(tablero[linea[0]] == jugador && tablero[linea[1]] == jugador && tablero[linea[2]] == jugador){
                return true; // Devuelve true si encuentra una línea
            }
        }
        return false;
    }

    // Metodo para comprobar el estado del juego. Devuelve true si el juego ha terminado (VICTORIA o EMPATE)
    private boolean comprobarEstadoJuego(){
        // 1. Verificamos si el usuario (1) ha ganado
        if(comprobarGanador(1)){
            finalizarPartida(1); // 1 = Usuario gana
            return false;
        }
        // 2. Verificamos si Android (2) ha ganado
        if(comprobarGanador(2)){
            finalizarPartida(2); // 2 = Android gana
            return false;
        }
        // 3. Verificamos si hay empate
        boolean tableroLleno = true;
        for(int casilla : tablero){
            if(casilla == 0){ // Si encontramos una casilla vacía
                tableroLleno = false;
                break;
            }
        }
        if(tableroLleno){
            finalizarPartida(0); // 0 = Empate
            return false;
            }
        // El juego continúa
        return true;
        }

    //Metodo para finalizar el juego: detiene la partida y muestra el resultado
    @SuppressLint("SetTextI18n")
    private void finalizarPartida(int resultado){
        // 1. Detenemos el juego y deshabilitamos casillas
        juegoIniciado = false;
        for (ImageButton casilla : casillas) {
            casilla.setEnabled(false);
        }
        // Pausamos la música de fondo ANTES de poner la música de resultados
        gameSounds.stopBackgroundMusic();

        // 2. Mostramos resultado y reproducimos música
        String mensajeResultado;
        if(resultado == 1){
            mensajeResultado = "You Win!";
            gameSounds.playWinMusic(); // Música de victoria
        } else if (resultado == 2){
            mensajeResultado = "Android Win!";
            gameSounds.playLoseMusic(); // Música de derrota
        } else {
            mensajeResultado = "Ooops!, nobody Wins";
            gameSounds.playDrawMusic(); // Música de empate
        }
        // Mostramos el resultado en el Textview superior y como Toast
        tvTitulo.setText("Partida Finalizada");
        Toast.makeText(this, mensajeResultado, Toast.LENGTH_SHORT).show();

        // 4. LANZAMOS LA PANTALLA DE RESULTADOS
        Intent intent = new Intent(this, ResultadoActivity.class);
        // Pasamos el resultado como extra al intent
        intent.putExtra(ResultadoActivity.EXTRA_RESULTADO, mensajeResultado);
        startActivity(intent);

    }

    // Metodo para actualizar el icono de turno (en ivTurno pondrá el icono del usuario o de Android)
    // y actualizará el texto del TextView (tvTurno)
    @SuppressLint("SetTextI18n")
    private void actualizarIndicadorTurno(){
        if(turno == 1){
            // Turno del usuario (1)
            ivTurno.setImageResource(R.drawable.usericon);
            tvTurno.setText("Usuario");
        } else if(turno == 2){
            // Turno de Android (2)
            ivTurno.setImageResource(R.drawable.androidicon);
            tvTurno.setText("Android");
        }
    }
    // Metodo para ejecutar el turno de Android
    private void ejecutarTurnoAndroid(){
        // Creamos un tiempo aleatorio entre 2 y 5 segundos para simular el movimiento del Android
        Random r = new Random();
        int delay = r.nextInt(3001) + 2000; // Genera un valor entre 2000 y 5000 milisegundos

        // 1. Dehabilitamos el tablero para que el usuario no interrumpa la espera del Android
        for (ImageButton casilla : casillas) {
            casilla.setEnabled(false);
        }

        // 2. Definimos la tarea (Runnable) a ejecutar después del retraso
        Runnable runnableAndroid = () -> {
            // Seleccionamos la estrategia de la máquina (aleatoria)
            AndroidJugada ia;
            if(nivelDificultad == 1){
                ia = new Aleatoria(tablero); // Nivel 1: Aleatorio
            } else {
                //Nivel 2: Marcará la casilla adyacente a la última jugada del usuario (le pasamos el tablero y la última jugada del usuario)
                ia = new Directa(tablero, ultimaJugadaUsuario);
            }
            // Obtenemos la posición de la jugada
            int posicion = ia.play();

            // Ejecutamos el movimiento si es válido
            if(posicion != -1){
                jugarAndroid(posicion);
            }

            // Volvemos a habilitar el tablero si el juego continúa
            if(juegoIniciado && turno == 1){
                for (int i = 0; i < 9; i++) {
                    if(tablero[i] == 0){
                        casillas[i].setEnabled(true);
                    }
                }
            }
        };
        // 3. Ejecutamos la tarea con el delay
        handler.postDelayed(runnableAndroid, delay);
    }

    // Metodo que realiza el movimiento de Android
    private void jugarAndroid(int posicion){
        // 1. Actualizamos el tablero lógico
        tablero[posicion] = 2; // 2 = Android

        // 2. Actualizamos la UI
        casillas[posicion].setBackgroundColor(android.graphics.Color.TRANSPARENT); // Establecemos el fondo del botón a transparente
        casillas[posicion].setImageResource(R.drawable.androidicon);
        casillas[posicion].setEnabled(false); // Deshabilitamos la casilla pulsada

        // 3. Reproducimos el sonido
        gameSounds.playAndroidSound();

        // 4. Comprobamos el estado del juego y cambiamos el turno
        if(comprobarEstadoJuego()){
            turno = 1; // Cambiamos el turno de Usuario
            actualizarIndicadorTurno(); // Actualizamos ivTurno
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        // La música se reanuda cuando la Activity vuelve a estar en primer plano
        if (gameSounds != null) {
            gameSounds.startBackgroundMusic();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        // La música se pausa cuando la Activity pierde el foco (ej: al abrir la pantalla de resultados)
        if (gameSounds != null) {
            gameSounds.stopBackgroundMusic();
        }
    }

    // Sobreescribimos el metodo onDestroy() para liberar los recursos de sonido del MediaPlayer cuando la Activity se cierra
    //y para detener cualquier tarea pendiente del Handler
    @Override
    protected void onDestroy() {
        super.onDestroy();
        //Detenemos cualquier tarea pendiente del Handler
        handler.removeCallbacksAndMessages(null);
        if (gameSounds != null) {
            gameSounds.release();
        }

    }
}

