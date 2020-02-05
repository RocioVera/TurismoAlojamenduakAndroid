package com.turismoalojamenduakandroid;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

//Se referencian las Clases necesarias para la conexión con el Servidor MySQL


public class MainActivity extends AppCompatActivity {
    public static ArrayList<Ostatu> ostatuArr;

    Button registrarse;
    Button btLogin, btRegistro, btMapa;
    EditText name;
    EditText pass;
    private Double lat, log;
    boolean registrado = false;
    private View vista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        registrarse = (Button) findViewById(R.id.bt_singup);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //btMapa = (Button) findViewById(R.id.bt_mapa);

    }

    public void ventanaRegistro(View view) {
        Intent intent2 = new Intent(view.getContext(), registro.class);
        startActivityForResult(intent2, 0);
    }

    public void acercaDe(View view) {
        Intent intent2 = new Intent(view.getContext(), ACercaDeActivity.class);
        startActivityForResult(intent2, 0);
    }

    public void hilosec(View v) {
        new Thread(new Runnable() {
            public void run() {
                acceder();

            }
        }).start();

    }

    public void acceder() {
        name = (EditText) findViewById(R.id.ET_user);
        pass = (EditText) findViewById(R.id.ET_pass);
        Context context = this;
        vista = new View(context);

        int baimena = 1;
        // Crear nuevo objeto Json basado en el mapa

        // Actualizar datos en el servidor
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                constants.conexion,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject obj = new JSONObject(response);
                            String error = obj.get("error").toString();
                            if (error.equalsIgnoreCase("false")) {
                                String nan = obj.get("NAN").toString();
                                String usuario = obj.get("ERABIL_IZENA").toString();
                                String telefono = obj.get("ERABIL_TELEFONO").toString();
                                String email = obj.get("ERABIL_EMAIL").toString();
                                Bezeroa bez = new Bezeroa(nan, usuario, telefono, email);

                                int duration = Toast.LENGTH_SHORT;
                                Context context = getApplicationContext();
                                Toast toast = Toast.makeText(context, R.string.inicioSesionBueno, duration);
                                toast.show();
                                registrado = true;
                                if (registrado) {
                                    Intent intent2 = new Intent(vista.getContext(), Mainmenu.class);
                                    intent2.putExtra("bez", bez);
                                    startActivityForResult(intent2, 0);
                                }
                            } else {
                                int duration2 = Toast.LENGTH_SHORT;
                                Context context2 = getApplicationContext();
                                Toast toast2 = Toast.makeText(context2, R.string.errorInicioSesion, duration2);
                                toast2.show();
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
                        Throwable er = error.getCause();
                        Context context = getApplicationContext();
                        Toast toast = Toast.makeText(context, error.getMessage(), duration);
                        toast.show();

                        Toast.makeText(
                                getApplicationContext(),
                                error.getMessage(),
                                Toast.LENGTH_LONG
                        ).show();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                int baimena = 1;
                Map<String, String> params = new HashMap<>();
                params.put("NAN", name.getText().toString());
                params.put("PASAHITZA", pass.getText().toString());

                return params;
            }

        };
        try {
            RequestHandler.getInstance(this).addToRequestQueue(stringRequest);

        } catch (Exception e) {

        }
        //Cambio de pantalla


    }


    public void ventanaMapa(View view) {

        final Context context = this;
        vista = new View(context);
        StringRequest stringRequest = new StringRequest(
                Request.Method.GET,
                constants.ostatuGuztiak,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        ostatuArr = new ArrayList<Ostatu>();
                        try {
                            JSONArray ja=new JSONArray(response);
                            JSONObject jo=null;
                            Ostatu[] aj = new Ostatu[5];
                            for (int i = 0; i < 5; i++) {
                                jo = ja.getJSONObject(i);
                                String ID_SIGNATURA, OSTATU_IZENA, DESKRIBAPENA, OSTATU_HELBIDEA, MARKA, OSTATU_EMAIL,
                                        OSTATU_TELEFONOA, MOTA, WEB_URL, ADISKIDETSU_URL, ZIP_URL, HERRI_KODEA;
                                int PERTSONA_TOT, POSTA_KODEA;
                                double LATITUDE, LONGITUDE;

                                //  String OSTATU_IZENA, String DESKRIBAPENA, String OSTATU_HELBIDEA, String MARKA, String OSTATU_EMAIL, String OSTATU_TELEFONOA, int PERTSONA_TOT, double LATITUDE, double LONGITUDE, String MOTA, String WEB_URL, String ADISKIDETSU_URL, String ZIP_URL, int POSTA_KODEA, String HERRI_KODEA) {
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
                                MOTA = jo.getString("MOTA");
                                WEB_URL = jo.getString("WEB_URL");
                                ADISKIDETSU_URL = jo.getString("ADISKIDETSU_URL");
                                ZIP_URL = jo.getString("ZIP_URL");
                                POSTA_KODEA = Integer.parseInt(jo.getString("POSTA_KODEA"));
                                HERRI_KODEA = jo.getString("HERRI_KODEA");

                                Ostatu osta = new Ostatu("", OSTATU_IZENA, "", OSTATU_HELBIDEA, "", "", "", 0, LATITUDE, LONGITUDE, "", "", "", "", POSTA_KODEA, "");
                                aj[i] = osta;
                                //ostatuArr.add(osta);

                            }

                            Intent intent2 = new Intent(context, mapa.class);
                            intent2.putExtra("ostatuArr", aj);
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
                        Toast toast = Toast.makeText(context, "Error", duration);
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
                params.put("PROBINTZIA","error");


                return params;
            }

        };
        try {
            RequestHandler.getInstance(this).addToRequestQueue(stringRequest);

        }catch(Exception e){

        }
        //Cambio de pantalla


    }

