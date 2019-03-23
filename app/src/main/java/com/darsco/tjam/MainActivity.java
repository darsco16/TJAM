package com.darsco.tjam;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.ListView;

import org.json.JSONArray;

import java.io.BufferedInputStream;
import java.lang.StringBuilder;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private ListView lvNota;
    //private Adaptador adaptador;
    ListView listaProducto;
    ProgressDialog pd;
    private ProgressDialog progressDialog;

    String resultadoTotal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        listaProducto = (ListView) findViewById(R.id.lvNotas);

        EjecutarTodo();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_tjam) {
            // Handle the camera action
        } else if (id == R.id.nav_jel) {
            startActivity(new Intent(MainActivity.this, login_jel.class));
            return true;
        } else if (id == R.id.nav_acdos) {
            startActivity(new Intent(MainActivity.this, consulta_acdos.class));
            return true;
        } else if (id == R.id.nav_listas) {
            Uri uri = Uri.parse("http://tjamich.gob.mx/Listas-de-Acuerdos");
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_manual) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private String Conectar(){

        int respuesta = 0;
        StringBuilder resultadoTotal = null;
        StringBuilder value = null;
        String linea = "";
        URL url = null;

        try{
            url = new URL("http://tjamich.gob.mx/newstjam.php");

            HttpURLConnection conexion = (HttpURLConnection) url.openConnection();
            respuesta = conexion.getResponseCode();

            resultadoTotal = new StringBuilder();
            value = new StringBuilder();

            if(respuesta == HttpURLConnection.HTTP_OK){

                InputStream inputStream = new BufferedInputStream(conexion.getInputStream());
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

                while((linea = bufferedReader.readLine()) != null){
                    //resultadoTotal.append(linea);
                }
                value.append("ing");

            }

        }catch (Exception e){
            e.printStackTrace();
        }
        //return resultadoTotal = stringBuilder.toString();
        //return resultadoTotal.toString();
        return linea.toString();
    }

    public ArrayList<Entidad> ObtenerJson(String result){
        try{
            ArrayList<Entidad> productoBean = new ArrayList<Entidad>();
            JSONArray jsonArray = new JSONArray(result);
            Entidad pb;

            for(int i = 0; i < jsonArray.length(); i++){
                pb = new Entidad();

                pb.setTitulo(jsonArray.getJSONObject(i).getString("titulo"));
                pb.setResumen(jsonArray.getJSONObject(i).getString("resumen"));
                pb.setContenido(jsonArray.getJSONObject(i).getString("contenido"));
                pb.setImgFoto(jsonArray.getJSONObject(i).getString("imagen2"));

                //Agregas a tu lista el nuevo objeto
                productoBean.add(pb);

            }
            //Devuelves el listado
            return productoBean;

        }catch (Exception e){
            e.printStackTrace();
        }

        return null;

    }

    protected class HiloCargarImagen extends AsyncTask<String, Void, Object> {

        @Override
        protected Object doInBackground(String... strings) {
            Conectar();
            return null;
        }

        protected void onPostExecute(Object result){
            progressDialog.dismiss();

            ArrayList<Entidad> productoBean = ObtenerJson(resultadoTotal);
            if (productoBean != null){
                Adaptador adaptador = new Adaptador(MainActivity.this, productoBean);
                listaProducto.setAdapter(adaptador);
            }

        }
    }

    private void EjecutarTodo(){
        //pd = ProgressDialog.show(this, "Cargando las noticias", "Espere por favor...", true, false);
        progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setTitle("Cargando las noticias");
        progressDialog.setMessage("Espere por favor...");
        progressDialog.show();

        new HiloCargarImagen().execute();
    }
}
