package com.turismoalojamenduakandroid;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Filtrador extends AppCompatActivity{
    Button filter;
    private String[] mota = {"Mota 1","Mota 2"};
    private ArrayList<String> filtroLista = new ArrayList<String>();
    private String Mota;
    EditText Posta;
    boolean filtrado = true;

    private EditText fechaInicio, fechaFinal;
    private TextView errorMessage;

    private Spinner spnProbintzia, spnMota, spnHerria, spnPertsonaTot;
    private Calendar c = Calendar.getInstance();
    private String CERO = "0", BARRA = "-";
    private int mes = c.get(Calendar.MONTH), dia = c.get(Calendar.DAY_OF_MONTH), ano = c.get(Calendar.YEAR);
    private String[] strMota = new String[4];
    private String[] putamierda = new String[3];

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.filtrador);

        fechaFinal=findViewById(R.id.txtAmaieraData);
        fechaInicio=findViewById(R.id.txtHasieraData);
        spnProbintzia=findViewById(R.id.spn_probintzia);
        spnMota=findViewById(R.id.spn_mota);
        spnHerria=findViewById(R.id.spn_herria);
        spnPertsonaTot=findViewById(R.id.spn_pertsonaTot);
        errorMessage=findViewById(R.id.txtVError);
        strMota  = (String[]) getIntent().getSerializableExtra("strMota");
        putamierda = (String[]) getIntent().getSerializableExtra("strProvincias");
        ArrayAdapter<String>  cmbAdapMota = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item, strMota);
        spnMota.setAdapter(cmbAdapMota);

        //Llenar pertsona totala
        ArrayList<String> pertsonaTot = new ArrayList<String>();
        for (int i = 1; i<=30;i++)
            pertsonaTot.add(i + "");
        ArrayAdapter<String> cmbAdapPertsonaTot = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item, pertsonaTot);
        spnPertsonaTot.setAdapter(cmbAdapPertsonaTot);

       // Llenar provincias
        //String[] strProvincias = new String[] {"Araba", "Bizkaia", "Gipuzkoa"};

        ArrayAdapter<String> cmbAdapProbintziak = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item, putamierda);
        spnProbintzia.setAdapter(cmbAdapProbintziak);
        /*
        spnProbintzia.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                String hartutakoProbintzia = (String) spnProbintzia.getSelectedItem();
                spnHerria.setAdapter(null);
               jarriHerriak(hartutakoProbintzia);
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });*/



    }

    private void jarriHerriak (String hartutakoProbintzia) {
        // para asier
        // "SELECT DISTINCT(HERRI_IZENA) FROM posta_kodeak WHERE upper(PROBINTZIA) LIKE '" + hartutakoProbintzia + "' ORDER BY HERRI_IZENA"

        String[] srtHerriak;
        if (hartutakoProbintzia == "Araba") {
            srtHerriak = new String[] {"Bernedo", "Amurrio", "Elciego"};
        }else if (hartutakoProbintzia == "Bizkaia") {
            srtHerriak = new String[] {"Bilbao", "Erandio", "Loiu"};
        }else { //gipuzkoa
            srtHerriak = new String[] {"Eibar", "Getaria", "Mendaro"};
        }
        ArrayAdapter<String> cmbAdapHerriak = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item, srtHerriak);
        spnHerria.setAdapter(cmbAdapHerriak);
    }

    public void seleccionarFechaInicio(View view){
        fechaInicio=findViewById(R.id.txtHasieraData);
        Calendar c= Calendar.getInstance();

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                int mesActual = month + 1;
                String diaFormateado = (dayOfMonth < 10)? CERO + String.valueOf(dayOfMonth):String.valueOf(dayOfMonth);
                String mesFormateado = (mesActual < 10)? CERO + String.valueOf(mesActual):String.valueOf(mesActual);
                fechaInicio.setText(year + BARRA + mesFormateado + BARRA + diaFormateado );
            }
        },ano, mes, dia);
        datePickerDialog.show();
    }

    public void seleccionarFechaFinal(View view){
        fechaFinal=findViewById(R.id.txtAmaieraData);
        Calendar c= Calendar.getInstance();

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                int mesActual = month + 1;
                String diaFormateado = (dayOfMonth < 10)? CERO + String.valueOf(dayOfMonth):String.valueOf(dayOfMonth);
                String mesFormateado = (mesActual < 10)? CERO + String.valueOf(mesActual):String.valueOf(mesActual);
                fechaFinal.setText(year + BARRA + mesFormateado + BARRA + diaFormateado );
            }
        },ano, mes, dia);
        datePickerDialog.show();
    }

    public void ostatuakPantalla () {
        Intent pantallaOstatuak = new Intent(Filtrador.this, Ostatuak.class);
        startActivity(pantallaOstatuak);
    }

    public void filtrar(View view){

        if (fechaFinal.getText() != null & fechaInicio.getText() != null ) {
            //AQUI LLAMAR A LA API, FILTRADOS TRUE
            //Supongo que habra que pasarle a la otra pantalla los datos o llamar desde ahi
            //Para Asier
            errorMessage.setVisibility(View.INVISIBLE);

            ostatuakPantalla();

        }else {
            errorMessage.setVisibility(View.VISIBLE);

        }



    }


}
