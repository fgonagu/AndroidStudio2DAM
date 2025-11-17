/**
 * Autor: Francisco Manuel Gonzalez Aguilera
 * Fecha: 07/11/2025
 * Curso: 2DAM
 */
package com.example.ejercicio2;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    // Declaramos el EditText como variable global
    private EditText lcd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Llama al constructor de la clase padre
        super.onCreate(savedInstanceState);
        //Carga los recursos de la vista (en XML)
        setContentView(R.layout.activity_main);

        // Inicializamos el EditText
        lcd = findViewById(R.id.lcd);

        //Configuramos todos los botones numéricos
        configurarBotones();

        /*
        PRUEBA DE LA EXPRESION EXP4J
        String calculo = "2+5-1";
        Expression calculo_expr = new ExpressionBuilder(calculo).build();
        Log.i("_DEBUG_", String.valueOf(calculo_expr.evaluate()));
         */

    }

    private void configurarBotones() {
        //Creamos un array con los IDs de todos los botones numéricos
        int[] idsNumeros = {
                R.id.btn_0, R.id.btn_1, R.id.btn_2, R.id.btn_3, R.id.btn_4,
                R.id.btn_5, R.id.btn_6, R.id.btn_7, R.id.btn_8, R.id.btn_9,

        };
        //Creamos un array con los operadores
        int[] idsOperadores = {
                R.id.btn_sumar,
                R.id.btn_restar,
                R.id.btn_multiplicar,
                R.id.btn_division,
                R.id.btn_igual,
                R.id.btn_borrar,
        };

        //Configuramos botones numéricos
        for (int id : idsNumeros) {
            Button boton = findViewById(id); //Obtenemos el botón por su ID
            boton.setOnClickListener(v -> {    //Agregamos un listener de clics al botón
                Button btn = (Button) v;            //Convertimos v a Button para obtener su texto

                agregarNumero(btn.getText().toString()); //LLamamos al metodo que agrega el número
            });
        }
        //Configuramos los botones de operadores
        for (int id : idsOperadores) {
            Button boton = findViewById(id); //Obtenemos el botón por su ID
            boton.setOnClickListener(v -> { //Agregamos un listener de clics al botón
                Button btn = (Button) v;        //Convertimos v a Button para obtener su texto

                manejarOperador(btn.getText().toString()); //LLamamos al metodo que agrega el operador
            });
        }

    }

    private void agregarNumero(String numero) {
        String textoActual = lcd.getText().toString(); //Obtenemos el texto actual del EditText

        //Lógica para decidir si reemplazar o concatenar
        if (textoActual.equals("0") || textoActual.equals("Error") || textoActual.isEmpty()) {
            //Caso 1: Si es 0, Error o está vacío, reemplazar por el nuevo número
            lcd.setText(numero);
        } else {
            //Caso 2: Si ya hay números: CONCATENAR
            lcd.setText(textoActual + numero);
        }

        //Mostramos toast con el número agregado
        Toast.makeText(this, "Se agregó el número: " + numero, Toast.LENGTH_SHORT).show();

    }
    private void manejarOperador(String operador) {
        //Logica para decidir que hacer con el operador
        switch (operador) {
            case "+":
            case "-":
            case "*":
            case "/":
                agregarOperador(operador);
                break;
            case "=":
                calcularResultado();
                break;
            case "AC":
                limpiarCalculadora();
                break;
            default:
                break;
        }
        Toast.makeText(this, operador, Toast.LENGTH_SHORT).show();
    }
    private void agregarOperador(String operador) {
        //Obtenemos el texto actual del EditText
        String textoActual = lcd.getText().toString();

        if (textoActual.isEmpty() || textoActual.equals("Error")) {  //No permitir operador si está vacío o ya termina con operador
            return; //No hacemos nada
        }
        //Verificamos si el último carácter es un operador
        char ultimoCaracter = textoActual.charAt(textoActual.length() - 1);
        if (ultimoCaracter == '+' || ultimoCaracter == '-' || ultimoCaracter == '*' || ultimoCaracter == '/') {
            //Reemplazamos el último operador
            textoActual = textoActual.substring(0, textoActual.length() - 1);
        }

        //sino agregamos el operador al final
        lcd.setText(textoActual + operador);

    }


    private void calcularResultado() {
        //Obtenemos el texto actual del EditText
        String expresion = lcd.getText().toString();

        if (expresion.isEmpty() || expresion.equals("Error")) { //Validamos que la expresión no esté vacía
            return;
        }
            // Validamos que no termine con operador
            char ultimoCaracter = expresion.charAt(expresion.length() - 1);
            if (ultimoCaracter == '+' || ultimoCaracter == '-' || ultimoCaracter == '*' || ultimoCaracter == '/') {
                lcd.setText("Error");
                return;
            }
            //Usamos evaluador para calcular el resultado
        try {
            Expression exp = new ExpressionBuilder(expresion).build();
            double resultado = exp.evaluate();

            String resultadoStr;
            if (resultado == (long) resultado) {
                resultadoStr = String.valueOf((long) resultado);
            } else {
                resultadoStr = String.valueOf(resultado);
            }

            lcd.setText(resultadoStr);

        } catch (Exception e) {
            lcd.setText("Error");
            Log.e("CALCULATOR_ERROR", "Error al evaluar la expresión: " + expresion, e);
        }

        }
    private void limpiarCalculadora() {
        lcd.setText("0");
    }

    }




