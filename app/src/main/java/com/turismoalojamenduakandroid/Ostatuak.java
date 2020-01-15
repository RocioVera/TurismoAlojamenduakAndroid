package com.turismoalojamenduakandroid;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;

public class Ostatuak extends AppCompatActivity {
    ListView ostatuList;
    Button search, filter;
    ArrayList<String> filtroLista;
    Ostatu Ostatu = new Ostatu();
    boolean filtrado;
    ArrayList<Ostatu> OstatuArrayLista = new ArrayList<Ostatu>();




    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ostatu);
        filtrado=false;
        Bundle miBundle=this.getIntent().getExtras();

        if (miBundle != null){
            filtroLista = miBundle.getStringArrayList("filtroLista");
            filtrado = miBundle.getBoolean("filtroTrue");
        }


        search = (Button) findViewById(R.id.bt_search);
        filter = (Button) findViewById(R.id.bt_filter);

        ostatuList = (ListView) findViewById(R.id.lstView_ostatu);



        filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent inten = new Intent(getApplicationContext(),Filtrador.class);
                startActivity(inten);
            }
        });

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Conector konexioa = new Conector( "3262035_ostatuagrad");
                //prueba konexioa = new prueba( "elorrieta");
                // Connection conexion = Conector.getConnection();
                Ostatu o = new Ostatu("123","Ostatu Pepe","El mejor Ostatu","Avd ostatu pepe","Cocacola","pepeostatu@gmail.com","653345654",50,43.2773216,-2.9763593,"Mota 1","www.pepeOstatu.com","AdiskidetsuURl","zipUrl",48878,"San Ignacio");
                Ostatu o2 = new Ostatu("124","Ostatu Juan","El mejor Juan","Avd ostatu juan","Cocacola","Juanostatu@gmail.com","653345657",50,45.2773216,-2.9963593,"Mota 2","www.JuanOstatu.com","AdiskidetsuURl","zipUrl",48868,"San Ignacio");

                //----




                OstatuArrayLista.add(o);
                OstatuArrayLista.add(o2);

                OstatuArrayLista = cargarLista(OstatuArrayLista,filtroLista,filtrado);



            }
        });


        ostatuList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int posicion, long id) {
              Toast.makeText(getApplicationContext(),"Selencionado: " + adapterView.getItemAtPosition(posicion).toString(), Toast.LENGTH_SHORT).show();
                ArrayList<Ostatu> pruebaArrayList = new ArrayList<>();
                OstatuArrayLista = cargarLista(OstatuArrayLista,filtroLista,filtrado);

                Intent inten = new Intent(getApplicationContext(),Ostatu_deskrp.class);
                Bundle miBundle= new Bundle();

                Ostatu ost = OstatuArrayLista.get(posicion);


                miBundle.putSerializable("ostatu",ost);
                inten.putExtras(miBundle);
                startActivity(inten);

            }
        });


    }

public ArrayList<Ostatu> cargarLista(ArrayList<Ostatu> ostatuArrayLista, ArrayList<String> filtroLista, boolean filtrado){
    ArrayList<Ostatu> pruebaArrayList = new ArrayList<>();

    for (int i = 0; i < ostatuArrayLista.size(); ++i) {
        if(filtroLista != null){// Si el filtrodor esta lleno, se entra en el if
            System.out.println("A Ver Que pasa Aqui: "+ "MotaBD: "+ostatuArrayLista.get(i).getMOTA() +" MotaFiltro " +filtroLista.get(0) + " PostalBd " + ostatuArrayLista.get(i).getPOSTA_KODEA() + " PostalFiltro " + filtroLista.get(1));
            if(ostatuArrayLista.get(i).getMOTA().equals(filtroLista.get(0)) && (ostatuArrayLista.get(i).getPOSTA_KODEA() == Integer.parseInt(filtroLista.get(1)) || filtroLista.get(1).equals("0"))){//Si el ostatuArrayLista.get(i).getMOTA() coincide con el filtro se entra en el if y se añada al nuevo arrayList filtrado
                pruebaArrayList.add(ostatuArrayLista.get(i));
            }
       }else{
            pruebaArrayList.add(ostatuArrayLista.get(i));
        }
    }
    ArrayAdapter <Ostatu> adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,pruebaArrayList);
    ostatuList.setAdapter(adapter);

    return pruebaArrayList;
}

    }
