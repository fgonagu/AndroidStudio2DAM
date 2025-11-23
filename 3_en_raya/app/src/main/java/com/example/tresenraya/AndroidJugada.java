package com.example.tresenraya;

// Clase base que define el comportamiento de una jugada en el juego.

public abstract class AndroidJugada {
    protected int[] tablero;

    // Constructor que recibe el tablero actual del juego.
    public AndroidJugada(int[] tablero) {
        this.tablero = tablero; // Guarda el tablero actual en el objeto.

    }

    // Metodo abstracto que debe ser implementado por las subclases para realizar una jugada.
    // Calcula y devuelve la posicion (indice 0-8) donde Android debe jugar
    public abstract int play();
}
