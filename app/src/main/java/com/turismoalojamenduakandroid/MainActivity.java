package com.turismoalojamenduakandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

//Se referencian las Clases necesarias para la conexión con el Servidor MySQL
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;



public class MainActivity extends AppCompatActivity {

    Button btLogin, btRegistro, btMapa;
    EditText name;
    EditText pass;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toast.makeText(MainActivity.this.getBaseContext(), "Hola", Toast.LENGTH_SHORT).show();
        Log.d("Diego", "Hola_____________________________________________");


        btMapa = (Button) findViewById(R.id.bt_mapa);
        btLogin = (Button) findViewById(R.id.bt_singin);

        btMapa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent inten = new Intent(getApplicationContext(),MapsActivity.class);
                startActivity(inten);
            }
        });

        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this.getBaseContext(), "Boton Login", Toast.LENGTH_SHORT).show();
                Intent inten = new Intent(getApplicationContext(),Ostatuak.class);
                startActivity(inten);
            }
        });



    }



}
