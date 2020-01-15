package com.turismoalojamenduakandroid;


import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import androidx.appcompat.app.AppCompatActivity;

public class prueba extends AppCompatActivity {

    private String  maquina="localhost/";
    private String  usuario="root";
    private String  clave="";
    private int puerto= 3306;
    private String  servidor="127.0.0.1";

    private static Connection conexion  = null;


    public prueba(String baseDatos){
        Log.d("Diego", "prueba");
        Log.d("Diego", baseDatos);

        String server="jdbc:mysql://";

        Log.d("Diego", "Entrada En conexion_____________________________________________");

        this.servidor="jdbc:mysql://localhost:3306/elorrieta";

        //this.servidor="jdbc:mysql://"+this.maquina+baseDatos+":"+ this.puerto+"/"+baseDatos;
        //jdbc:mysql://localhost:3306/[database]

        try {
            Log.d("Diego", "Entra en el primer try_____________________________________________");

            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            Log.d("Diego", "Falla el primer try_____________________________________________");

            System.err.println("ERROR AL REGISTRAR EL DRIVER");
            System.exit(0); //parar la ejecución
        }
        try {
            conexion = DriverManager.getConnection("jdbc:mysql://"+this.maquina+baseDatos+"",this.usuario, this.clave);
        } catch (SQLException e) {
            e.printStackTrace();
            Log.d("Diego", "Fally maxmo: " + e);

        }
    }



}
