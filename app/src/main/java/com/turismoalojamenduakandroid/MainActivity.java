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
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

//Se referencian las Clases necesarias para la conexión con el Servidor MySQL


public class MainActivity extends AppCompatActivity {
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

    private void locationStart() {
        LocationManager mlocManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Localizacion Local = new Localizacion();
        Local.setMainActivity(this);
        final boolean gpsEnabled = mlocManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if (!gpsEnabled) {
            Intent settingsIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(settingsIntent);
        }
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,}, 1000);
            return;
        }
        mlocManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, (LocationListener) Local);
        mlocManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, (LocationListener) Local);


    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 1000) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                locationStart();
                return;
            }
        }
    }
    public void setLocation(Location loc) {
        //Obtener la direccion de la calle a partir de la latitud y la longitud
        if (loc.getLatitude() != 0.0 && loc.getLongitude() != 0.0) {
            try {
                Geocoder geocoder = new Geocoder(this, Locale.getDefault());
                List<Address> list = geocoder.getFromLocation(
                        loc.getLatitude(), loc.getLongitude(), 1);
                lat = loc.getLatitude();
                log = loc.getLongitude();
               // Toast.makeText(getApplicationContext(), "Latitud:" + loc.getLatitude() + " Longitud "+ loc.getLongitude(), Toast.LENGTH_SHORT).show();
                if (!list.isEmpty()) {
                    Address DirCalle = list.get(0);
                   // mensaje2.setText("Mi direccion es: + DirCalle.getAddressLine(0));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void ventanaRegistro(View view){
        Intent intent2 = new Intent (view.getContext(), registro.class);
        startActivityForResult(intent2, 0);
    }

    public void acercaDe(View view){
        Intent intent2 = new Intent (view.getContext(), ACercaDeActivity.class);
        startActivityForResult(intent2, 0);
    }

    public void hilosec(View v){
        new Thread(new Runnable() {
            public void run() {
                acceder();

            }
        }).start();

    }


    public void acceder() {
        name =  (EditText) findViewById(R.id.ET_user);
        pass = (EditText) findViewById(R.id.ET_pass);
        Context context = this;
        vista = new View(context);

       /* Context context = getApplicationContext();
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, "Fallo", duration);
        toast.show();*/

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
                            String error= obj.get("error").toString();
                            if(error.equalsIgnoreCase("false")){
                            String nan = obj.get("NAN").toString();
                            String usuario = obj.get("ERABIL_IZENA").toString();
                            String telefono = obj.get("ERABIL_TELEFONO").toString();
                            String email = obj.get("ERABIL_EMAIL").toString();
                            Bezeroa bez = new Bezeroa(nan, usuario,telefono,email);

                            int duration = Toast.LENGTH_SHORT;
                            Context context = getApplicationContext();
                            Toast toast = Toast.makeText(context, R.string.inicioSesionBueno, duration);
                            toast.show();
                                registrado = true;
                                if (registrado){
                                    Intent intent2 = new Intent (vista.getContext(), Mainmenu.class);
                                    intent2.putExtra("bez", bez);
                                    startActivityForResult(intent2, 0);
                                }
                            }
                            else{
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
                        Toast toast = Toast.makeText(context,error.getMessage() , duration);
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
                params.put("NAN", name.getText().toString());
                params.put("PASAHITZA", pass.getText().toString());

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


