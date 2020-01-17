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
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

//Se referencian las Clases necesarias para la conexión con el Servidor MySQL


public class MainActivity extends AppCompatActivity {

    Button btLogin, btRegistro, btMapa;
    EditText name;
    EditText pass;
    private Double lat, log;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toast.makeText(MainActivity.this.getBaseContext(), "Hola", Toast.LENGTH_SHORT).show();
        Log.d("Diego", "Hola_____________________________________________");



        btMapa = (Button) findViewById(R.id.bt_mapa);
        btLogin = (Button) findViewById(R.id.bt_singin);

        btMapa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent inten = new Intent(getApplicationContext(), MapsActivity.class);
                Bundle miBundle= new Bundle();

                Ostatu o = new Ostatu("123","Ostatu Pepe","El mejor Ostatu","Avd ostatu pepe","Cocacola","pepeostatu@gmail.com","653345654",50,43.2633534,-2.951074,"Mota 1","www.pepeOstatu.com","AdiskidetsuURl","zipUrl",48500,"San Ignacio");
                Ostatu o2 = new Ostatu("124","Ostatu Juan","El mejor Juan","Avd ostatu juan","Cocacola","Juanostatu@gmail.com","653345657",50,0.4381311,-3.8196194,"Mota 2","www.JuanOstatu.com","AdiskidetsuURl","zipUrl",48501,"San Ignacio");
                Ostatu o3 = new Ostatu("123","Ostatu Jose","El mejor Ostatu","Avd ostatu pepe","Cocacola","pepeostatu@gmail.com","653345654",50,43.3452853,-2.9177977,"Mota 1","www.pepeOstatu.com","AdiskidetsuURl","zipUrl",48502,"San Ignacio");
                Ostatu o4 = new Ostatu("124","Ostatu Josefa","El mejor Juan","Avd ostatu juan","Cocacola","Juanostatu@gmail.com","653345657",50,43.2894694,-3.0108891,"Mota 2","www.JuanOstatu.com","AdiskidetsuURl","zipUrl",48503,"San Ignacio");
                Ostatu o5 = new Ostatu("123","Ostatu Josefin","El mejor Ostatu","Avd ostatu pepe","Cocacola","pepeostatu@gmail.com","653345654",50,41.1622023,-8.656973,"Mota 1","www.pepeOstatu.com","AdiskidetsuURl","zipUrl",48504,"San Ignacio");
                Ostatu o6 = new Ostatu("124","Ostatu Falete","El mejor Juan","Avd ostatu juan","Cocacola","Juanostatu@gmail.com","653345657",50,42.3441564,-3.7122026,"Mota 2","www.JuanOstatu.com","AdiskidetsuURl","zipUrl",48505,"San Ignacio");

                ArrayList<Ostatu> ostatuLista = new  ArrayList<Ostatu>();

                miBundle.putDouble("LATITUDE",lat);
                miBundle.putDouble("LONGITUDE",log);
                miBundle.putSerializable("ostatuLista",ostatuLista);
                inten.putExtras(miBundle);
                startActivity(inten);
            }
        });

        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this.getBaseContext(), "Boton Login", Toast.LENGTH_SHORT).show();
                Intent inten = new Intent(getApplicationContext(), Ostatuak.class);
                startActivity(inten);
            }
        });


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,}, 1000);
        } else {
            locationStart();


        }

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
}


