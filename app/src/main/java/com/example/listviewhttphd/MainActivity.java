package com.example.listviewhttphd;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ListView list;
    ArrayList<String> nomHD = new ArrayList<>();
    ArrayList<String> ciuHD = new ArrayList<>();
    ArrayList<String> telHD = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        android:setTitle("Contactos");

        //Declaracion del listView
        nomHD = new ArrayList<String>();
        ciuHD = new ArrayList<String>();
        telHD = new ArrayList<String>();
        list = (ListView) findViewById(R.id.listView);

        //Conexion con el servidor
        Tarea t = new Tarea();
        t.execute("http://huasteco.tiburcio.mx/~dam17091039/consultar.php");
    }

    class Tarea extends AsyncTask<String,Void,String> {
        @Override
        protected String doInBackground(String... strings) {
            return ConexionWeb(strings[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try{
                String salida ="";
                JSONArray tabla = new JSONArray(s);//Aqui llega la cadena JSON
                for(int i =0; i<tabla.length();i++){
                    JSONObject renglon=tabla.getJSONObject(i);
                    String nombre = renglon.getString("nombre");
                    nomHD.add(nombre);
                    String ciudad = renglon.getString("ciudad");
                    ciuHD.add(ciudad);
                    String idtel= renglon.getString("idtel");
                    telHD.add(idtel);
                }
                ArrayAdapter<String> adap = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1,nomHD);
                list.setAdapter(adap);
                Object[]arregloN = nomHD.toArray();
                Object[]arregloC = ciuHD.toArray();
                Object[]arregloTel= telHD.toArray();

                list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int i, long id) {
                        String n = String.valueOf(arregloN[i]);
                        String c = String.valueOf(arregloC[i]);
                        String t = String.valueOf(arregloTel[i]);
                        Intent intent = new Intent(getApplicationContext(),ContacHD.class);
                        intent.putExtra("nombre",n);
                        intent.putExtra("ciudad",c);
                        intent.putExtra("idtel",t);
                        startActivity(intent); }
                });
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    String ConexionWeb(String direccion) {
        String pagina="";
        try {
            URL url = new URL(direccion);
            HttpURLConnection conexion = (HttpURLConnection) url.openConnection();
            if (conexion.getResponseCode() == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new
                        InputStreamReader(conexion.getInputStream()));
                String linea = reader.readLine();
                while (linea != null) {
                    pagina += linea + "\n";
                    linea = reader.readLine();
                }
                reader.close();

            } else {
                pagina += "ERROR: " + conexion.getResponseMessage() + "\n";
            }
            conexion.disconnect();
        }
        catch (Exception e){
            pagina+=e.getMessage();
        }
        return pagina;
    }
}