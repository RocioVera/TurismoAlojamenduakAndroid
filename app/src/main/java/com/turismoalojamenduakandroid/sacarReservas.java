package com.turismoalojamenduakandroid;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Base64;
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

import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class sacarReservas extends AppCompatActivity {
    public static ListView lv1;
    public static String ostatu = "";
    public static Ostatu hostal ;
    public static String OSTATU_IZENA ="";
    public static String Fechaini = "";
    public static String FechaFini = "";
    public static ArrayList<String> listadoReservas;
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
                            listadoReservas =  new ArrayList<>();
                            String linea = "";
                            int contador =0;
                            for(int i=0;i<ja.length();i++){
                                jo=ja.getJSONObject(i);
                                OSTATU_IZENA = jo.getString("OSTATU_IZENA");
                                Fechaini = jo.getString("DATA_HASIERA");
                                FechaFini = jo.getString("DATA_AMAIERA");
                                linea = OSTATU_IZENA + ": "+Fechaini+" - " +FechaFini;
                                listadoReservas.add(linea);
                            }

                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, listadoReservas);
                            lv1.setAdapter(adapter);

                        } catch (JSONException e) {
                            int duration = Toast.LENGTH_SHORT;
                            Context context = getApplicationContext();
                            Toast toast = Toast.makeText(context, R.string.errorSacarReservas, duration);
                            toast.show();                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        int duration = Toast.LENGTH_SHORT;
                        Context context = getApplicationContext();
                        Toast toast = Toast.makeText(context, R.string.errorSacarProvincia, duration);
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
                String nanEnk = "";
                try {
                    nanEnk = encriptar(bez.getNAN().toString(), "encriptar");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                params.put("NAN",nanEnk);
                return params;
            }
        };
        try {
            RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
        }catch(Exception e){
        }
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