/*
 for (int i = 0; i < ja.length(); i++) {
                                jo = ja.getJSONObject(i);
                                String ID_SIGNATURA, OSTATU_IZENA, DESKRIBAPENA, OSTATU_HELBIDEA, MARKA, OSTATU_EMAIL,
                                        OSTATU_TELEFONOA, MOTA, WEB_URL, ADISKIDETSU_URL, ZIP_URL, HERRI_KODEA;
                                int PERTSONA_TOT, POSTA_KODEA;
                                double LATITUDE, LONGITUDE;

                                //  String OSTATU_IZENA, String DESKRIBAPENA, String OSTATU_HELBIDEA, String MARKA, String OSTATU_EMAIL, String OSTATU_TELEFONOA, int PERTSONA_TOT, double LATITUDE, double LONGITUDE, String MOTA, String WEB_URL, String ADISKIDETSU_URL, String ZIP_URL, int POSTA_KODEA, String HERRI_KODEA) {
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
                                MOTA = jo.getString("MOTA");
                                WEB_URL = jo.getString("WEB_URL");
                                ADISKIDETSU_URL = jo.getString("ADISKIDETSU_URL");
                                ZIP_URL = jo.getString("ZIP_URL");
                                POSTA_KODEA = Integer.parseInt(jo.getString("POSTA_KODEA"));
                                HERRI_KODEA = jo.getString("HERRI_KODEA");

                                Ostatu osta = new Ostatu(ID_SIGNATURA, OSTATU_IZENA, DESKRIBAPENA, OSTATU_HELBIDEA, MARKA, OSTATU_EMAIL, OSTATU_TELEFONOA, PERTSONA_TOT, LATITUDE, LONGITUDE, MOTA, WEB_URL, ADISKIDETSU_URL, ZIP_URL, POSTA_KODEA, HERRI_KODEA);

                                ostatuArr.add(osta);

                            }

                            Intent intent2 = new Intent(context, mapa.class);
                            intent2.putExtra("ostatuArr", ostatuArr);
                            startActivityForResult(intent2, 0);

 */


}


