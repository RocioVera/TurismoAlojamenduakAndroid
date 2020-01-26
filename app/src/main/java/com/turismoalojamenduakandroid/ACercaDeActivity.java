package com.turismoalojamenduakandroid;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class ACercaDeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acercade);
    }

    public void volverAtras (View view) {
        Intent pantallaListar = new Intent(ACercaDeActivity.this, Ostatuak.class);
        startActivity(pantallaListar);
        finish();
    }

}
