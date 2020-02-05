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
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.text.Editable;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
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
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

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
        try {
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
                                        String nan =  obj.get("NAN").toString();
                                        String usuario = obj.get("ERABIL_IZENA").toString();
                                        String telefono = obj.get("ERABIL_TELEFONO").toString();
                                        String email = obj.get("ERABIL_EMAIL").toString();

                                        try {
                                            //nan = desencriptar(nan, "encriptar");
                                            usuario = desencriptar(usuario, "encriptar");
                                            telefono = desencriptar(telefono, "encriptar");
                                            email = desencriptar(email, "encriptar");
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }

                                        Bezeroa bez = new Bezeroa(name.getText().toString(), usuario, telefono, email);

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
                        try {

                            params.put("NAN", encriptar(name.getText().toString(), "encriptar"));
                            params.put("PASAHITZA", encriptar(pass.getText().toString(), "encriptar"));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        return params;
                    }

                };
                try {
                    RequestHandler.getInstance(this).addToRequestQueue(stringRequest);

                } catch (Exception e) {

                }
                //Cambio de pantalla


        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    public void ventanaMapa(View view) {
        int duration = Toast.LENGTH_SHORT;

        Context context2 = getApplicationContext();
        Toast toast2 = Toast.makeText(context2, R.string.errorInicioSesion, duration);
        toast2.show();

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

                            Ostatu[] aj = new Ostatu[10];
                            for (int i = 0; i < aj.length-1; i++) {
                                jo = ja.getJSONObject(i);
                                String OSTATU_IZENA, OSTATU_HELBIDEA;
                                int POSTA_KODEA;
                                double LATITUDE, LONGITUDE;

                                OSTATU_IZENA = jo.getString("OSTATU_IZENA");
                                OSTATU_HELBIDEA = jo.getString("OSTATU_HELBIDEA");
                                LATITUDE = Double.parseDouble(jo.getString("LATITUDE"));
                                LONGITUDE = Double.parseDouble(jo.getString("LONGITUDE"));
                                POSTA_KODEA = Integer.parseInt(jo.getString("POSTA_KODEA"));

                                Ostatu osta = new Ostatu(OSTATU_IZENA, OSTATU_HELBIDEA, LATITUDE, LONGITUDE, POSTA_KODEA);
                                aj[i] = osta;

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

    private String desencriptar(String datos, String password) throws Exception{
        SecretKeySpec secretKey = generateKey(password);
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        byte[] datosDescoficados = android.util.Base64.decode(datos, android.util.Base64.DEFAULT);
        byte[] datosDesencriptadosByte = cipher.doFinal(datosDescoficados);
        String datosDesencriptadosString = new String(datosDesencriptadosByte);
        return datosDesencriptadosString;
    }

    private String encriptar(String datos, String password) throws Exception{
        SecretKeySpec secretKey = generateKey(password);
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] datosEncriptadosBytes = cipher.doFinal(datos.getBytes());
        String datosEncriptadosString = android.util.Base64.encodeToString(datosEncriptadosBytes, Base64.DEFAULT);
        datosEncriptadosString = datosEncriptadosString.substring(0,datosEncriptadosString.length()-2);
        return datosEncriptadosString;
    }

    private SecretKeySpec generateKey(String password) throws Exception{
        MessageDigest sha = MessageDigest.getInstance("SHA-256");
        byte[] key = password.getBytes("UTF-8");
        key = sha.digest(key);
        SecretKeySpec secretKey = new SecretKeySpec(key, "AES");
        return secretKey;
    }


}


