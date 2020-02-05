package com.turismoalojamenduakandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

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
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class reserva extends AppCompatActivity {
    String dataSartu="",dataIrten="",signatura = "",nan ="", nombre ="", direccion ="";
    Double prezio=0.0;
    int pertsonato = 0;
    public static TextView txtDni,txtPrezioTotala,txtIzena,txtSignatura,txtHelbidea;
    public static EditText txtFechaInicio, txtFechaSalida, coste;

    private Spinner spnPertsonaTot;
    private Calendar c = Calendar.getInstance();
    private String CERO = "0", BARRA = "-";
    private int mes = c.get(Calendar.MONTH), dia = c.get(Calendar.DAY_OF_MONTH), ano = c.get(Calendar.YEAR);
    private Bezeroa bez;
    private Ostatu ostatu;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserva);
        txtDni = (TextView) findViewById(R.id.txtNan);
        txtPrezioTotala = (TextView) findViewById(R.id.txtPrezioa);
        txtIzena = (TextView) findViewById(R.id.txtOstatuIzena);
        txtSignatura = (TextView) findViewById(R.id.txtSignatura);
        txtHelbidea = (TextView) findViewById(R.id.txtHelbidea);
        txtFechaInicio = (EditText) findViewById(R.id.txtDataHasiera);
        txtFechaSalida = (EditText) findViewById(R.id.txtDataAmaiera);
        spnPertsonaTot= (Spinner) findViewById(R.id.spn_pertsonaErreserba);


        ostatu = (Ostatu) getIntent().getSerializableExtra("datos");
        bez = (Bezeroa) getIntent().getSerializableExtra("bez");

        nan=bez.getNAN();
        txtDni.setText(nan.toString());

        nombre = ostatu.getOSTATU_IZENA();
        txtIzena.setText(nombre);

        signatura = ostatu.getID_SIGNATURA();
        txtSignatura.setText(signatura);

        direccion = ostatu.getOSTATU_HELBIDEA();
        txtHelbidea.setText(direccion);

        dataSartu = "";
        dataIrten = "";
        txtFechaInicio.setText(dataSartu);
        txtFechaSalida.setText(dataIrten);

        //Llenar pertsona totala
        ArrayList<String> pertsonaTotArr = new ArrayList<String>();
        for (int i = 1; i<=30;i++)
            pertsonaTotArr.add(i + "");
        ArrayAdapter<String> cmbAdapPertsonaTot = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item, pertsonaTotArr);
        cmbAdapPertsonaTot.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnPertsonaTot.setAdapter(cmbAdapPertsonaTot);
        spnPertsonaTot.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                cambiarPrecio(position);
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        cambiarPrecio(2);

    }

    public void ventanaMapa(View view) {

        Intent intent2 = new Intent (view.getContext(), mapaOstatubat.class);
        intent2.putExtra("ostatu", ostatu);
        startActivityForResult(intent2, 0);
    }

    public void cambiarPrecio(int pertsonaTot){
        double prez = (Math.random() * 300) + 1;

        prezio =  prez * pertsonaTot;
        txtPrezioTotala.setText(prezio+"");
    }

    public void seleccionarFecha1(View view){
        Calendar c = Calendar.getInstance();

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                int mesActual = month + 1;
                String diaFormateado = (dayOfMonth < 10)? CERO + String.valueOf(dayOfMonth):String.valueOf(dayOfMonth);
                String mesFormateado = (mesActual < 10)? CERO + String.valueOf(mesActual):String.valueOf(mesActual);
                txtFechaInicio.setText(year + BARRA + mesFormateado + BARRA + diaFormateado);
            }
        },ano, mes, dia);
        datePickerDialog.show();
    }

    public void seleccionarFecha2(View view){
        Calendar c = Calendar.getInstance();

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                int mesActual = month + 1;
                String diaFormateado = (dayOfMonth < 10)? CERO + String.valueOf(dayOfMonth):String.valueOf(dayOfMonth);
                String mesFormateado = (mesActual < 10)? CERO + String.valueOf(mesActual):String.valueOf(mesActual);
                txtFechaSalida.setText(year + BARRA + mesFormateado + BARRA + diaFormateado);
            }
        },ano, mes, dia);
        datePickerDialog.show();
    }

    public void reservar(View view){
      //  txtFechaInicio = (EditText) findViewById(R.id.txtDataHasiera);
//        txtFechaSalida = (EditText) findViewById(R.id.txtDataAmaiera);
    if (txtFechaSalida.getText().length() != 0 & txtFechaInicio.getText().length() != 0 ) {
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                constants.reserva,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        String insertado="";
                        try {
                            JSONObject obj = new JSONObject(response);
                            insertado = obj.getString("error").toString();

                            if(insertado.equalsIgnoreCase("false")){
                                int duration = Toast.LENGTH_LONG;
                                Context context = getApplicationContext();
                                Toast toast = Toast.makeText(context, R.string.erreserbaEginda, duration);
                                toast.show();

                                Intent intent1 = new Intent (context, Mainmenu.class);
                                intent1.putExtra("bez", bez);
                                startActivityForResult(intent1, 0);
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
                int baimena = 1;
                Map<String, String> params = new HashMap<>();
                String nanEncr="";
                try {
                    nanEncr = encriptar(nan, "encriptar");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                params.put("DATA_AMAIERA",txtFechaSalida.getText().toString());
                params.put("DATA_HASIERA",txtFechaInicio.getText().toString());
                params.put("ERRESERBA_PREZIO_TOT", String.valueOf(prezio));
                params.put("PERTSONA_KANT_ERRES", String.valueOf(spnPertsonaTot.getSelectedItem()).toString());
                params.put("OSTATUAK_ID_SIGNATURA",signatura);
                params.put("ERABILTZAILEAK_NAN",nanEncr);

                return params;
            }

        };
        try {
            RequestHandler.getInstance(this).addToRequestQueue(stringRequest);

        }catch(Exception e){
            int duration = Toast.LENGTH_SHORT;
            Context context = getApplicationContext();
            Toast toast = Toast.makeText(context, R.string.errorReservaServ, duration);
            toast.show();
        }
        //Cambio de pantalla
    } else {
        int duration = Toast.LENGTH_SHORT;
        Context context = getApplicationContext();
        Toast toast = Toast.makeText(context, R.string.errorReserva, duration);
        toast.show();
    }
    }

    public void cancelarReserva(View view){
        Intent intent1 = new Intent (view.getContext(), sacarReservas.class);
        startActivityForResult(intent1, 0);
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
