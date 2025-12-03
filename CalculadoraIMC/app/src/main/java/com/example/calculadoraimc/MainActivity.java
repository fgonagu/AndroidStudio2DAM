package com.example.calculadoraimc;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.time.Year;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    //Declaración de las Vistas
    private EditText etAltura, etAnioNacimiento, etPesoActual;
    private RadioButton rbMujer, rbHombre;
    private RadioButton rbPerrault, rbBroca, rbWanDerVael;
    private TextView tvPesoIdealResultado;
    private ImageView ivResultadoGrafico;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //Inicialización de las Vistas
        //EditTexts
        etAltura = findViewById(R.id.editAltura);
        etAnioNacimiento = findViewById(R.id.editYear);
        etPesoActual = findViewById(R.id.editTextNumber2);

        //RadioButtons Sexo
        rbMujer = findViewById(R.id.rbWomen);
        rbHombre = findViewById(R.id.rbMen);

        //RadioButtons Metodo
        rbPerrault = findViewById(R.id.rbPerrault);
        rbBroca = findViewById(R.id.rbBroca);
        rbWanDerVael = findViewById(R.id.rbWanDerVael);

        //TextViews
        tvPesoIdealResultado = findViewById(R.id.textResult);

        //ImageView
        ivResultadoGrafico = findViewById(R.id.imgResult);

        //Button
        Button btnCalcular = findViewById(R.id.btnCalculate);

        //Asociamos el evento click al botón
        btnCalcular.setOnClickListener(v -> calcularPesoIdeal());

    }
    //Metodos
    public void calcularPesoIdeal(){
        //Variables
        double alturaCm, pesoActual;
        int anioNacimiento;

        //Validamos la altura
        String alturaStr = etAltura.getText().toString();
        if(alturaStr.isBlank()){
            Toast.makeText(this, "Introduce la altura en cm", Toast.LENGTH_SHORT).show();
            return;
        }
        //Convertimos la altura de nuevo a double
        alturaCm = Double.parseDouble(alturaStr);

        //Validamos el año de nacimiento
        String anioStr = etAnioNacimiento.getText().toString();
        if(anioStr.isBlank()){
            Toast.makeText(this, "Introduce el año de nacimiento", Toast.LENGTH_SHORT).show();
            return;
        }
        //Convertimos el año de nacimiento a int
        anioNacimiento = Integer.parseInt(anioStr);
        //Para calcular la edad para la fórmula de Perrault
        //Obtenemos primero el año actual usando Calendar
        int anioActual = Calendar.getInstance().get(Calendar.YEAR);
        //Calculamos la edad restando al año actual el año de nacimiento
        int edad = anioActual - anioNacimiento;

        //Validamos el peso actual
        String pesoStr = etPesoActual.getText().toString();
        if(pesoStr.isBlank()){
            Toast.makeText(this, "Introduce el peso actual en kg", Toast.LENGTH_SHORT).show();
            return;
        }
        //Convertimos el peso actual a double
        pesoActual = Double.parseDouble(pesoStr);

        //Validamos el sexo
        boolean esMujer = rbMujer.isChecked();
        boolean esHombre = rbHombre.isChecked();
        if(!esMujer && !esHombre){
            Toast.makeText(this, "Selecciona el sexo", Toast.LENGTH_SHORT).show();
            return;
        }

        //Validamos el metodo
        boolean esPerrault = rbPerrault.isChecked();
        boolean esBroca = rbBroca.isChecked();
        boolean esWanDerVael = rbWanDerVael.isChecked();
        if(!esPerrault && !esBroca && !esWanDerVael){
            Toast.makeText(this, "Selecciona un método de cálculo", Toast.LENGTH_SHORT).show();
            return;
        }

        //Aplicación de las fórmulas
        double pesoIdeal;

        if(esPerrault){
            //FÓRMULA PERRAULT: Altura - 100 + ((edad/10) * 0.9)
            pesoIdeal = alturaCm - 100 + ((edad/10.0) * 0.9);
        }else if(esBroca){
            //FÓRMULA BROCA: Altura - 100
            pesoIdeal = alturaCm - 100;
        }else {
            //FÓRMULA WAN DER VAEL (según sexo)
            if(esMujer){
                //FÓRMULA WAN DER VAEL MUJER: (Altura - 150) * 0.6 + 50
                pesoIdeal = (alturaCm - 150) * 0.6 + 50;
            }else{
                //FÓRMULA WAN DER VAEL HOMBRE: (Altura - 150) * 0.75 + 50
                pesoIdeal = (alturaCm - 150) * 0.75 + 50;
            }
        }

        // Mostramos los resultados

        //Mostramos peso ideal numérico
        tvPesoIdealResultado.setText(String.format("Peso Ideal: %.2f kg", pesoIdeal));

        //Mostramos resultado gráfico (imagen)
        mostrarResultadoGrafico(pesoActual, pesoIdeal, esMujer);
        }

    private void mostrarResultadoGrafico(double pesoActual, double pesoIdeal, boolean esMujer) {

        //Cálculo de la diferencia de porcentaje
        //Diferencia porcentual = (pesoActual - pesoIdeal) / pesoIdeal * 100
        double diferenciaPorcentual = Math.abs((pesoActual - pesoIdeal) / pesoIdeal * 100);

        int drawableResId;
        //Si el valor de peso ideal se distancia del real en más del 10% -> angry
        if(diferenciaPorcentual > 10.0){
            //Determinamos el sexo para imagen correcta
            if(esMujer){
                drawableResId = R.drawable.angrygirl;
            }else{
                drawableResId = R.drawable.angryman;
            }
        } else{
            //Si la diferencia es menos del 10% -> happy
            if(esMujer){
                drawableResId = R.drawable.happygirl;
            }else{
                drawableResId = R.drawable.happyman;
            }
        }
        //Establecemos la imagen
        ivResultadoGrafico.setImageResource(drawableResId);
        }
}