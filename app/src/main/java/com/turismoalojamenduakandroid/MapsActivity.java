package com.turismoalojamenduakandroid;

import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

import androidx.fragment.app.FragmentActivity;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private Double lat, log;
    private ArrayList<Ostatu> ostatuLista = new  ArrayList<Ostatu>();

    //prueba
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        Bundle miBundle=this.getIntent().getExtras();

        if (miBundle != null){

            lat = miBundle.getDouble("LATITUDE");
            log = miBundle.getDouble("LONGITUDE");
            ostatuLista = (ArrayList<Ostatu>) miBundle.getSerializable("ostatuLista");
            System.out.println("Que Tal............................... " + ostatuLista.size());

        }



        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }



    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(lat, log);
        Double[] cordenadas = new Double[2];
    System.out.println("HOLAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA:.... " + ostatuLista.size());
        for(int i=0; i < ostatuLista.size(); ++i){
            System.out.println( i + ": " +ostatuLista.get(i).getLATITUDE() + ">=" + (ostatuLista.get(i).getLATITUDE()-3) +" " +ostatuLista.get(i).getLATITUDE() + " <= " +(ostatuLista.get(i).getLATITUDE()+3) + " " + ostatuLista.get(i).getLONGITUDE() + " >= " + (ostatuLista.get(i).getLONGITUDE()-3) +" "+ ostatuLista.get(i).getLONGITUDE() + " <= " + (ostatuLista.get(i).getLONGITUDE()+3));
            if(ostatuLista.get(i).getLATITUDE()>=ostatuLista.get(i).getLATITUDE()-3 && ostatuLista.get(i).getLATITUDE()<=ostatuLista.get(i).getLATITUDE()+3 && ostatuLista.get(i).getLONGITUDE()>=ostatuLista.get(i).getLONGITUDE()-3 && ostatuLista.get(i).getLONGITUDE()<=ostatuLista.get(i).getLONGITUDE()+3){
                LatLng sydney2 = new LatLng(ostatuLista.get(i).getLATITUDE(),ostatuLista.get(i).getLONGITUDE());
                mMap.addMarker(new MarkerOptions().position(sydney2).title( ostatuLista.get(i).getOSTATU_IZENA()));
            }
        }

        mMap.addMarker(new MarkerOptions().position(sydney).title("You"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }


    public void cerca(){
Ostatu o = new Ostatu();
    }
}
