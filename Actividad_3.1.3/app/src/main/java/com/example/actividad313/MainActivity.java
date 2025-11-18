package com.example.actividad313;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {
    //Vamos a definir el usuario y la contraseña por defecto
    String user = "admin";
    String password = "admin";

    //Contadores para los intentos de login y para los logins correctos
    int intentosErroneos = 0;
    int intentosCorrectos = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        //Obtenemos las vistas de los elementos del layout
        EditText usuario = findViewById(R.id.editTextUser);
        EditText contraseña = findViewById(R.id.editTextPass);
        TextView intentos = findViewById(R.id.textViewNumIntentos);
        TextView logins = findViewById(R.id.textViewNumLogueados);
        Button btLogin = findViewById(R.id.buttonLogin);
        Button btCancel = findViewById(R.id.buttonCancel);

        //Inicializamos los textos
        intentos.setText("0");
        logins.setText("0");

        //Eventos del botón LOGIN
        btLogin.setOnClickListener(v -> {

            String usuarioIntroducido = usuario.getText().toString();
            String contraseñaIntroducida = contraseña.getText().toString();

            //Comprobamos si el usuario y la contraseña son correctos
            if(usuarioIntroducido.equals(user) && contraseñaIntroducida.equals(password)) {
                intentosCorrectos++;
                logins.setText(String.valueOf(intentosCorrectos));
                Toast.makeText(this, "Login correcto", Toast.LENGTH_SHORT).show();
            }else {
                intentosErroneos++;
                intentos.setText(String.valueOf(intentosErroneos));
                Toast.makeText(this, "Login incorrecto", Toast.LENGTH_SHORT).show();
            }



        });

        //Eventos del botón CANCEL
        btCancel.setOnClickListener(v -> {
            usuario.setText("");
            contraseña.setText("");
        Toast.makeText(this, "Campos limpiados", Toast.LENGTH_SHORT).show();
        });

        }

    }

