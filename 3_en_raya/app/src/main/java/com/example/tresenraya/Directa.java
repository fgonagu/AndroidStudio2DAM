package com.example.tresenraya;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
public class Directa extends AndroidJugada{
    private final int ultimaJugadaUsuario;

    // Mapa que define las casillas vecinas para cada posición (0-8)
    //Ejemplo: la casilla 0 tiene como vecinas a 1, 3 y 4
    private static final int[][] MAPA_VECINAS = {
            {1, 3, 4},              //0
            {0, 2, 3, 4, 5},        //1
            {1, 4, 5},              //2
            {0, 1, 4, 6, 7},        //3
            {0, 1, 2, 3, 5, 6, 7, 8}, // 4 CENTRO
            {1, 2, 4, 7, 8},        //5
            {3, 4, 7},              //6
            {3, 4, 5, 6, 8},        //7
            {4, 5, 7}               //8
    };
    //Constructor
    public Directa(int[] tablero, int ultimaJugadaUsuario) {
     super(tablero);
     this.ultimaJugadaUsuario = ultimaJugadaUsuario;
    }
    @Override
    public int play() {
        // 1. Encontramos las casillas VECINAS que estén LIBRES
        List<Integer> vecinasLibres = new ArrayList<>();

        // Si es la primera jugada del juego (ultimaJugadaUsuario = -1) usamos el modo aleatorio
        if(ultimaJugadaUsuario != -1){
            int[] vecinas = MAPA_VECINAS[ultimaJugadaUsuario];
            for (int vecina : vecinas) {
                // Si la casilla vecina está vacía (0) la agregamos a la lista de opciones válidas
                if(tablero[vecina] == 0){
                    vecinasLibres.add(vecina);
                }
            }
        }
        // 2. MOVIMIENTO
        if(!vecinasLibres.isEmpty()){
            //REGLA PRINCIPAL: ADYACENTE LIBRE = selecciona una casilla vecina de forma aleatoria
            Random random = new Random();
            int indiceAleatorio = random.nextInt(vecinasLibres.size());
            return vecinasLibres.get(indiceAleatorio);
        } else {
            // REGLA SECUNDARIA: Si no hay vecinas libres o es el primer turno, se selecciona una casilla aleatoria
            List<Integer> todasLibres = new ArrayList<>();
            for(int i = 0; i < tablero.length; i++){
                if(tablero[i] == 0){
                    todasLibres.add(i);
                }
            }
            if (todasLibres.isEmpty()){
                return -1;
            }
            Random random = new Random();
            int indiceAleatorio = random.nextInt(todasLibres.size());
            return todasLibres.get(indiceAleatorio);
        }

    }
}
