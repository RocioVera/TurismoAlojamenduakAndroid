package com.turismoalojamenduakandroid;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.HashMap;
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

public class Filtrador<pueblos> extends AppCompatActivity{
    Button filter;
    private String[] mota = {"Mota 1","Mota 2"};
    private ArrayList<String> filtroLista = new ArrayList<String>();
    private String Mota;
    EditText Posta;
    boolean filtrado = true;
    //Para pasar a las siguiente pantalla Datos
    public static String Motass = "";
    public static String Probintzia ="";
    public static String Herria="";
    public static String kop = "";
    public static int kopurua = 0;
    public static ArrayList <String> ostatus =  new ArrayList<>();


    public static String hartutakoProbintzia="";
    public static ArrayList<String> pueblos ;

    private Spinner spnProbintzia, spnMota, spnHerria, spnPertsonaTot;

    private String[] strMota = new String[4];
    private String[] putamierda = new String[3];
    View vista;
    Context context;
    private Bezeroa bez;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.filtrador);

        spnProbintzia=findViewById(R.id.spn_probintzia);
        spnMota=findViewById(R.id.spn_mota);
        spnHerria=findViewById(R.id.spn_herria);
        spnPertsonaTot=findViewById(R.id.spn_pertsonaTot);
        strMota  = (String[]) getIntent().getSerializableExtra("strMota");
        putamierda = (String[]) getIntent().getSerializableExtra("strProvincias");
        bez = (Bezeroa) getIntent().getSerializableExtra("bez");

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

        spnProbintzia.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                hartutakoProbintzia = (String) spnProbintzia.getSelectedItem().toString();
                spnHerria.setAdapter(null);
                jarriHerriak ();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }

    private void jarriHerriak () {
        context = this;
        vista = new View(context);
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                constants.herris,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        pueblos = new ArrayList<String>();
                        try {

                            JSONArray ja=new JSONArray(response);
                            JSONObject jo=null;

                            int contador =0;
                            for(int i=0;i<ja.length();i++){
                                jo=ja.getJSONObject(i);
                                String pueblo = jo.getString("HERRI_IZENA");
                                pueblos.add(pueblo);
                            }

                            ArrayAdapter<String> cmbAdapHerriak = new ArrayAdapter<>(context,android.R.layout.simple_spinner_item, pueblos);
                            spnHerria.setAdapter(cmbAdapHerriak);
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
                params.put("PROBINTZIA",hartutakoProbintzia);


                return params;
            }

        };
        try {
            RequestHandler.getInstance(this).addToRequestQueue(stringRequest);

        }catch(Exception e){

        }
        //Cambio de pantalla


    }

    public void ostatuakPantalla () {
        Intent pantallaOstatuak = new Intent(Filtrador.this, Ostatuak.class);
        pantallaOstatuak.putExtra("bez", bez);

        startActivity(pantallaOstatuak);
    }

    public void pintarPueblos(){

    }

    public void aFiltrar(View v){
         Motass = (String) spnMota.getSelectedItem().toString();;
         Probintzia =(String) spnProbintzia.getSelectedItem().toString();
         Herria=(String) spnHerria.getSelectedItem().toString();
         kop = (String) spnPertsonaTot.getSelectedItem().toString();
         kopurua = Integer.parseInt(kop);
        Context context = this;
        vista = new View(context);
            StringRequest stringRequest = new StringRequest(
                    Request.Method.POST,
                    constants.ostatus,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            ostatus = new ArrayList<String>();
                            try {

                                JSONArray ja=new JSONArray(response);
                                JSONObject jo=null;

                                int contador =0;
                                for(int i=0;i<ja.length();i++){
                                    jo=ja.getJSONObject(i);
                                    String ostatu = jo.getString("OSTATU_IZENA");
                                    ostatus.add(ostatu);

                                }

                                Intent intent2 = new Intent (vista.getContext(), pintarListado.class);
                                    intent2.putExtra("listado", ostatus);
                                    intent2.putExtra("bez", bez);
                                    startActivityForResult(intent2, 0);


                            } catch (JSONException e) {
                                int duration2 = Toast.LENGTH_SHORT;
                                Context context2 = getApplicationContext();
                                Toast toast2 = Toast.makeText(context2, R.string.errorFiltrado, duration2);
                                toast2.show();

                               // e.printStackTrace();
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

                    params.put("MOTA",Motass);
                    params.put("PROBINTZIA",Probintzia);
                    params.put("HERRI_IZENA",Herria);


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
