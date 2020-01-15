package com.turismoalojamenduakandroid;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;

public class Filtrador extends AppCompatActivity{
    Button filter;
    private String[] mota = {"Mota 1","Mota 2"};
    private ArrayList<String> filtroLista = new ArrayList<String>();
    private String Mota;
    EditText Posta;
    boolean filtrado = true;
;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.filtrador);


        Spinner spnMota= findViewById(R.id.spn_mota);
        Posta = (EditText) findViewById(R.id.ET_posta);

        ArrayAdapter aaMota = new ArrayAdapter(getApplicationContext(),android.R.layout.simple_spinner_dropdown_item,mota);
        aaMota.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnMota.setAdapter(aaMota);




        spnMota.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                Mota = mota[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });



        filter = (Button) findViewById(R.id.bt_filtrar);

        filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filtroLista.add(Mota);
                filtroLista.add(Posta.getText().toString());

                if(filtroLista.get(1).isEmpty()){
                    System.out.println("Esta vacio ");
                    filtroLista.add(1,"0");
                }

                System.out.println("Estoy en el filtrador " + filtroLista.get(1));

                Intent inten = new Intent(getApplicationContext(),Ostatuak.class);

                Bundle miBundle = new Bundle();
                miBundle.putStringArrayList("filtroLista",filtroLista);
                miBundle.putBoolean("filtroTrue",filtrado);
                inten.putExtras(miBundle);
                startActivity(inten);




            }
        });
    }

}
