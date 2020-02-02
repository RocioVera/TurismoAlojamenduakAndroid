package com.turismoalojamenduakandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

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

public class pintarListado extends AppCompatActivity {
    private ListView lv1;
    public static String ostatu = "";
    public static Ostatu hostal ;
    public static String ID_SIGNATURA="";
    public static String OSTATU_IZENA ="";
    public static String DESKRIBAPENA = "";
    public static String OSTATU_HELBIDEA = "";
    public static String MARKA ="" ;
    public static String OSTATU_EMAIL = "";
    public static String OSTATU_TELEFONOA ="";
    public static int PERTSONA_TOT = 0;
    public static double LATITUDE = 0.0;
    public static double LONGITUDE = 0.0;
    public static String MOTA = "";
    public static String WEB_URL  ="";
    public static String ADISKIDETSU_URL = "";
    public static String ZIP_URL = "";
    public static int POSTA_KODEA = 0;
    public static String HERRI_KODEA = "";
    View vista;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pintar_listado);
        lv1 = (ListView)findViewById(R.id.lv1);
        final ArrayList <String> ostatus =  (ArrayList <String>) getIntent().getSerializableExtra("listado");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, ostatus);
        lv1.setAdapter(adapter);
        lv1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                ostatu = ostatus.get(position);
                sacarDatos();

            }
        });

    }

    public  void sacarDatos() {
        Context context = this;
        vista = new View(context);

        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                constants.ostatuUnico,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {

                            JSONArray ja=new JSONArray(response);
                            JSONObject jo=null;

                            int contador =0;
                            for(int i=0;i<ja.length();i++){
                                jo=ja.getJSONObject(i);
                                    ID_SIGNATURA = jo.getString("ID_SIGNATURA");
                                    OSTATU_IZENA = jo.getString("OSTATU_IZENA");
                                    DESKRIBAPENA = jo.getString("DESKRIBAPENA");
                                     OSTATU_HELBIDEA = jo.getString("OSTATU_HELBIDEA");
                                     MARKA = jo.getString("MARKA");
                                    OSTATU_EMAIL = jo.getString("OSTATU_EMAIL");
                                     OSTATU_TELEFONOA = jo.getString("OSTATU_TELEFONOA");
                                     PERTSONA_TOT = Integer.parseInt(jo.getString("PERTSONA_TOT"));
                                    LATITUDE = Double.parseDouble(jo.getString("LATITUDE"));
                                    LONGITUDE = Double.parseDouble(jo.getString("LONGITUDE"));
                                    MOTA = jo.getString("MOTA");;
                                    WEB_URL  = jo.getString("WEB_URL");
                                    ADISKIDETSU_URL = jo.getString("ADISKIDETSU_URL");
                                    ZIP_URL = jo.getString("ZIP_URL");
                                    POSTA_KODEA = Integer.parseInt(jo.getString("POSTA_KODEA"));
                                    HERRI_KODEA = jo.getString("HERRI_KODEA");


                            }
                            hostal = new Ostatu (ID_SIGNATURA,OSTATU_IZENA,DESKRIBAPENA,OSTATU_HELBIDEA,  MARKA,OSTATU_EMAIL,OSTATU_TELEFONOA,PERTSONA_TOT,  LATITUDE,  LONGITUDE,  MOTA,  WEB_URL,  ADISKIDETSU_URL,  ZIP_URL,  POSTA_KODEA,  HERRI_KODEA) ;


                            Intent intent2 = new Intent (vista.getContext(), reserva.class);
                            intent2.putExtra("datos", hostal);
                            startActivityForResult(intent2, 0);



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
                int baimena = 1;
                Map<String, String> params = new HashMap<>();

                params.put("OSTATU_IZENA",ostatu);



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
