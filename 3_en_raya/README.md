# Tres en Raya (Tic-Tac-Toe)

Aplicación para Android desarrollada en **Java** que implementa el clásico juego del tres en raya (Tic-Tac-Toe) para un solo jugador, ofreciendo una experiencia interactiva y multimedia contra la máquina.

---

## Características Destacadas

* **Modo de Juego:** Un solo jugador (Humano vs. Android).
* **Pantalla de Bienvenida (Splash Screen):** Introducción de 3 segundos antes de iniciar la Activity principal.
* **Doble Nivel de Dificultad (IA):**
    * **Fácil (Aleatorio):** La IA realiza movimientos completamente al azar.
    * **Difícil:** La IA prioriza jugar en una casilla adyacente (vecina) a la última jugada del usuario, simulando una reacción defensiva básica.
* **Experiencia Multimedia:**
    * Música de fondo en bucle.
    * Efectos de sonido distintos para la jugada del usuario y la de Android.
    * Música específica para la victoria, la derrota y el empate.
* **Flujo de Partida Completo:** Muestra el resultado final en una pantalla dedicada (`ResultadoActivity`) con opción para reiniciar el juego.
* **Simulación de Pensamiento:** La jugada de Android se retrasa aleatoriamente entre 2 y 5 segundos, simulando que la máquina está "pensando".

---

## Arquitectura y Diseño

### Clases principales - LÓGICA Y CONTROL DEL JUEGO:
* **`MainActivity`**:
    * **Controlador Principal**: Gestiona la Interfaz de Usuario (UI) y el flujo de la Activity.
    * **Motor del Juego**: Contiene la lógica del tablero, las reglas, la comprobación de victoria/empate y el control de turnos.

* **`AndroidJugada`**:
    * **Clase Abstracta**: Define el método (`play()`) que todas las estrategias de IA deben seguir.

* **`Aleatoria` / `Directa`**:
    * **Implementación de la IA**: Clases concretas que extienden `AndroidJugada` y proveen los dos niveles de dificultad: **Fácil** (aleatorio) y **Difícil** (adyacente).


---

## Tecnologías Utilizadas

* **Lenguaje:** Java
* **Plataforma:** Android Studio
* **Componentes Clave:** `ImageButton`, `MediaPlayer`, `Handler` (para el retardo de la IA), y `Intent` para el flujo de Activities.

---

## Uso e Inicio Rápido

1.  La aplicación comienza con el **Splash Screen** de 3 segundos.
2.  En la pantalla principal, **selecciona la dificultad** (Fácil o Difícil).
3.  Pulsa el botón **"START"**. El usuario siempre inicia la partida.
4.  La aplicación indica el **Turno** actual con un icono.
5.  Al finalizar la partida, serás redirigido a la pantalla de **Resultado**.

---

## Autor / Contacto

* **Nombre:** [Francisco Manuel González Aguilera]
* **Curso:** [2º DAM]
* **Fecha:** [Diciembre 2025]
