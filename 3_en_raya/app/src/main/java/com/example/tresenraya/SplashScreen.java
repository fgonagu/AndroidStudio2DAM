package com.example.tresenraya;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

public class SplashScreen extends Activity {

    // Tiempo de espera de 3 segundos (3000 ms)
    private static final int SPLASH_DELAY = 3000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen); // Asegúrate de crear este layout

        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                // Lanzar la Activity principal
                Intent intent = new Intent(SplashScreen.this, MainActivity.class);
                startActivity(intent);

                // Finalizar esta Activity para que no se pueda volver atrás
                finish();
            }
        }, SPLASH_DELAY);
    }
}
