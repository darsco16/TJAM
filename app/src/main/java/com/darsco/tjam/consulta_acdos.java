package com.darsco.tjam;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class consulta_acdos extends AppCompatActivity {

    Button btnBuscarAcdos;
    EditText etExpediente;
    TextView txtExpediente;
    TextView txtArea;

    private ListView lvLista;
    private adaptador_acdos adaptadorAcdos;
    ListView listaAcuerdos;
    ProgressDialog pd;

    String resultadoTotalAcdos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulta_acdos);

        btnBuscarAcdos = (Button)findViewById(R.id.btnBuscarAcdos);
        etExpediente = (EditText)findViewById(R.id.etExpediente);
        txtArea = (TextView)findViewById(R.id.txtArea);
        txtExpediente = (TextView)findViewById(R.id.txtExpediente);
        listaAcuerdos = (ListView) findViewById(R.id.lvLista);

        btnBuscarAcdos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //String consulta = "http://tjamich.gob.mx/consultarAcdos.php?accion=acdos&expediente="+etExpediente.getText();
                String consulta2 = "http://tjamich.gob.mx/consultarAcdos.php?accion=exp&expediente="+etExpediente.getText();
                String consulta3 = "http://tjamich.gob.mx/consultarAcdos.php?accion=area&expediente="+etExpediente.getText();
                //EnviarRecibirAcdos(consulta);
                EnviarRecibirExp(consulta2);
                EnviarRecibirArea(consulta3);
                //EjecutarTodo();
                InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(btnBuscarAcdos.getWindowToken(), 0);
                etExpediente.setText("");
            }
        });
    }

    public void EnviarRecibirArea(String URL){
        //Toast.makeText(getApplicationContext(), ""+URL, Toast.LENGTH_SHORT).show();
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //response = response.replace("][",",");
                if (response.length()>0){
                    try {
                        JSONArray ja = new JSONArray(response);
                        Log.i("sizejson",""+ja.length());
                        CargarArea(ja);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        queue.add(stringRequest);
    }
    public void CargarArea(JSONArray ja){
        ArrayList<String> lista = new ArrayList<>();
        try {
            lista.add(ja.getString(0));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        txtArea.setText(lista.get(0));
    }

    public void EnviarRecibirExp(String URL){

        Toast.makeText(getApplicationContext(), ""+URL, Toast.LENGTH_SHORT).show();
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                response = response.replace("][",",");
                if (response.length()>0){
                    try {
                        JSONArray ja = new JSONArray(response);
                        Log.i("sizejson",""+ja.length());
                        CargarExpe(ja);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        queue.add(stringRequest);
    }

    public void CargarExpe(JSONArray ja){
        ArrayList<String> lista = new ArrayList<>();
        try {
            lista.add(ja.getString(0));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        txtExpediente.setText(lista.get(0));
    }

    private String ConectarAcdos(){

        int respuesta = 0;
        StringBuilder stringBuilder = null;
        String linea = "";
        URL url = null;

        try{
            url = new URL("http://tjamich.gob.mx/consultarAcdos.php?accion=acdos&expediente="+etExpediente.getText());

            HttpURLConnection conexion = (HttpURLConnection) url.openConnection();
            respuesta = conexion.getResponseCode();

            stringBuilder = new StringBuilder();

            if(respuesta == HttpURLConnection.HTTP_OK){

                InputStream inputStream = new BufferedInputStream(conexion.getInputStream());
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

                while((linea = bufferedReader.readLine()) != null){
                    stringBuilder.append(linea + "\n");
                }
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return resultadoTotalAcdos = stringBuilder.toString();
    }
}
