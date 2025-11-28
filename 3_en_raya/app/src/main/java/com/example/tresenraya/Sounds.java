package com.example.tresenraya;

import android.content.Context;
import android.media.MediaPlayer;
public class Sounds {
    private final MediaPlayer userPlayer;
    private final MediaPlayer androidPlayer;
    private final MediaPlayer winMusic;
    private final MediaPlayer loseMusic;
    private final MediaPlayer drawMusic;
    private MediaPlayer backgroundMusicPlayer;

    //Constructor
    public Sounds(Context context) {
        //Inicialiamos los MediaPlayers con los recursos en la carpera raw
        userPlayer = MediaPlayer.create(context, R.raw.sounduser);
        androidPlayer = MediaPlayer.create(context, R.raw.soundandroid);
        winMusic = MediaPlayer.create(context, R.raw.winmusic);
        loseMusic = MediaPlayer.create(context, R.raw.losemusic);
        drawMusic = MediaPlayer.create(context, R.raw.drawmusic);
        backgroundMusicPlayer = MediaPlayer.create(context, R.raw.backgroundmusic);
    }

    //Musica de fondo en bucle
    public void startBackgroundMusic(){
        if(backgroundMusicPlayer == null) {
            // Aseguramos que la música se repita en bucle
            backgroundMusicPlayer.setLooping(true);

            // Ajustamos el volumen de la música al 50%
            backgroundMusicPlayer.setVolume(0.5f, 0.5f);
        }
        // Iniciamos la reproducción de la música
        if (!backgroundMusicPlayer.isPlaying()) {
            backgroundMusicPlayer.start();
        }
    }

    // Detiene y libera la música de fondo
    public void stopBackgroundMusic(){
        if(backgroundMusicPlayer != null && backgroundMusicPlayer.isPlaying()){
            backgroundMusicPlayer.pause(); // Pausa la música
        }
    }

    public void playUserSound() {
        if (userPlayer != null) {
            userPlayer.seekTo(0); // Reinicia el sonido al principio si ya estaba reproduciéndose
            userPlayer.start();
        }
    }

    public void playAndroidSound() {
        if (androidPlayer != null) {
            androidPlayer.seekTo(0); // Reinicia el sonido al principio si ya estaba reproduciéndose
            androidPlayer.start();
        }

    }

    public void playWinMusic() {
        if (winMusic != null) {
            stopMusic(); // Detiene cualquier música anterior
            winMusic.start();
        }
    }

    public void playLoseMusic() {
        if (loseMusic != null) {
            stopMusic(); // Detiene cualquier música anterior
            loseMusic.start();
        }
    }

    public void playDrawMusic() {
        if (drawMusic != null) {
            stopMusic(); // Detiene cualquier música
            drawMusic.start();
        }
    }

    // Metodo para detener y reinicar toda la música
    public void stopMusic() {
        if (winMusic != null && winMusic.isPlaying()) {
            winMusic.pause();
        }
        if (loseMusic != null && loseMusic.isPlaying()) {
            loseMusic.pause();
        }
        if (drawMusic != null && drawMusic.isPlaying()) {
            drawMusic.pause();
        }
        if (winMusic != null) {
            winMusic.seekTo(0);
        }
        if (loseMusic != null) {
            loseMusic.seekTo(0);
        }
        if (drawMusic != null) {
            drawMusic.seekTo(0);
        }

    }

    // Metodo para liberar recursos cuando la Activity se destruye
    public void release() {
        if (userPlayer != null) {
            userPlayer.release();
        }
        if (androidPlayer != null) {
            androidPlayer.release();
        }
        if (winMusic != null) {
            winMusic.release();
        }
        if (loseMusic != null) {
            loseMusic.release();
        }
        if (drawMusic != null) {
            drawMusic.release();
        }
        if (backgroundMusicPlayer != null){
            backgroundMusicPlayer.stop();
            backgroundMusicPlayer.release();
            backgroundMusicPlayer = null;
        }
    }
}
