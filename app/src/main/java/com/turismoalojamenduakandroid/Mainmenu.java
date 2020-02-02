package com.turismoalojamenduakandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Mainmenu extends AppCompatActivity {
    private String[] strMota = new String[4];
    private String[] strProvincias = new String[3];
    private Bezeroa bez;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        bez = (Bezeroa) getIntent().getSerializableExtra("bez");

        new MyTask().execute();
    }


    public void areservas(View view){
        Intent intent2 = new Intent (view.getContext(), Filtrador.class);
        intent2.putExtra("strMota", strMota);
        intent2.putExtra("strProvincias", strProvincias);
        intent2.putExtra("bez", bez);

        startActivityForResult(intent2, 0);
    }

    private class MyTask extends AsyncTask<Void,Void,Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            ateramotas();
            ateraprobintzia();
            return null;
        }
    }

    public String[] ateramotas() {
        String line=null;
        String result=null;
        BufferedInputStream is = null;
        try{

            URL url=new URL(constants.U30);
            HttpURLConnection con=(HttpURLConnection)url.openConnection();
            con.setRequestMethod("GET");
            is= new BufferedInputStream(con.getInputStream());

        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        //content
        try{
            BufferedReader br=new BufferedReader(new InputStreamReader(is));
            StringBuilder sb=new StringBuilder();
            while ((line=br.readLine())!=null){
                sb.append(line+"\n");
            }
            is.close();
            result=sb.toString();

        }
        catch (Exception ex)
        {
            ex.printStackTrace();

        }

//JSON
        try{
            JSONArray ja=new JSONArray(result);
            JSONObject jo=null;


            for(int i=0;i<ja.length();i++){
                jo=ja.getJSONObject(i);
                strMota[i]=jo.getString("MOTA");

            }
        }
        catch (Exception ex)
        {

            ex.printStackTrace();
        }

        //PHP
        //strMota = ateramotas();
        //String[] strMota = new String[] {"Casas Rurales", "Apartarmentos", "Hotel"};

        return strMota;

    }

    public String[] ateraprobintzia() {

        String line=null;
        String result=null;
        BufferedInputStream is = null;
        try{

            URL url=new URL(constants.probintzia);
            HttpURLConnection con=(HttpURLConnection)url.openConnection();
            con.setRequestMethod("GET");
            is= new BufferedInputStream(con.getInputStream());

        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        //content
        try{
            BufferedReader br=new BufferedReader(new InputStreamReader(is));
            StringBuilder sb=new StringBuilder();
            while ((line=br.readLine())!=null){
                sb.append(line+"\n");
            }
            is.close();
            result=sb.toString();

        }
        catch (Exception ex)
        {
            ex.printStackTrace();

        }

//JSON
        try{
            JSONArray ja=new JSONArray(result);
            JSONObject jo=null;

            int contador =0;
            for(int i=0;i<ja.length();i++){
                jo=ja.getJSONObject(i);

                if (!jo.getString("PROBINTZIA").equalsIgnoreCase("HUTSIK") && !jo.getString("PROBINTZIA").equalsIgnoreCase(null)){
                    strProvincias[contador]=jo.getString("PROBINTZIA");
                    contador ++;
                }

            }
        }
        catch (Exception ex)
        {

            ex.printStackTrace();
        }

        //PHP
        //strMota = ateramotas();
        //String[] strMota = new String[] {"Casas Rurales", "Apartarmentos", "Hotel"};

        return strProvincias;

    }
}
