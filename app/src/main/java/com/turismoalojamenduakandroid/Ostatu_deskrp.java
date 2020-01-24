package com.turismoalojamenduakandroid;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;

public class Ostatu_deskrp extends AppCompatActivity {
    com.turismoalojamenduakandroid.Ostatu Ostatu = new Ostatu();
    ArrayList<com.turismoalojamenduakandroid.Ostatu> OstatuArrayLista = new ArrayList<>();

    Button mapa, reserva;
    TextView campoNombre, campoDescrip, campoHelbidea, campoTelefonoa, campoGmail, campoMarca, campoWebURL;
    private com.turismoalojamenduakandroid.Ostatu ost = null;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ostatu_deskrp);

        mapa = findViewById(R.id.bt_mapa);
        reserva = findViewById(R.id.bt_reserva);

        campoNombre = (TextView) findViewById(R.id.tv_nombre);
        campoDescrip = (TextView) findViewById(R.id.tv_descripcion);
        campoHelbidea = (TextView) findViewById(R.id.tv_helbidea);
        campoTelefonoa = (TextView) findViewById(R.id.tv_telefonoa);
        campoGmail = (TextView) findViewById(R.id.tv_email);
        campoMarca = (TextView) findViewById(R.id.tv_marka);
        campoWebURL = (TextView) findViewById(R.id.tv_webUrl);

        Bundle miBundle=this.getIntent().getExtras();

        if (miBundle != null){
         ost = (com.turismoalojamenduakandroid.Ostatu) miBundle.getSerializable("ostatu");

            campoNombre.setText(ost.getOSTATU_IZENA());
            campoDescrip.setText(ost.getDESKRIBAPENA());
            campoHelbidea.setText(ost.getOSTATU_HELBIDEA());
            campoTelefonoa.setText(ost.getOSTATU_TELEFONOA());
            campoGmail.setText(ost.getOSTATU_EMAIL());
            campoMarca.setText(ost.getMARKA());
            campoWebURL.setText(ost.getWEB_URL());


        }

        mapa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* Intent inten = new Intent(getApplicationContext(), MapsActivity.class);
                Bundle miBundle= new Bundle();

                miBundle.putDouble("LATITUDE",ost.getLATITUDE());
                miBundle.putDouble("LONGITUDE",ost.getLONGITUDE());
                inten.putExtras(miBundle);
                startActivity(inten);*/
            }
        });


    }
}
