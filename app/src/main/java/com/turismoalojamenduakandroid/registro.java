package com.turismoalojamenduakandroid;







import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class registro extends AppCompatActivity {
    private EditText nombre;
    private EditText apellido;
    private EditText contraseña;
    private EditText telefono;
    private EditText dni;
    private EditText email;
    Button boton;
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        nombre = (EditText) findViewById(R.id.editText);
        apellido = (EditText) findViewById(R.id.editText4);
        contraseña = (EditText) findViewById(R.id.editText2);
        telefono = (EditText) findViewById(R.id.editText3);
        dni = (EditText) findViewById(R.id.editText7);
        email = (EditText) findViewById(R.id.editText6);
        boton = (Button) findViewById(R.id.button);
        boton.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                registro();
            }
        });

    }

    public  void registro(){
       /* Context context = getApplicationContext();
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, "Fallo", duration);
        toast.show();*/

        int baimena = 1;
        // Crear nuevo objeto Json basado en el mapa

        // Actualizar datos en el servidor
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                constants.registrarUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            int duration = Toast.LENGTH_SHORT;
                            Context context = getApplicationContext();
                            Toast toast = Toast.makeText(context, "Registro completado", duration);
                            toast.show();
                            JSONObject obj = new JSONObject(response);

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
                        Toast toast = Toast.makeText(context, "Error en el registro,el usuario está registrado", duration);
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
                params.put("dni", dni.getText().toString());
                params.put("username", nombre.getText().toString());
                params.put("ape", apellido.getText().toString());
                params.put("pass", contraseña.getText().toString());
                params.put("baimena", String.valueOf(baimena));
                params.put("email", email.getText().toString());
                params.put("telefono", telefono.getText().toString());
                return params;
            }

        };
        try {
            RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
        }catch(Exception e){

        }
    }

    private void procesarRespuestaActualizar(JSONObject response) {

        try {
            // Obtener estado
            String estado = response.getString("error");
            // Obtener mensaje
            String mensaje = response.getString("mensaje");


        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


    public void elBajondeNickOT(View v){
        Intent intent2 = new Intent (v.getContext(), MainActivity.class);
        startActivityForResult(intent2, 0);
    }



    public String estoesmejorqueunconservatorio(String parametro){
        String codificado = "";



        return codificado;
    }


}
