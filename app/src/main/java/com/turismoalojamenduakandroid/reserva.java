package com.turismoalojamenduakandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class reserva extends AppCompatActivity {
    String dataSartu="",dataIrten="",signatura = "",nan ="", nombre ="", direccion ="";
    Double prezio=0.0;
    int pertsonato = 0;
    private TextView txtDni,txtPrezioTotala,txtIzena,txtSignatura,txtHelbidea;
    private EditText txtFechaInicio, txtFechaSalida, coste;

    private Spinner spnPertsonaTot;
    private Calendar c = Calendar.getInstance();
    private String CERO = "0", BARRA = "-";
    private int mes = c.get(Calendar.MONTH), dia = c.get(Calendar.DAY_OF_MONTH), ano = c.get(Calendar.YEAR);

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
        spnPertsonaTot=findViewById(R.id.spn_pertsonaTot);

        //Llenar pertsona totala
        ArrayList<String> pertsonaTot = new ArrayList<String>();
        for (int i = 1; i<=30;i++)
            pertsonaTot.add(i + "");
        ArrayAdapter<String> cmbAdapPertsonaTot = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item, pertsonaTot);
        spnPertsonaTot.setAdapter(cmbAdapPertsonaTot);

        recibirDatos();
        ponerDatos();


    }

    private void recibirDatos() {
        Ostatu ostatu = (Ostatu) getIntent().getSerializableExtra("datos");
        Bezeroa bez = (Bezeroa) getIntent().getSerializableExtra("bez");

        nan = bez.getNAN();
        prezio =  (Math.random()*300 + 100)*(int)spnPertsonaTot.getSelectedItem();
        nombre = ostatu.getOSTATU_IZENA();
        signatura = ostatu.getID_SIGNATURA();
        direccion = ostatu.getOSTATU_HELBIDEA();
        dataSartu = "";
        dataIrten = "";

    }

    private void ponerDatos() {
        txtDni.setText(signatura);
    }

    public void cambiarPrecio(View v){
        prezio =  (Math.random()*300 + 100)*(int)spnPertsonaTot.getSelectedItem();
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
    if (txtFechaSalida.getText().toString() != "" & txtFechaInicio.getText().toString() != "") {
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

                params.put("DATA_AMAIERA",txtFechaSalida.getText().toString());
                params.put("DATA_HASIERA",txtFechaInicio.getText().toString());
                params.put("ERRESERBA_PREZIO_TOT", String.valueOf(prezio));
                params.put("PERTSONA_KANT_ERRES", String.valueOf((int)spnPertsonaTot.getSelectedItem()));
                params.put("OSTATUAK_ID_SIGNATURA",signatura);
                params.put("ERABILTZAILEAK_NAN",nan);


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
}
