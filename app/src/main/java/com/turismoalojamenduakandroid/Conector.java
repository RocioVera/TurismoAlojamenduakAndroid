package com.turismoalojamenduakandroid;

import android.util.Log;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import androidx.appcompat.app.AppCompatActivity;

public class Conector extends AppCompatActivity {

    private String  maquina="localhost/";
    private String  usuario="root";
    private String  clave="";
    private int puerto= 3306;
    private String  servidor="127.0.0.1";

    private static Connection conexion  = null;

    //CONSTRUCTOR
    //Recibe el nombre de la base de datos

    public Conector(String baseDatos){

        String server="jdbc:mysql://";

        Log.d("Diego", "Entrada En conexion_____________________________________________");

        this.servidor="jdbc:mysql://"+this.maquina+baseDatos+":"+ this.puerto+"/"+baseDatos;
        //Registrar el driver
        try {
            Log.d("Diego", "Entra en el primer try_____________________________________________");

            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            Log.d("Diego", "Falla el primer try_____________________________________________");

            System.err.println("ERROR AL REGISTRAR EL DRIVER");
            System.exit(0); //parar la ejecución
        }

        //Establecer la conexión con el servidor
        try {
            conexion = DriverManager.getConnection("jdbc:mysql://"+this.maquina+baseDatos+"",this.usuario, this.clave);


        } catch (SQLException e) {
            System.err.println("ERROR AL CONECTAR CON EL SERVIDOR");
            System.exit(0); //parar la ejecución

        }
        System.out.println("Conectado a "+baseDatos);
    }

    /**
     *
     * @return conexion
     */
    //Devuelve el objeto Connection que se usará en la clase Controller
    public static Connection getConnection() {
        return conexion;
    }


}
