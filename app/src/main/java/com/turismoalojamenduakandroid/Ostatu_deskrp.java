package com.turismoalojamenduakandroid;

import android.os.Bundle;
import android.system.Os;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;

public class Ostatu_deskrp extends AppCompatActivity {
    Ostatu Ostatu = new Ostatu();
    ArrayList<Ostatu> OstatuArrayLista = new ArrayList<>();

    TextView campoNombre, campoDescrip, campoHelbidea, campoTelefonoa, campoGmail, campoMarca, campoWebURL;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ostatu_deskrp);

        campoNombre = (TextView) findViewById(R.id.tv_nombre);
        campoDescrip = (TextView) findViewById(R.id.tv_descripcion);
        campoHelbidea = (TextView) findViewById(R.id.tv_helbidea);
        campoTelefonoa = (TextView) findViewById(R.id.tv_telefonoa);
        campoGmail = (TextView) findViewById(R.id.tv_email);
        campoMarca = (TextView) findViewById(R.id.tv_marka);
        campoWebURL = (TextView) findViewById(R.id.tv_webUrl);

        Bundle miBundle=this.getIntent().getExtras();
//        Toast.makeText(getApplicationContext(),"??: " + miBundle.toString(), Toast.LENGTH_SHORT).show();
        com.turismoalojamenduakandroid.Ostatu ost = null;


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



    }
}
