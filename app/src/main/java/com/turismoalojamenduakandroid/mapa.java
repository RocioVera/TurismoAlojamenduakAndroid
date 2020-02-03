package com.turismoalojamenduakandroid;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.location.LocationServices;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.mapbox.android.core.permissions.PermissionsListener;
import com.mapbox.android.core.permissions.PermissionsManager;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.camera.CameraPosition;
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.location.LocationComponent;
import com.mapbox.mapboxsdk.location.LocationComponentActivationOptions;
import com.mapbox.mapboxsdk.location.modes.CameraMode;
import com.mapbox.mapboxsdk.location.modes.RenderMode;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.maps.Style;

import java.util.List;

public class mapa extends AppCompatActivity implements
        OnMapReadyCallback, PermissionsListener {
    private MapView mapaView;
    private FloatingActionButton btUbicacion;
    private LocationServices servicioUbicacion;
    private PermissionsManager permissionsManager;
    private MapboxMap mapboxMap;
    private String longitudea = "", latitudea="", izena="", kokapena="", helbidea="";
    private Ostatu ostatu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        //credenciales
        Mapbox.getInstance(this, "pk.eyJ1Ijoicm9jaW92ZXJhIiwiYSI6ImNrNXpuMWJrMTFncG4zZnJ2djlhaXZsb2gifQ.apgdEaJw2unPnclYPRR5Yw");
        setContentView(R.layout.activity_mapa);
        getSupportActionBar().hide();

        mapaView = (MapView) findViewById(R.id.mapView);
        mapaView.onCreate(savedInstanceState);
        mapaView.getMapAsync(this);

        ostatu = (Ostatu) getIntent().getSerializableExtra("ostatu");
        longitudea = ostatu.getLONGITUDE()+"";
        latitudea = ostatu.getLATITUDE()+"";
        izena = ostatu.getOSTATU_IZENA();
        kokapena = ostatu.getPOSTA_KODEA()+"";
        helbidea = ostatu.getOSTATU_HELBIDEA();

        // mapbox camara
        mapaView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(MapboxMap mapboxMap) {
                CameraPosition position = new CameraPosition.Builder()
                        .target(new LatLng(43.257, -2.92344)) // coordenadas del alojamiento escogido
                        .zoom(55) // nivel de zoom
                        .tilt(30) // inclinación de la cámara
                        .build();
                mapboxMap.animateCamera(CameraUpdateFactory
                        .newCameraPosition(position), 7000);
            }
        });

    }

    // localizacion
    public void localizacion(View view){
        final Double latitude, longitude;
        latitude = Double.parseDouble(latitudea);
        longitude = Double.parseDouble(longitudea);

        // MAPBOX
        mapaView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(MapboxMap mapboxMap) {
                mapboxMap.addMarker(new MarkerOptions()
                        .position(new LatLng(
                                latitude, longitude)) // coordenadas del alojamiento  elegido
                        .title(izena)// nombre del alojamiento elegido
                        .snippet(kokapena + "\n" + helbidea));// descripcion del alojamiento elegido

                CameraPosition position = new CameraPosition.Builder()
                        .target(new LatLng(latitude, longitude)) // coordenadas del alojamiento elegido
                        .zoom(15) // zoom
                        .tilt(30) // Finclinación de la cámara
                        .build();
                mapboxMap.animateCamera(CameraUpdateFactory
                        .newCameraPosition(position), 7000);
            }
        });
    }

    //MAPBOX UTILS
    @Override
    public void onMapReady(@NonNull final MapboxMap mapboxMap) {
        mapa.this.mapboxMap = mapboxMap;

        mapboxMap.setStyle(new Style.Builder().fromUri("mapbox://styles/mapbox/satellite-streets-v9"), //mapbox://styles/mapbox/cjerxnqt3cgvp2rmyuxbeqme7
                new Style.OnStyleLoaded() {
                    @Override
                    public void onStyleLoaded(@NonNull Style style) {
                        enableLocationComponent(style);
                    }
                });
    }

    @SuppressWarnings( {"MissingPermission"})
    private void enableLocationComponent(@NonNull Style loadedMapStyle) {
        // mirar los permisos
        if (PermissionsManager.areLocationPermissionsGranted(this)) {
            // instanciar el componente
            LocationComponent locationComponent = mapboxMap.getLocationComponent();
            // activar opciones
            locationComponent.activateLocationComponent(
                    LocationComponentActivationOptions.builder(this, loadedMapStyle).build());
            // poner el datos del componente
            locationComponent.setLocationComponentEnabled(true);
            locationComponent.setCameraMode(CameraMode.TRACKING);
            locationComponent.setRenderMode(RenderMode.COMPASS);
        } else {
            permissionsManager = new PermissionsManager(this);
            permissionsManager.requestLocationPermissions(this);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        permissionsManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onExplanationNeeded(List<String> permissionsToExplain) {
        Toast.makeText(this, R.string.user_location_permission_explanation, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onPermissionResult(boolean granted) {
        if (granted) {
            mapboxMap.getStyle(new Style.OnStyleLoaded() {
                @Override
                public void onStyleLoaded(@NonNull Style style) {
                    enableLocationComponent(style);
                }
            });
        } else {
            Toast.makeText(this, R.string.user_location_permission_not_granted, Toast.LENGTH_LONG).show();
            finish();
        }
    }

    @Override
    @SuppressWarnings( {"MissingPermission"})
    protected void onStart() {
        super.onStart();
        mapaView.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapaView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapaView.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mapaView.onStop();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapaView.onSaveInstanceState(outState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapaView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapaView.onLowMemory();
    }
} 