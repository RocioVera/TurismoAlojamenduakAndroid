package com.turismoalojamenduakandroid;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class sacarReservas extends AppCompatActivity {
    public static ListView lv1;
    public static String ostatu = "";
    public static Ostatu hostal ;
    public static String OSTATU_IZENA ="";
    public static String Fechaini = "";
    public static String FechaFini = "";
    public static ArrayList<String> listadoReservas =  new ArrayList<>();
    public static View vista;
    Context context;
    Bezeroa bez;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pintar_listado);
        bez = (Bezeroa) getIntent().getSerializableExtra("bez");
        lv1 = (ListView)findViewById(R.id.lv1);
        sacarDatos();


        /*lv1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                ostatu = ostatus.get(position);
               // sacarDatos();

            }
        });*/

    }

    public void verReservas(View view){
        Intent intent2 = new Intent (view.getContext(), Mainmenu.class);
        intent2.putExtra("bez", bez);

        startActivityForResult(intent2, 0);
    }

    public  void sacarDatos() {
        final Context context = this;
        vista = new View(context);

        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                constants.mostrarReserva,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {

                            JSONArray ja=new JSONArray(response);
                            JSONObject jo=null;

                            int contador =0;
                            for(int i=0;i<ja.length();i++){
                                jo=ja.getJSONObject(i);
                                OSTATU_IZENA = jo.getString("OSTATU_IZENA");
                                Fechaini = jo.getString("DATA_HASIERA");
                                FechaFini = jo.getString("DATA_AMAIERA");
                                String linea = OSTATU_IZENA + ": "+Fechaini+"," +FechaFini;
                                listadoReservas.add(linea);

                            }

                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, listadoReservas);
                            lv1.setAdapter(adapter);





                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        int duration = Toast.LENGTH_SHORT;
                        Context context = getApplicationContext();
                        Toast toast = Toast.makeText(context, "Error en la selección de la provincia", duration);
                        toast.show();

                        Toast.makeText(
                                getApplicationContext(),
                                error.getMessage(),
                                Toast.LENGTH_LONG
                        ).show();
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();
                params.put("NAN",bez.getNAN().toString());



                return params;
            }

        };
        try {
            RequestHandler.getInstance(this).addToRequestQueue(stringRequest);

        }catch(Exception e){

        }
        //Cambio de pantalla


    }




}
