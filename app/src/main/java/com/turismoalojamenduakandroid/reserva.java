package com.turismoalojamenduakandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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

public class reserva extends AppCompatActivity {
    String data1="";
    String data2="";
    Double prezio=0.0;
    int pertsonato = 0;
    String signatura = "";
    String nana ="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserva);
        Ostatu ostatu = (Ostatu) getIntent().getSerializableExtra("datos");
        signatura = ostatu.getID_SIGNATURA();
    }









    public void reservar(){

        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                constants.reserva,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        String insertado="";
                        try {

                            JSONArray ja=new JSONArray(response);
                            JSONObject jo=null;

                            int contador =0;
                            for(int i=0;i<ja.length();i++){
                                jo=ja.getJSONObject(i);
                                 insertado = jo.getString("Error");


                            }

                        if(insertado.equalsIgnoreCase("false")){
                            //Se ha insertado la reserva
                        }



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

                params.put("DATA_AMAIERA",data1);
                params.put("DATA_HASIERA",data2);
                params.put("ERRESERBA_PREZIO_TOT", String.valueOf(prezio));
                params.put("PERTSONA_KANT_ERRES", String.valueOf(pertsonato));
                params.put("OSTATUAK_ID_SIGNATURA",signatura);
                params.put("ERABILTZAILEAK_NAN",nana);


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
