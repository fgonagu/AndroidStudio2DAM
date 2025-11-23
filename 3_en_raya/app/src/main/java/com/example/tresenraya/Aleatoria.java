package com.example.tresenraya;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Aleatoria extends AndroidJugada {

    // Constructor
    public Aleatoria(int[] tablero) {
        super(tablero);
    }

    @Override
    public int play() {
        // 1. Encontramos todas las casillas que están vacías y las guardamos en un arraylist
        List<Integer> vacias = new ArrayList<>();
        for (int i = 0; i < tablero.length; i++) {
            if (tablero[i] == 0){
                vacias.add(i);
            }
        }
        // 2. Elegimos una posicion al azar
        if(vacias.isEmpty()){
            return -1;
        }
        Random random = new Random();
        int indiceAleatorio = random.nextInt(vacias.size());
        return vacias.get(indiceAleatorio);
    }
}
