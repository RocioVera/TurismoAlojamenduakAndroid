package com.turismoalojamenduakandroid;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class registro extends AppCompatActivity {
    private EditText nombre;
    private EditText apellido;
    private EditText contraseña;
    private EditText telefono;
    private EditText dni;
    private EditText email;
    Button boton;
    private ProgressDialog progressDialog;

    String apiKeyEncriptada ="0SPrEK0JntQ2qCm9cPEabw==";
    String passwordEncriptacion = "gdsawr";

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
        boton = (Button) findViewById(R.id.btnRegistrar);


        boton.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                registro();
            }
        });

    }

    public  void registro(){
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
                            Toast toast = Toast.makeText(context, R.string.registroCompletado, duration);
                            toast.show();
                            JSONObject obj = new JSONObject(response);
                            dni.setText("");
                            nombre.setText("");
                            telefono.setText("");
                            contraseña.setText("");
                            apellido.setText("");
                            email.setText("");

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
                        Toast toast = Toast.makeText(context, R.string.registroError, duration);
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
                String nan = dni.getText().toString(), bez = nombre.getText().toString(),
                        abiz = apellido.getText().toString(), pasahitza = contraseña.getText().toString(),
                        emaila = email.getText().toString(), telefonoa = telefono.getText().toString();
                try {

                    nan = encriptar(dni.getText().toString(), "encriptar");
                    bez = encriptar(nombre.getText().toString(), "encriptar");
                    abiz = encriptar(apellido.getText().toString(), "encriptar");
                    pasahitza = encriptar(contraseña.getText().toString(), "encriptar");
                    emaila = encriptar(email.getText().toString(), "encriptar");
                    telefonoa = encriptar(telefono.getText().toString(), "encriptar");
                } catch (Exception e) {
                    e.printStackTrace();
                }

                params.put("dni", nan);
                params.put("username", bez);
                params.put("ape", abiz);
                params.put("pass", pasahitza);
                params.put("baimena", String.valueOf(baimena));
                params.put("email", emaila);
                params.put("telefono", telefonoa);

                return params;
            }

        };
        try {
            RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
        }catch(Exception e){

        }
    }


    public void volverAtras(View v){
        Intent intent2 = new Intent (v.getContext(), MainActivity.class);
        startActivityForResult(intent2, 0);
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
